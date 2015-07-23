(ns kollchap.location
  (:require [kollchap.character :as c]
            [kollchap.room :as r]))

(defn get-character-location [character-id] (r/get-room 5))

