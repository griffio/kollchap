(ns kollchap.resources
  (:require [schema.core :as s]
            [kollchap.character :as cr]
            [kollchap.link :as lk]
            [kollchap.monster :as mr]
            [kollchap.room :as rm]
            [kollchap.location :as ln]))

(s/defschema CharacterResource {:character cr/GameCharacter
                                :_links lk/CharacterLink})

(s/defschema LocationResource {:location ln/Location
                               :_links   lk/RoomLink})

(s/defschema MonsterResource {:monster mr/Monster
                              :_links   lk/MonsterLink})

(s/defschema RoomResource {:room   rm/Room
                           :_links lk/SelfLink})

(s/defschema KollchapResource {:characters lk/Link
                               :monsters lk/Link
                               :rooms lk/Link})

