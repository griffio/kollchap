(ns kollchap.room
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))

;; Domain


(s/defschema Room {:id          Long
                   :name        String
                   :description String})

;; Repository

(defonce id-seq (atom 0))
(defonce rooms (atom (array-map)))

(defn get-room [room-id] (@rooms room-id))
(defn get-rooms [] (-> rooms deref vals reverse))

(defn add! [new-room]
  (let [id (swap! id-seq inc)
        room (coerce! Room (assoc new-room :id id))]
    (swap! rooms assoc id room)
    room))

;; Data

(when (empty? @rooms)
  (add! {:name        "Orc Guardroom"
         :description "In this room are two sets of bunk beds..."})
  (add! {:name        "Rubbish Dump"
         :description "This room is used by the inhabitants..."})
  (add! {:name        "Floorless Room"
         :description "This room was once the entrance to the main part..."})
  (add! {:name        "Corridor Trap"
         :description "The Slime is clinging to the west wall and..."})
  (add! {:name        "Storeroom"
         :description "This room was once a storeroom for the temple..."})
  (add! {:name        "Healing Pool"
         :description "This pool is circular with a radius of..."})
  (add! {:name        "Coffin Storeroom"
         :description "This room is bare except for fifteen plain wooden coffins..."})
  (add! {:name        "Odric's Bedroom"
         :description "This room has a sumptuosly furnished bed..."})
  (add! {:name        "The Office"
         :description "This room serves as Odric's office..."})
  (add! {:name        "Collapsed Room"
         :description "The Rockmen are rummaging around in this room..."})
  (add! {:name        "UnderPriests' Room"
         :description "This room is used by the three Clerics as a base..."})
  (add! {:name        "Room of Absolution"
         :description "This room is almost wholly bare except for six large..."})
  (add! {:name        "Great Sacrifice Chamber"
         :description "This room is vast..."})
  (add! {:name        "The Cells"
         :description "There are three cells..."})
  (add! {:name        "Treasure Chamber"
         :description "The door to this room is locked..."})
  (add! {:name        "Changing Room"
         :description "This room is used by Odric for changing into his sacrifice robes..."})
  (add! {:name        "Old Waiting Room"
         :description "This room was once a waiting room for the oracle..."})
  (add! {:name        "Oracle Room"
         :description "This room used to be an oracle room..."})
  (add! {:name        "Pit Trap"
         :description "At this point the floor is covering a pit..."})
  (add! {:name        "Goblins' Room"
         :description "This room is very stark, with only straw for bedding, on the floor..."})
  (add! {:name        "Cave"
         :description "This room is a cave littered with rocks and boulders..."})
  (add! {:name        "Statue Room"
         :description "The door in the east room is a sliding block..."})
  (add! {:name        "Meditation Room"
         :description "This room was originally a meditation room..."})
  (add! {:name        "Flooded Storeroom"
         :description "The stairs leading down are wet and so anyone going down them..."})
  )