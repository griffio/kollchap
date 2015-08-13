(ns kollchap.unit.handler
  (:require [midje.sweet :refer :all]
            [kollchap.character :as cr]
            [kollchap.handler :as hr]))

(fact-group
  :unit

  (fact "returns path to characters id from entity"
        (let [path-link (hr/entity-to-resource (cr/get-character 1) "/characters")]
          path-link => "/characters/1"))

  (fact "returns path to characters id from entity"
        (let [entity-links (hr/assoc-links-to-entity (cr/get-character 1) {:_links {:self     {:href "/characters"}
                                                                                    :location {:href "/location"}}})]
          entity-links => {:_links {:self {:href "/characters/1"} :location {:href "/characters/1/location"}}})))