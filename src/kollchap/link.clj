(ns kollchap.link
  (:require [schema.core :as s]))

(def Link {:href s/Str})

(def SelfLink {:self Link})

(def LocationLink {:location Link})

(def RoomLink {:self Link :room Link})

(def CharacterLink {:self Link :location Link})

(def MonsterLink {:self Link :location Link})
