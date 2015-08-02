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
        (let [uri "/kollchap/monsters" resp (request :get uri)]
          (:status resp) => 200
          (get-in resp [:body :_links :self :href]) => (contains uri)))

  (fact "returns a monster"
        (let [uri "/kollchap/monster/1" resp (request :get uri)]
          (:status resp) => 200
          (get-in resp [:body :_links :self :href]) => (contains uri)
          (get-in resp [:body :monster :name]) => (get (mr/get-monster 1) :name)))

  (fact "returns characters"
        (let [uri "/kollchap/characters" resp (request :get uri)]
          (:status resp) => 200
          (get-in resp [:body :_links :self :href]) => (contains uri)))

  (fact "returns a player character"
        (let [uri "/kollchap/characters/1" resp (request :get uri)]
          (:status resp) => 200
          (get-in resp [:body :_links :self :href]) => (contains "/kollchap/characters/1")
          (get-in resp [:body :character]) => (cr/get-character 1)))

  (fact "create a game character"
        (let [uri "/kollchap/characters"
              resp (request :post uri :body test-player-fixture :content-type "application/json")]
          (:status resp) => 201
          (get-in resp [:body :character :name]) => (test-player-fixture :name)))

  (fact "returns a character's location"
        (let [uri "/kollchap/characters/6/location" resp (request :get uri)]
          (:status resp) => 200
          (get-in resp [:body :_links :self :href]) => (contains uri)
          (get-in resp [:body :location]) => (ln/get-character-location 6)))

  (fact "returns a room by key"
        (let [uri "/kollchap/rooms/1" resp (request :get uri)]
          (:status resp) => 200
          (get-in resp [:body :_links :self :href]) => (contains uri)
          (get-in resp [:body :room]) => (rm/get-room "1")))

  (fact "update character location"
        (let [uri "/kollchap/characters/1/location"
              resp (request :put uri :body test-location-fixture :content-type "application/json")]
          (:status resp) => 200)))