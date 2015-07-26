(ns kollchap.character
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))

;; Domain
(s/defschema GameCharacter {:id         Long
                            :name       String
                            :background String
                            (s/optional-key :room-key)  String})
;; Repository
(defonce id-seq (atom 0))
(defonce characters (atom (array-map)))

(defn get-character [character-id] (@characters character-id))
(defn get-characters [] (-> characters deref vals reverse))

(defn add! [new-character]
  (let [id (swap! id-seq inc)
        character (coerce! GameCharacter (assoc new-character :id id))]
    (swap! characters assoc id character)
    character))

;; Data
(when (empty? @characters)
  (add! {:name       "Slammer Kyntire"
         :background "First-level Fighter searching for the Sword of the Sorcerer."})
  (add! {:name       "Hotfa Nap"
         :background "First-level Sorceress from a nomad tribe in the Mesta Desert."})
  (add! {:name       "Gripper 'The Skin' Longshank"
         :background "First-level Thief from a tribe on the Albine empire border."})
  (add! {:name       "Zhod Thobi"
         :background "First-level Cleric joins party as N.P.C and receives equal share of treasure."})
  (add! {:name       "Belisarius"
         :background "First-level Thief N.P.C survivor. Currently hiding, if located will join party."
         :room-key    "5"})
  (add! {:name       "Rosa Dobbit"
         :background "First-level Fighter, N.P.C survivor. Currently captive, if released will join party."
         :room-key    "12"}))
