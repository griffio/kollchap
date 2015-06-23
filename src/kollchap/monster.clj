(ns kollchap.monster
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))

;; Domain


(s/defschema Monster {:id Long
                      :name String
                      :location String})

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
  (add! {:name "4 Orcs" 
         :location "..."}))
