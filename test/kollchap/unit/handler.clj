(ns kollchap.unit.handler
  (:require [midje.sweet :refer :all]
            [kollchap.character :as cr]
            [kollchap.handler :as hr]))

(fact-group
  :unit

  (fact "returns path to characters id from entity"
        (let [path-link (hr/entity-to-resource (cr/get-character "16abb36f-c1fd-4cc5-99c1-2261fd69d4e3") "/characters")]
          path-link => "/characters/16abb36f-c1fd-4cc5-99c1-2261fd69d4e3"))

  (fact "returns path to characters id from entity"
        (let [entity-links (hr/assoc-links-to-entity (cr/get-character "16abb36f-c1fd-4cc5-99c1-2261fd69d4e3") {:_links {:self     {:href "/characters"}
                                                                                    :location {:href "/location"}}})]
          entity-links => {:_links {:self {:href "/characters/16abb36f-c1fd-4cc5-99c1-2261fd69d4e3"} :location {:href "/characters/16abb36f-c1fd-4cc5-99c1-2261fd69d4e3/location"}}})))
