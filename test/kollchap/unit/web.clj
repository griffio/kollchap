(ns kollchap.unit.web
  (:require [kollchap.handler :refer :all]
            [kollchap.character :as cr]
            [kollchap.room :as rm]
            [kollchap.location :as ln]
            [cheshire.core :as json]
            [midje.sweet :refer :all]
            [kollchap.monster :as mr])
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
;;http --json POST localhost:3000/kollchap/characters id:=0 name='Wealthy Merchant' background='Locked in cells...'
(def test-location-fixture {:room-key "9a"})
(def test-player-fixture {:id 0 :name "Wealthy Merchant" :background "Locked in cells..." :room-key "13"})

(fact-group
  :unit

  (fact "returns monsters"
        (let [resp (request :get "/kollchap/monsters")]
          (:status resp) => 200))

  (fact "returns a monster"
        (let [resp (request :get "/kollchap/monster/1")]
          (:status resp) => 200
          (get-in resp [:body :monster :name]) => (get (mr/get-monster 1) :name)))

  (fact "returns characters"
        (let [resp (request :get "/kollchap/characters")]
          (:status resp) => 200))

  (fact "returns a player character"
        (let [resp (request :get "/kollchap/characters/1")]
          (:status resp) => 200
          (get-in resp [:body :character]) => (cr/get-character 1)))

  (fact "create a game character"
        (let [resp (request :post "/kollchap/characters"
                            :content-type "application/json"
                            :body test-player-fixture)]
          (:status resp) => 201
          (get-in resp [:body :character :name]) => (test-player-fixture :name)))

  (fact "returns a character's location"
        (let [resp (request :get "/kollchap/characters/6/location")]
          (:status resp) => 200
          (get-in resp [:body :location]) => (ln/get-character-location 6)))

  (fact "returns a room by key"
        (let [resp (request :get "/kollchap/rooms/1")]
          (:status resp) => 200
          (get-in resp [:body :room]) => (rm/get-room "1")))

  (fact "update character location"
        (let [resp (request :put "/kollchap/characters/1/location"
                            :content-type "application/json"
                            :body test-location-fixture)]
          (:status resp) => 200))
  )