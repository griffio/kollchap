(ns kollchap.uuid)
(defn random-uuid-str [] (str (java.util.UUID/randomUUID)))
(defn uuid-from-str [uuid-str] (java.util.UUID/fromString uuid-str))
