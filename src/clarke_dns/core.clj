(ns clarke-dns.core
  (:gen-class)
  (:require [clarke-dns.handler :refer [app]]
            [org.httpkit.server :as httpkit]
            [clarke-dns.peer-validation]))

(defonce server-shutdown-fn (atom nil))
(def update-delay 5000) ;; ms
(defn -main [& args]
  (reset! server (httpkit/run-server app))
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. server-shutdown-fn))
  (while true
    (future (pv/update-available-peers!))
    (Thread/sleep update-delay)))
