(ns kollchap.location
  (:require [kollchap.character :as cr]
            [kollchap.monster :as mr]
            [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))
; Domain
(def Location {:room-key s/Str})

(defn- get-location [fn-get-entity, entity-uuid]
  (let [located-character (fn-get-entity entity-uuid) located-room-key (located-character :room-key)]
    (coerce! Location {:room-key located-room-key})))

(defn get-character-location [character-uuid]
  (get-location cr/get-character character-uuid))

(defn get-monster-location [monster-uuid]
  (get-location mr/get-monster monster-uuid))

(defn set-character-location [character-uuid, location]
  (let [character (cr/get-character character-uuid) updated-character (merge character location)]
    (cr/update! updated-character)))
