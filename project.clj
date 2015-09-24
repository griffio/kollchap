(defproject metosin/compojure-api-examples "0.21.0"
  :description "Shrine of Kollchap api - taken from the book What is Dungeons & Dragons?"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clj-time "0.9.0"]
                 [metosin/compojure-api "0.23.1"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/clojurescript "1.7.58"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [com.cognitect/transit-cljs "0.8.225"]
                 [secretary "1.2.3"]
                 [cc.qbits/jet "0.6.6"]
                 [midje "1.7.0"]
                 [hiccup "1.0.5"]
                 [sablono "0.3.6"]]
  :main kollchap.handler
  :plugins [[lein-cljsbuild "1.1.0"]
            [lein-figwheel "0.4.0"]]
  :clean-targets [:target-path "out"]
  :cljsbuild {
              :builds [{:id           "kollchap-dev"
                        :source-paths ["src-cljs"]
                        :figwheel     {:on-jsload "hello-kollchap.client/render!"}
                        :compiler     {:main                 hello-kollchap.client
                                       :output-to            "resources/public/js/main.js"
                                       :optimizations        :whitespace
                                       :pretty-print         true
                                       :recompile-dependents false}}]})
