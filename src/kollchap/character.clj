(ns kollchap.character
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]
            [kollchap.uuid :as uid])
  (:import (java.util UUID)))

(defmulti get-character class)

;; Domain
(def GameCharacter {:uuid                      s/Uuid
                    :name                      s/Str
                    :background                s/Str
                    (s/optional-key :room-key) s/Str})
;; Repository
(defonce characters (atom (array-map)))

(defmethod get-character String [character-id] (@characters (UUID/fromString character-id)))
(defmethod get-character UUID [character-id] (@characters character-id))

(defn get-characters [] (-> characters deref vals reverse))

(defn add! [new-character]
  (let [uuid (uid/random-uuid-str)
        character (coerce! GameCharacter (assoc new-character :uuid uuid))]
    (swap! characters assoc uuid character)
    character))

(defn update! [updated-character]
  (let [character (coerce! GameCharacter updated-character)]
    (swap! characters assoc (:uuid character) character)
    (get-character (:uuid character))))
;; Data
(when (empty? @characters)
  (update! {:uuid       "16abb36f-c1fd-4cc5-99c1-2261fd69d4e3"
            :name       "Slammer Kyntire"
            :background "First-level Fighter searching for the Sword of the Sorcerer."})
  (update! {:uuid       "e098065f-9bf0-498d-9021-5fd0cb97401b"
            :name       "Hotfa Nap"
            :background "First-level Sorceress from a nomad tribe in the Mesta Desert."})
  (update! {:uuid       "374de75f-9406-4475-920d-75a3fd842d06"
            :name       "Gripper 'The Skin' Longshank"
            :background "First-level Thief from a tribe on the Albine empire border."})
  (update! {:uuid       "35b8e946-4042-4acf-9874-3bade5df527d"
            :name       "Zhod Thobi"
            :background "First-level Cleric joins party as N.P.C and receives equal share of treasure."})
  (update! {:uuid       "b5d2a54a-851e-47f9-92b3-eecc54cc95ec"
            :name       "Belisarius"
            :background "First-level Thief N.P.C survivor. Currently hiding, if located will join party."
            :room-key   "5"})
  (update! {:uuid       "34e23d2c-7a03-48ec-b69c-59e658fcbd09"
            :name       "Rosa Dobbit"
            :background "First-level Fighter, N.P.C survivor. Currently captive, if released will join party."
            :room-key   "12"}))