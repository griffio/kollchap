(ns kollchap.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [kollchap.character :as c]
            [kollchap.monster :as m]
            [kollchap.resources :as rs]
            [kollchap.room :as r]
            [schema.core :as s]))

(defn middleware-add-self-link [handler]
  (fn add-base-link [{:keys [scheme server-name server-port uri] :as r}]
    (let [link (str (name scheme) "://" server-name ":" server-port uri)]
      (handler (assoc r :self-link link)))))

(defapi app
  (swagger-ui)
  (swagger-docs
    {:info {:title "The Shrine of Kollchap Api"
            :description "Shrine of Kollchap - taken from the book What is Dungeons and Dragons?"}
     :tags [{:name "kollchap", :description "kollchap api "}]})

  (context* "/kollchap" []
    :tags ["kollchap"]

    (GET* "/characters/:id" {:as req} 
      :return rs/CharacterResource 
      :path-params [id :- Long]
      :summary "character id path-parameter"
      :middlewares [middleware-add-self-link]
      (ok {:character (c/get-character id)
           :_links {:self {:href (-> req :self-link)}}}))

    (POST* "/characters" {:as req}
      :body [character c/GameCharacter]
      :return rs/CharacterResource
      :summary "create new character resource from body"
      :middlewares [middleware-add-self-link]
     (let [new-character (c/add! character)]
      (created {:character new-character :_links {:self {:href (str (req :self-link) "/" (new-character :id))}}})))

    (GET* "/rooms/:id" {:as req}
      :return rs/RoomResource
      :path-params [id :- Long]
      :summary "room id path-parameter"
      :middlewares [middleware-add-self-link]
      (ok {:room (r/get-room id)
           :_links {:self {:href (str (req :self-link))}}}))

    (POST* "/rooms" {:as req}
      :body [room r/Room]
      :return rs/RoomResource
      :summary "create new room resource from body"
      :middlewares [middleware-add-self-link]
      (let [new-room (r/add! room)]
      (created {:room new-room :_links {:self {:href (str (req :self-link) "/" (new-room :id))}}})))))
