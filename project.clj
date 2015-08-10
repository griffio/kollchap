(defproject metosin/compojure-api-examples "0.21.0"
  :description "Shrine of Kollchap api - taken from the book What is Dungeons & Dragons?"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.9.0"] ;; needed as `lein ring` is broken.
                 [metosin/compojure-api "0.21.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [cljs-http "0.1.36"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [lein-light-nrepl "0.1.0"]]
  :repl-options {:nrepl-middleware [lighttable.nrepl.handler/lighttable-ops]}
  :ring {:handler kollchap.handler/app}
  :uberjar-name "kollchap.jar"
  :uberwar-name "kollchap.war"
  :profiles {:uberjar {:resource-paths ["swagger-ui"]
                       :aot :all}
             :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [midje "1.6.3"]
                                  [hiccup "1.0.5"]]
                   :plugins [[lein-ring "0.9.4"]
                             [lein-cljsbuild "1.0.3"]]
                   :cljsbuild {
                      :builds [{
                        :source-paths ["src-cljs"]
                        :compiler {
                        :output-to "resources/public/js/main.js"
                        :optimizations :whitespace
                        :pretty-print true}}]}}})
