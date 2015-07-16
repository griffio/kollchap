(ns kollchap.resources
  (:require [schema.core :as s]
            [kollchap.character :as c]
            [kollchap.monster :as m]
            [kollchap.room :as r]
            [kollchap.link :as l]))

(s/defschema CharacterResource {:character c/GameCharacter 
                                :_links l/Resource})

(defn character-resource [game-character](game-character))

