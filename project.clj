(defproject metosin/compojure-api-examples "0.21.0"
  :description "Shrine of Kollchap api - taken from the book What is Dungeons & Dragons?"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.9.0"]
                 [metosin/compojure-api "0.21.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [com.cognitect/transit-cljs "0.8.220"]
                 [secretary "1.2.3"]
                 [cc.qbits/jet "0.6.6"]
                 [lein-light-nrepl "0.1.0"]
                 [midje "1.6.3"]
                 [hiccup "1.0.5"]]
  :main kollchap.handler
  :plugins [[lein-cljsbuild "1.0.4"]]
  :cljsbuild {:builds [{:id           "kollchap"
                        :source-paths ["src-cljs"]
                        :compiler     {:output-to     "resources/public/js/main.js"
                                       :optimizations :whitespace
                                       :pretty-print  true}}]})