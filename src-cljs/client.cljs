(ns kollchap.client
  (:require-macros [secretary.core :refer [defroute]]
                   [cljs.core.async.macros :refer [go]])
  (:require [goog.events :as events]
            [goog.dom :as dom]
            [cognitect.transit :as transit]
            [secretary.core :as secretary]
            [cljs.core.async :refer [<! put! chan]  ])
  (:import [goog.net XhrIo]
            goog.History
            goog.net.EventType
           [goog.events EventType]))

(enable-console-print!)

(def json-r (transit/reader :json-verbose))

(def ^:private meths
  {:get "GET"
   :put "PUT"
   :post "POST"
   :delete "DELETE"})

(defn json-xhr [{:keys [method url data on-complete]}]
  (let [xhr (XhrIo.)]
    (events/listen xhr goog.net.EventType.COMPLETE
                   (fn [e]
                     (on-complete (str(.getResponseText xhr)))))
    (. xhr
       (send url (meths method) (when data (pr-str data))
             #js {"Content-Type" "application/json"}))))


(def app (dom/getElement "kollchap"))
(def characters-url "/kollchap/characters")
(def home-html
  (str "<h1>Kollchap Characters:</h1>"
       "<section>"
       "<button id=\"js-findButton\">Find</button>"
       "<ul id=\"js-characters\"></ul>"
       "</section>"))


(defn set-html! [el content]
  (set! (.-innerHTML el) content))

(defn render-results [json-results]
  (let [results (transit/read json-r json-results) characters (results "characters")]
    (println characters)
    (reduce
      (fn [acc result]
        (str acc "<li>" result "</li>"))
      "" (first characters))))

(defn listen [el type]
  (let [out (chan)]
    (events/listen el type (fn [e] (put! out e)))
    out))

(defn   get-data [url]
  (let [out (chan)]
    (json-xhr
      {:method :get
       :url url
       :on-complete (fn [result] (put! out result))})
    out))

(defroute home-path "/" []
          (set-html! app home-html)
          (let [clicks (listen (dom/getElement "js-findButton") "click")]
            (go (while true
                  (<! clicks)
                  (let [uri characters-url
                        results (<! (get-data uri))]
                    (set-html! (dom/getElement "js-characters")
                               (render-results results)))))))

(defroute "*" []
          (set-html! app "<h1>Path Not Found</h1>"))

(defn main []
  (secretary/set-config! :prefix "#")
  (let [history (History.)]
    (events/listen history "navigate"
                   (fn [event]
                     (secretary/dispatch! (.-token event))))
    (.setEnabled history true)))

(main)
