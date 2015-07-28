(ns kollchap.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [kollchap.character :as cr]
            [kollchap.location :as ln]
            [kollchap.monster :as mr]
            [kollchap.resources :as rs]
            [kollchap.room :as rm]
            [schema.core :as s]))

(defn middleware-add-self-link [handler]
  (fn add-base-link [{:keys [scheme server-name server-port uri] :as r}]
    (let [base-link (str (name scheme) "://" server-name ":" server-port) self-link (str base-link uri)]
      (handler (assoc r :base-link base-link :self-link self-link)))))

(defn entity-to-resource [path entity] (str path "/" (entity :id)))

(defn assoc-links-to-entity [entity links base-link]
  (let [entity-id-base-link (str base-link "/" (entity :id))]
    (assoc-in links [:_links :self :href] entity-id-base-link)))

(defn list-entities [_entities _self-links _base-links]
  (map (fn [_entity] (merge _entity (assoc-links-to-entity _entity _self-links _base-links))) _entities))

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
                        (ok {:character (cr/get-character id)
                             :_links    {:self {:href (-> req :self-link)}}}))

                  (GET* "/characters" {:as req}
                        :summary "list all characters"
                        :middlewares [middleware-add-self-link]
                        (ok {:characters (list-entities (cr/get-characters) {:_links {:self {:href ""}}} (req :self-link))}))

                  (POST* "/characters" {:as req}
                         :body [character cr/GameCharacter]
                         :return rs/CharacterResource
                         :summary "create new character resource from body"
                         :middlewares [middleware-add-self-link]
                         (let [new-character (cr/add! character)
                               new-resource-id (entity-to-resource (req :self-link) new-character)]
                           (created {:character new-character
                                     :_links    {:self     {:href new-resource-id},
                                                 :location {:href (str new-resource-id "/room")}}})))

                  (GET* "/characters/:id/location" {:as req}
                        :return rs/LocationResource
                        :path-params [id :- Long]
                        :summary "character id path-parameter"
                        :middlewares [middleware-add-self-link]
                        (let [character-location (ln/get-character-location id)]
                          (ok {:location character-location
                               :_links   {:self {:href (str (req :base-link) "/rooms/" (character-location :room-key))}}})))

                  (PUT* "/characters/:id/location" {:as req}
                        :body [location ln/Location]
                        :path-params [id :- Long]
                        :summary "character id path-parameter and room-key body"
                        :middlewares [middleware-add-self-link]
                        (ln/set-character-location id location)
                        (ok))

                  (GET* "/rooms/:key" {:as req}
                        :return rs/RoomResource
                        :path-params [key :- String]
                        :summary "room key path-parameter"
                        :middlewares [middleware-add-self-link]
                        (ok {:room   (rm/get-room key)
                             :_links {:self {:href (str (req :self-link))}}}))))