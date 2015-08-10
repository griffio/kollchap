(ns kollchap.client
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(defn ^:export init []
  (go
    (let [response (<! (http/get "/kollchap/characters"))]
      (js/alert (:body response)))))

(init)
