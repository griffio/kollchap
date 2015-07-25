(ns kollchap.location
  (:require [kollchap.character :as c]
            [kollchap.room :as r]))

(defn get-character-location [character-id]
  (let [located-character (c/get-character character-id)]
  (r/get-room (located-character :room-key))))

