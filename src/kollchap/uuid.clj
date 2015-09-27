(ns kollchap.uuid
  (:import (java.util UUID)))
(defn random-uuid-str [] (str (UUID/randomUUID)))
(defn uuid-from-str [uuid-str] (UUID/fromString uuid-str))
