(ns kollchap.unit.web
  (:require [kollchap.handler :refer :all]
            [kollchap.character :as c]
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
   [method resource & [{:keys [params body content-type headers]
                        :or {param {}
                             headers {}}}]]
   (let [{:keys [body] :as res}
         (app (merge {:request-method method
                      :uri resource
                      :params params
                      :scheme "http"
                      :server-name "localhost"
                      :server-port "8080"
                      :headers headers}
                    (when body {:body (-> body json/generate-string .getBytes ByteArrayInputStream.)})
                    (when content-type {:content-type content-type})))]
  (cond-> res
    (instance? InputStream body)
    (update-in [:body] slurp)

    (json-response? res)
    (update-in [:body] #(json/parse-string % true)))))

(defn to-json [s] (json/generate-string s))

(def lizard-man-fixture {:id 0 :name "Lizard Man"})

(fact-group
 :unit

(fact "returns a player character" 
      (let [resp (request :get "/kollchap/characters/1")]
        (:status resp) => 200
        (get-in resp [:body :character]) => (c/get-character 1)))

(fact "create a player character"
      (let [resp (request :post "/kollchap/characters"
                          :content-type "application/json" 
                          (to-json lizard-man-fixture))]
        (:status resp) => 200
        (get-in resp [:body :character]) => lizard-man-fixture)))
