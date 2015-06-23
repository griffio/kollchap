(ns kollchap.character
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))

;; Domain


(s/defschema GameCharacter {:id Long
                            :name String
                            :background String})

;; Repository

(defonce id-seq (atom 0))
(defonce characters (atom (array-map)))

(defn get-character [id] (@characters id))
(defn get-characters [] (-> characters deref vals reverse))

(defn add! [new-character]
  (let [id (swap! id-seq inc)
        character (coerce! GameCharacter (assoc new-character :id id))]
    (swap! characters assoc id character)
    character))

;; Data

(when (empty? @characters)
  (add! {:name "Slammer Kyntire" 
         :background "Fighter..."})
  (add! {:name "Hotfa Nap" 
         :background "Sorceress..."})
  (add! {:name "Gripper Longshank"
         :background "Thief..."}))
