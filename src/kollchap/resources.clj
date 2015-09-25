(ns kollchap.resources
  (:require [kollchap.character :as cr]
            [kollchap.link :as lk]
            [kollchap.monster :as mr]
            [kollchap.room :as rm]
            [kollchap.location :as ln]))

(def CharacterResource {:character cr/GameCharacter
                        :_links lk/CharacterLink})

(def LocationResource {:location ln/Location
                       :_links   lk/RoomLink})

(def MonsterResource {:monster mr/Monster
                      :_links   lk/MonsterLink})

(def RoomResource {:room   rm/Room
                   :_links lk/SelfLink})

(def KollchapResource {:characters lk/Link
                       :monsters lk/Link
                       :rooms lk/Link})

