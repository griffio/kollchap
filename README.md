# Compojure-api-examples

## Kollchap example

Example project for using [Compojure-api](https://github.com/metosin/compojure-api).

Aiming to make it Restful hypermedia with resource links

## Usage

### REPL

`lein repl`

```
(use 'midje.repl)
(autotest)
```

### Running

`lein ring server`

### Packaging and running as standalone jar

```
lein do clean, ring uberjar
java -jar target/examples.jar
```

### Packaging as war

`lein ring uberwar`

## License

Copyright © 2014-2015 [Metosin Oy](http://www.metosin.fi)

Distributed under the Eclipse Public License, the same as Clojure.
