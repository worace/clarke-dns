(defproject clarke-dns "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-time "0.11.0"] ; required due to bug in `lein-ring uberwar`
                 [org.clojure/core.async "0.2.374"]
                 [clj-http "2.2.0"]
                 [cheshire "5.6.1"]
                 [http-kit "2.1.18"]
                 [metosin/compojure-api "1.1.1"]]
  :ring {:handler clarke-dns.handler/app}
  :uberjar-name "server.jar"
  :main clarke-dns.core
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]]
                   :plugins [[lein-ring "0.9.7"]]}})
