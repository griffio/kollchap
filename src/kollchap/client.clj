(ns kollchap.client
  (:require
    [hiccup
     [page :refer [html5]]
     [page :refer [include-js]]]))

(defn index-page []
  (html5
    [:head
     [:title "Hello Kollchap"]]
    [:body
     [:h1 "Hello Kollchap"]
     [:section {:id "kollchap"}]
     [:h2 "Characters"]
     [:section {:id "characters"}]
     (include-js "/js/main.js")]))
