(ns kollchap.handler
  (:require [qbits.jet.server :refer [run-jetty]]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [compojure.api.middleware :refer [public-resources]]
            [kollchap.client :as ct]
            [kollchap.character :as cr]
            [kollchap.location :as ln]
            [kollchap.monster :as mr]
            [kollchap.resources :as rs]
            [kollchap.room :as rm]
            [schema.core :as s]))

(defn middleware-add-self-link [handler]
  "Adds a :base-link and :self-link to a handler request map"
  (fn add-base-link [{:keys [scheme server-name server-port uri] :as r}]
    (let [base-link (str (name scheme) "://" server-name ":" server-port) self-link (str base-link uri)]
      (handler (assoc r :base-link base-link :self-link self-link)))))

(defn entity-to-resource [entity path]
  "Returns a path/entity:id string"
  (str path "/" (entity :id)))

(defn assoc-links-to-entity [entity links-self-href]
  "Provides the href value to self and location using the entity resource id. Returns map of populated links"
  (let [entity-id-base-link (entity-to-resource entity (get-in links-self-href [:_links :self :href]))]
    (-> links-self-href
        (assoc-in [:_links :self :href] entity-id-base-link)
        (assoc-in [:_links :location :href] (str entity-id-base-link "/location")))))

  (defn list-entities [entities links-self-href]
    "Associate each entity in the list with its self href resource '_links -> self -> href'"
    (map (fn [entity] (merge entity (assoc-links-to-entity entity links-self-href))) entities))

  (defapi app
          (middlewares [public-resources])
          (swagger-ui)
          (swagger-docs
            {:info {:title       "The Shrine of Kollchap Api"
                    :description "Shrine of Kollchap - taken from the book What is Dungeons and Dragons?"}
             :tags [{:name "kollchap", :description "kollchap api "}]})

          (context* "/kollchap" []
                    :tags ["kollchap"]

                    (GET* "/index.html" []
                          :no-doc true
                          (ok (ct/index-page)))

                    (GET* "/" {:as req}
                          :return rs/KollchapResource
                          :middlewares [middleware-add-self-link]
                          :summary "api resources"
                          (ok {:characters {:href (str (:self-link req) "/location")}
                               :monsters   {:href (str (:self-link req) "/monsters")}
                               :rooms      {:href (str (:self-link req) "/rooms")}}))

                    (GET* "/characters/:id" {:as req}
                          :return rs/CharacterResource
                          :path-params [id :- String]
                          :summary "character id path-parameter"
                          :middlewares [middleware-add-self-link]
                          (ok {:character (cr/get-character id)
                               :_links    {:self     {:href (:self-link req)}
                                           :location {:href (str (:self-link req) "/location")}}}))

                    (GET* "/characters" {:as req}
                          :summary "list all characters"
                          :middlewares [middleware-add-self-link]
                          (ok {:characters (list-entities (cr/get-characters) {:_links {:self     {:href (:self-link req)}
                                                                                        :location {:href "/location"}}})
                               :_links     {:self {:href (:self-link req)}}}))

                    (POST* "/characters" {:as req}
                           :body [character cr/GameCharacter]
                           :return rs/CharacterResource
                           :summary "create new character resource from body"
                           :middlewares [middleware-add-self-link]
                           (let [new-character (cr/add! character)
                                 new-resource-link (entity-to-resource new-character (:self-link req))]
                             (created {:character new-character
                                       :_links    {:self     {:href new-resource-link}
                                                   :location {:href (str new-resource-link "/location")}}})))

                    (GET* "/characters/:id/location" {:as req}
                          :return rs/LocationResource
                          :path-params [id :- String]
                          :summary "character id path-parameter"
                          :middlewares [middleware-add-self-link]
                          (let [character-location (ln/get-character-location id)]
                            (ok {:location character-location
                                 :_links   {:self {:href (:self-link req)}
                                            :room {:href (str (:base-link req) "/rooms/" (character-location :room-key))}}})))

                    (PUT* "/characters/:id/location" {:as req}
                          :body [location ln/Location]
                          :path-params [id :- String]
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
                               :_links {:self {:href (:self-link req)}}}))

                    (GET* "/monsters" {:as req}
                          :summary "list all monsters"
                          :middlewares [middleware-add-self-link]
                          (ok {:monsters (list-entities (mr/get-monsters) {:_links {:self     {:href (:self-link req)}
                                                                                    :location {:href "/location"}}})
                               :_links   {:self {:href (:self-link req)}}}))

                    (GET* "/monster/:id" {:as req}
                          :return rs/MonsterResource
                          :path-params [id :- Long]
                          :summary "monster id path-parameter"
                          :middlewares [middleware-add-self-link]
                          (ok {:monster (mr/get-monster id)
                               :_links  {:self     {:href (:self-link req)}
                                         :location {:href (str (:self-link req) "/location")}}}))

                    (GET* "/monster/:id/location" {:as req}
                          :return rs/LocationResource
                          :path-params [id :- Long]
                          :summary "monster id path-parameter"
                          :middlewares [middleware-add-self-link]
                          (let [monster-location (ln/get-monster-location id)]
                            (ok {:location monster-location
                                 :_links   {:self {:href (:self-link req)}
                                            :room {:href (str (:base-link req) "/rooms/" (monster-location :room-key))}}})))))

  (defn -main [& args]
    (run-jetty {:ring-handler app :port 8080}))
