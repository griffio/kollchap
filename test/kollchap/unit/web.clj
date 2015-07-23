(ns kollchap.unit.web
  (:require [kollchap.handler :refer :all]
            [kollchap.character :as c]
            [kollchap.room :as r]
            [cheshire.core :as json]
            [midje.sweet :refer :all])
  (:import [java.io ByteArrayInputStream InputStream]))

(defn- json-response?
  [res]
  (when-let [content-type (get-in res [:headers "Content-Type"])]
    (re-find #"^application/(..+)?json.+" content-type)))

(defn request
  "Creates a compojure request map and applies it to our routes.
    Accepts method, resource and optionally an extended map"
  [method resource & {:keys [params body content-type headers]
                      :or   {params  {}
                             headers {}}}]
  (let [{:keys [body] :as res}
        (app (merge {:request-method method
                     :uri            resource
                     :params         params
                     :scheme         "http"
                     :server-name    "localhost"
                     :server-port    "8080"
                     :headers        headers}
                    (when body {:body (-> body json/generate-string .getBytes ByteArrayInputStream.)})
                    (when content-type {:content-type content-type})))]
    (cond-> res
            (instance? InputStream body)
            (update-in [:body] slurp)

            (json-response? res)
            (update-in [:body] #(json/parse-string % true)))))

(defn to-json [s] (json/generate-string s))
;;http --json POST localhost:3000/kollchap/characters id:=0 name='Lizard Man' background='Sitting on a rock...'
(def lizard-man-fixture {:id 4 :name "Lizard Man" :background "Sitting on a rock..." :room-id 23})
;;http --json POST localhost:3000/kollchap/rooms id:=0 name='Meditation Room' :description='This room was ...'
(def another-room-fixture {:id 0 :name "Another Room" :description "This room was ..."})

(fact-group
  :unit

  (fact "returns a player character"
        (let [resp (request :get "/kollchap/characters/1")]
          (:status resp) => 200
          (get-in resp [:body :character]) => (c/get-character 1)))

  (fact "create a game character"
        (let [resp (request :post "/kollchap/characters"
                            :content-type "application/json"
                            :body lizard-man-fixture)]
          (:status resp) => 201
          (get-in resp [:body :character :name]) => (lizard-man-fixture :name)))

  (fact "returns a character room location"
        (let [resp (request :get "/kollchap/characters/4/room")]
          (:status resp) => 200
          (get-in resp [:body :room]) => (r/get-room 5)))

  (fact "returns a room"
        (let [resp (request :get "/kollchap/rooms/1")]
          (:status resp) => 200
          (get-in resp [:body :room]) => (r/get-room 1)))

  (fact "create a room"
        (let [resp (request :post "/kollchap/rooms"
                            :content-type "application/json"
                            :body another-room-fixture)]
          (:status resp) => 201
          (get-in resp [:body :room :name]) => (another-room-fixture :name))))
                                      

