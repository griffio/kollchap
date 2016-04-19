(defproject metosin/compojure-api-examples "0.21.0"
  :description "Shrine of Kollchap api - taken from the book What is Dungeons & Dragons?"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/clojurescript "1.8.40"]
                 [org.clojure/core.async "0.2.374"]
                 [bidi "2.0.6"]
                 [clj-time "0.9.0"]
                 [cc.qbits/jet "0.6.6"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [com.cognitect/transit-cljs "0.8.225"]
                 [hiccup "1.0.5"]
                 [metosin/compojure-api "1.0.2"]
                 [midje "1.8.3"]
                 [secretary "1.2.3"]
                 [sablono "0.7.0"]]
  :main kollchap.handler
  :plugins [[lein-figwheel "0.5.0-2"]]
  :clean-targets [:target-path "out"]
  :cljsbuild {
              :builds [{:id           "kollchap-dev"
                        :source-paths ["src-cljs"]
                        :figwheel     {:on-jsload "hello-kollchap.client/render!"}
                        :compiler     {:main                 hello-kollchap.client
                                       :output-to            "resources/public/js/main.js"
                                       :optimizations        :none
                                       :pretty-print         true
                                       :recompile-dependents false}}]})
