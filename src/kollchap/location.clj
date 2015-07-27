(ns kollchap.location
  (:require [kollchap.character :as cr]
            [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))
; Domain
(s/defschema Location {:room-key String})

(defn get-character-location [character-id]
  (let [located-character (cr/get-character character-id) located-room-key (located-character :room-key)]
    (coerce! Location {:room-key located-room-key})))

(defn set-character-location [character-id, location]
  (let [character (cr/get-character character-id) updated-character (merge character location)]
    (cr/update! updated-character)))
