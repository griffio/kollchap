(defproject metosin/compojure-api-examples "0.21.0"
  :description "Shrine of Kollchap api - taken from the book What is Dungeons & Dragons?"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.9.0"] ;; needed as `lein ring` is broken.
                 [metosin/compojure-api "0.21.0"]]
  :ring {:handler kollchap.handler/app}
  :uberjar-name "kollchap.jar"
  :uberwar-name "kollchap.war"
  :profiles {:uberjar {:resource-paths ["swagger-ui"]
                       :aot :all}
             :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [midje "1.6.3"]]
                   :plugins [[lein-ring "0.9.4"]]}})
