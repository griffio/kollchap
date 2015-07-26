(ns kollchap.resources
  (:require [schema.core :as s]
            [kollchap.character :as cr]
            [kollchap.room :as rm]
            [kollchap.link :as lk]
            [kollchap.location :as ln]))

(s/defschema CharacterResource {:character cr/GameCharacter
                                :_links    lk/Resource})

(s/defschema RoomResource {:room   rm/Room
                           :_links lk/Resource})

(s/defschema LocationResource {:location ln/Location
                               :_links   lk/Resource})