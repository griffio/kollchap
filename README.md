## Kollchap

### clojure ring compojure

### Project template created from [mr-clojure](https://github.com/mixradio/mr-clojure)

### web.clj

Read custom headers on request 

~~~ clojure

 (GET "/" req
        [] (greet (get-in req [:headers "x-greet"])))

~~~

unit test web.clj with headers

~~~ clojure

(defn request
  "Creates a compojure request map and applies it to our routes.
     Accepts method, resource and optionally an extended map"
       [method resource & [{:keys [params body content-type headers]
                              :or {params {}
                                   headers {"x-greet" "Bobert"}}}]]


~~~




