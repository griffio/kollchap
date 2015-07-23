(ns kollchap.character
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))

;; Domain


(s/defschema GameCharacter {:id Long
                            :name String
                            :background String
                            :room-id Long})

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
  (add! {:name "Slammer Kyntire" 
         :background "Fighter - searching for the Sword of the Sorcerer."
         :room-id 0})
  (add! {:name "Hotfa Nap" 
         :background "Sorceress - from a nomad tribe in the Mesta Desert/"
         :room-id 0})
  (add! {:name "Gripper 'The Skin' Longshank"
         :background "Thief - from a tribe on the Albine empire border."
         :room-id 0})
  (add! {:name "Belisarius"
         :background "Thief - NPC survivor"
         :room-id 5}))
