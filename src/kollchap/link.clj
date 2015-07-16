(ns kollchap.link
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))

(s/defschema Link {:href s/Str})

(s/defschema Resource {:self Link})
