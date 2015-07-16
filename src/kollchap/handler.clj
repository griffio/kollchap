(ns kollchap.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [kollchap.character :as c]
            [kollchap.monster :as m]
            [kollchap.resources :as rs]
            [kollchap.room :as r]
            [schema.core :as s]))

(defapi app
  (swagger-ui)
  (swagger-docs
    {:info {:title "The Shrine of Kollchap Api"
            :description "Shrine of Kollchap - taken from the book What is Dungeons and Dragons?"}
     :tags [{:name "kollchap", :description "kollchap api "}]})

  (context* "/kollchap" []
    :tags ["kollchap"]

    (GET* "/characters/:id" []
      :return rs/CharacterResource 
      :path-params [id :- Long]
      :summary "id path-parameter"
      (ok {:character (c/get-character id) :_links {:self {:href (str "/characters/" id)}}})))

    (GET* "/rooms/:id" []
      :return r/Room
      :path-params [id :- Long]
      :summary "id with path-parameters"
      (ok (r/get-room id))))
