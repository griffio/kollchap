(ns kollchap.client
  (:require
  [hiccup
   [page :refer [html5]]
   [page :refer [include-js]]]))


(defn index-page []
  (html5
   [:head
    [:title "Hello Kollchap"]
    (include-js "main.js")]
   [:body
    [:h1 "Hello Kollchap"]]))
