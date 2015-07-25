(ns kollchap.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [kollchap.character :as c]
            [kollchap.location :as l]
            [kollchap.monster :as m]
            [kollchap.resources :as rs]
            [kollchap.room :as r]
            [schema.core :as s]))

(defn middleware-add-self-link [handler]
  (fn add-base-link [{:keys [scheme server-name server-port uri] :as r}]
    (let [base-link (str (name scheme) "://" server-name ":" server-port) self-link (str base-link uri)]
      (handler (assoc r :base-link base-link :self-link self-link)))))

(defn entity-to-resource [path entity] (str path "/" (entity :id)))

(defapi app
        (swagger-ui)
        (swagger-docs
          {:info {:title       "The Shrine of Kollchap Api"
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
                             :_links    {:self {:href (-> req :self-link)}}}))

                  (POST* "/characters" {:as req}
                         :body [character c/GameCharacter]
                         :return rs/CharacterResource
                         :summary "create new character resource from body"
                         :middlewares [middleware-add-self-link]
                         (let [new-character (c/add! character)
                               new-resource-id (entity-to-resource (req :self-link) new-character)]
                           (created {:character new-character
                                     :_links    {:self     {:href new-resource-id},
                                                 :location {:href (str new-resource-id "/room")}}})))

                  (GET* "/characters/:id/room" {:as req}
                        :return rs/RoomResource
                        :path-params [id :- Long]
                        :summary "character id path-parameter"
                        :middlewares [middleware-add-self-link]
                        (let [location-room (l/get-character-location id)]
                          (ok {:room   location-room
                               :_links {:self {:href (str (req :base-link) "/rooms/" (location-room :key))}}})))

                  (GET* "/rooms/:key" {:as req}
                        :return rs/RoomResource
                        :path-params [key :- String]
                        :summary "room key path-parameter"
                        :middlewares [middleware-add-self-link]
                        (ok {:room   (r/get-room key)
                             :_links {:self {:href (str (req :self-link))}}}))))