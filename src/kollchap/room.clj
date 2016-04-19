(ns kollchap.room
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))
;; Domain
(def Room {:key         s/Str
           :name        s/Str
           :description s/Str})
;; Repository
(defonce rooms (atom (array-map)))

(defn get-room [room-key] (@rooms room-key))
(defn get-rooms [] (-> rooms deref vals reverse))

(defn add! [new-room]
  (let [room-key (new-room :key)
        room (coerce! Room new-room)]
    (swap! rooms assoc room-key room)
    room))
;; Data
(when (empty? @rooms)
  (add! {:key         "1"
         :name        "Orc Guardroom"
         :description "In this room are two sets of bunk beds..."})
  (add! {:key         "2"
         :name        "Rubbish Dump"
         :description "This room is used by the inhabitants..."})
  (add! {:key         "3"
         :name        "Floorless Room"
         :description "This room was once the entrance to the main part..."})
  (add! {:key         "4"
         :name        "Corridor Trap"
         :description "The Slime is clinging to the west wall and..."})
  (add! {:key         "5"
         :name        "Storeroom"
         :description "This room was once a storeroom for the temple..."})
  (add! {:key         "6"
         :name        "Healing Pool"
         :description "This pool is circular with a radius of..."})
  (add! {:key         "7"
         :name        "Coffin Storeroom"
         :description "This room is bare except for fifteen plain wooden coffins..."})
  (add! {:key         "8"
         :name        "Odric's Bedroom"
         :description "This room has a sumptuosly furnished bed..."})
  (add! {:key         "9"
         :name        "The Office"
         :description "This room serves as Odric's office..."})
  (add! {:key         "9a"
         :name        "Collapsed Room"
         :description "The Rockmen are rummaging around in this room..."})
  (add! {:key         "10"
         :name        "UnderPriests' Room"
         :description "This room is used by the three Clerics as a base..."})
  (add! {:key         "11"
         :name        "Room of Absolution"
         :description "This room is almost wholly bare except for six large..."})
  (add! {:key         "12"
         :name        "Great Sacrifice Chamber"
         :description "This room is vast..."})
  (add! {:key         "13"
         :name        "The Cells"
         :description "There are three cells..."})
  (add! {:key         "14"
         :name        "Treasure Chamber"
         :description "The door to this room is locked..."})
  (add! {:key         "15"
         :name        "Changing Room"
         :description "This room is used by Odric for changing into his sacrifice robes..."})
  (add! {:key         "16"
         :name        "Old Waiting Room"
         :description "This room was once a waiting room for the oracle..."})
  (add! {:key         "17"
         :name        "Oracle Room"
         :description "This room used to be an oracle room..."})
  (add! {:key         "18"
         :name        "Pit Trap"
         :description "At this point the floor is covering a pit..."})
  (add! {:key         "19"
         :name        "Goblins' Room"
         :description "This room is very stark, with only straw for bedding, on the floor..."})
  (add! {:key         "20"
         :name        "Cave"
         :description "This room is a cave littered with rocks and boulders..."})
  (add! {:key         "21"
         :name        "Statue Room"
         :description "The door in the east room is a sliding block..."})
  (add! {:key         "22"
         :name        "Meditation Room"
         :description "This room was originally a meditation room..."})
  (add! {:key         "23"
         :name        "Flooded Storeroom"
         :description "The stairs leading down are wet and so anyone going down them..."}))

