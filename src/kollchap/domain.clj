(ns kollchap.domain
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))

;; Domain

(s/defschema Player {:id Long
                     :name String
                     :background String})

(s/defschema Room {:id Long
                   :name String
                   :description String})

;; Repository

(defonce id-seq (atom 0))
(defonce characters (atom (array-map)))
(defonce rooms (atom (array-map)))

(defn get-character [id] (@characters id))
(defn get-characters [] (-> characters deref vals reverse))

(defn get-room [id] (@rooms id))
(defn get-rooms [] (-> rooms deref vals reverse))

(defn add! [new-room]
  (let [id (swap! id-seq inc)
        room (coerce! Room (assoc new-room :id id))]
    (swap! rooms assoc id room)
    room))

;; Data

(when (empty? @rooms)
  (add! {:name "Orc Guardroom" 
         :description "In this room are two sets of bunk beds..."})
  (add! {:name "Rubbish Dump" 
         :description "This room is used by the inhabitants..."})
  (add! {:name "Floorless Room"
         :description "This room was once the entrance to the main part..."})
  (add! {:name "Corridor Trap"
         :description "The Slime is clinging to the west wall and..."})
  (add! {:name "Storeroom"
         :description "This room was once a storeroom for the temple..."})
  (add! {:name "Healing Pool"
         :description "This pool is circular with a radius of..."}))
