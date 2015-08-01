(ns kollchap.location
  (:require [kollchap.character :as cr]
            [kollchap.monster :as mr]
            [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))
; Domain
(s/defschema Location {:room-key String})

(defn- get-location [fn-get-entity, entity-id]
  (let [located-character (fn-get-entity entity-id) located-room-key (located-character :room-key)]
    (coerce! Location {:room-key located-room-key})))

(defn get-character-location [character-id]
  (get-location cr/get-character character-id))

(defn get-monster-location [monster-id]
  (get-location mr/get-monster monster-id))

(defn set-character-location [character-id, location]
  (let [character (cr/get-character character-id) updated-character (merge character location)]
    (cr/update! updated-character)))
