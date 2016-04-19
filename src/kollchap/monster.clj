(ns kollchap.monster
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]
            [kollchap.uuid :as uid]))

(defmulti get-monster class)

;; Domain
(def Alignment (s/enum :chaotic :neutral :lawful))

(def Monster {:uuid      s/Uuid
              :name      s/Str
              :alignment Alignment
              :count     s/Int
              :room-key  s/Str})
;; Repository
(defonce monsters (atom (array-map)))

(defmethod get-monster java.lang.String [monster-uuid] (@monsters (java.util.UUID/fromString monster-uuid)))
(defmethod get-monster java.util.UUID [monster-uuid] (@monsters monster-uuid))

(defn get-monsters [] (-> monsters deref vals reverse))

(defn add! [new-monster]
  (let [uuid (uid/random-uuid-str)
        monster (coerce! Monster (assoc new-monster :uuid uuid))]
    (swap! monsters assoc uuid monster)
    monster))

(defn update! [updated-monster]
  (let [monster (coerce! Monster updated-monster)]
    (swap! monsters assoc (:uuid monster) monster)
    (get-monster (:uuid monster))))
;; Data
(when (empty? @monsters)
  (update! {:uuid      "aa9d8dbc-592d-4017-b55d-63ce49f73c5e"
            :name      "Orc"
            :alignment :chaotic
            :count     4
            :room-key  "1"})
  (update! {:uuid      "0eec33d1-4a3a-4456-8fad-178f814514ce"
            :name      "Giant Rat"
            :alignment :neutral
            :count     5
            :room-key  "2"})
  (update! {:uuid      "beae9c2f-18d8-46ad-b30c-708ac4422112"
            :name      "Green Slime"
            :alignment :neutral
            :count     1
            :room-key  "4"})
  (update! {:uuid      "9976ff96-eb25-48ed-b724-eb5fb44419a4"
            :name      "Ghoul"
            :alignment :chaotic
            :count     1
            :room-key  "8"})
  (update! {:uuid      "f70658ff-b127-45b8-bd67-9fa48d141744"
            :name      "Fourth-level Cleric"
            :alignment :chaotic
            :count     1
            :room-key  "9"})
  (update! {:uuid      "c727f3ff-62cb-4a99-8bb9-efaa9193fb16"
            :name      "Rockman"
            :alignment :chaotic
            :count     2
            :room-key  "9a"})
  (update! {:uuid      "a2148156-2e22-4e27-af3c-d101a52d3382"
            :name      "First-level Cleric"
            :alignment :chaotic
            :count     2
            :room-key  "10"})
  (update! {:uuid      "57f8e571-252f-419a-a2e0-f700580ea14c"
            :name      "Second-level Cleric"
            :alignment :chaotic
            :count     1
            :room-key  "10"})
  (update! {:uuid      "99e8e703-fffe-4e22-ba22-a0551c447835"
            :name      "Bugbear"
            :alignment :chaotic
            :count     1
            :room-key  "11"})
  (update! {:uuid      "ba2b9a2c-5eac-4187-8bfb-24f151b04853"
            :name      "Skeletons"
            :alignment :chaotic
            :count     3
            :room-key  "12"})
  (update! {:uuid      "6a862a71-5670-4353-99d8-e50b1cb933d8"
            :name      "Gecko"
            :alignment :neutral
            :count     1
            :room-key  "12"})
  (update! {:uuid      "6be829b9-860d-48cc-b09b-37a6d3f7dde3"
            :name      "Third-level Cleric"
            :alignment :chaotic
            :count     1
            :room-key  "12"})
  (update! {:uuid      "cf997e1a-f700-43aa-a4dd-5dadf9a1989c"
            :name      "Zombie"
            :alignment :chaotic
            :count     2
            :room-key  "13"})
  (update! {:uuid      "26578c72-861b-402c-aae6-9c6157be77fc"
            :name      "Giant Centipede"
            :alignment :neutral
            :count     1
            :room-key  "14"})
  (update! {:uuid      "6e6c3191-15a6-4c04-9946-93ed2a37ef2d"
            :name      "Goblin"
            :alignment :chaotic
            :count     4
            :room-key  "19"})
  (update! {:uuid      "df08e008-09ea-465e-8268-a158c95a8490"
            :name      "Cave Locust"
            :alignment :neutral
            :count     2
            :room-key  "20"})
  (update! {:uuid      "78dc99e5-fb73-41f9-bccb-83c30666d5d0"
            :name      "Lizardman"
            :alignment :neutral
            :count     1
            :room-key  "23"}))