(ns kollchap.link
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))

(s/defschema Link {:href s/Str})

(s/defschema SelfLink {:self Link})

(s/defschema LocationLink {:location Link})

(s/defschema RoomLink {:self Link :room Link})

(s/defschema CharacterLink {:self Link :location Link})

(s/defschema MonsterLink {:self Link :location Link})

