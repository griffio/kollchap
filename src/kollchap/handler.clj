(ns kollchap.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [kollchap.domain :refer :all]
            [schema.core :as s]))

(defapi app
  (swagger-ui)
  (swagger-docs
    {:info {:title "The Shrine of Kollchap Api"
            :description "Shrine of Kollchap - taken from the book What is Dungeons and Dragons?"}
     :tags [{:name "kollchap", :description "kollchap api "}]})

  (context* "/" []
    :tags ["kollchap"]

    (GET* "/characters/:id" []
      :return Player 
      :path-params [id :- Long]
      :summary "id path-parameter"
      (ok get-character id))

    (GET* "/rooms/:id" []
      :return Room
      :path-params [id :- Long]
      :summary "id with path-parameters"
      (ok (get-room id)))))
