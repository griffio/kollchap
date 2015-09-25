(ns kollchap.monster
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))

;; Domain
(def Alignment (s/enum :chaotic :neutral :lawful))

(def Monster {:id        s/Int
              :name      s/Str
              :alignment Alignment
              :count     s/Int
              :room-key  s/Str})
;; Repository
(defonce id-seq (atom 0))
(defonce monsters (atom (array-map)))

(defn get-monster [id] (@monsters id))
(defn get-monsters [] (-> monsters deref vals reverse))

(defn add! [new-monster]
  (let [id (swap! id-seq inc)
        monster (coerce! Monster (assoc new-monster :id id))]
    (swap! monsters assoc id monster)
    monster))
;; Data
(when (empty? @monsters)
  (add! {:name      "Orc"
         :alignment :chaotic
         :count     4
         :room-key  "1"})
  (add! {:name      "Giant Rat"
         :alignment :neutral
         :count     5
         :room-key  "2"})
  (add! {:name      "Green Slime"
         :alignment :neutral
         :count     1
         :room-key  "4"})
  (add! {:name     "Ghoul"
         :alignment :chaotic
         :count    1
         :room-key  "8"})
  (add! {:name      "Fourth-level Cleric"
         :alignment :chaotic
         :count     1
         :room-key  "9"})
  (add! {:name      "Rockman"
         :alignment :chaotic
         :count     2
         :room-key  "9a"})
  (add! {:name      "First-level Cleric"
         :alignment :chaotic
         :count     2
         :room-key  "10"})
  (add! {:name      "Second-level Cleric"
         :alignment :chaotic
         :count     1
         :room-key  "10"})
  (add! {:name      "Bugbear"
         :alignment :chaotic
         :count     1
         :room-key  "11"})
  (add! {:name      "Skeletons"
         :alignment :chaotic
         :count     3
         :room-key  "12"})
  (add! {:name      "Gecko"
         :alignment :neutral
         :count     1
         :room-key  "12"})
  (add! {:name      "Third-level Cleric"
         :alignment :chaotic
         :count     1
         :room-key  "12"})
  (add! {:name      "Zombie"
         :alignment :chaotic
         :count     2
         :room-key  "13"})
  (add! {:name      "Giant Centipede"
         :alignment :neutral
         :count     1
         :room-key  "14"})
  (add! {:name      "Goblin"
         :alignment :chaotic
         :count     4
         :room-key  "19"})
  (add! {:name      "Cave Locust"
         :alignment :neutral
         :count     2
         :room-key  "20"})
  (add! {:name      "Lizardman"
         :alignment :neutral
         :count     1
         :room-key  "23"}))
