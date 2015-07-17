(ns kollchap.resources
  (:require [schema.core :as s]
            [kollchap.character :as c]
            [kollchap.monster :as m]
            [kollchap.room :as r]
            [kollchap.link :as l]))

(s/defschema CharacterResource {:character c/GameCharacter 
                                :_links l/Resource})
(s/defschema RoomResource {:room r/Room
                           :_links l/Resource})


