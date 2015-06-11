(ns kollchap.acceptance
  (:require [kollchap.test-common :refer :all]
            [clj-http.client :as http]
            [environ.core :refer [env]]
            [midje.sweet :refer :all]))

(fact-group
 :acceptance

 (fact "Greet resource returns 200 HTTP response"
       (let [response (http/get (url+ "/")  {:throw-exceptions false})]
         response => (contains {:status 200})))

 (fact "Healthcheck resource returns 200 HTTP response"
       (let [response (http/get (url+ "/healthcheck") {:throw-exceptions false})
             body (json-body response)]
         response => (contains {:status 200})
         body => (contains {:name "kollchap"
                            :success true
                            :version truthy}))))
