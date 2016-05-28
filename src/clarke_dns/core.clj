(ns clarke-dns.core
  (:gen-class)
  (:require [clarke-dns.handler :refer [app]]
            [org.httpkit.server :as httpkit]
            [clarke-dns.peer-validation :as pv]))

(defonce server-shutdown-fn (atom nil))
(def update-delay 5000) ;; ms
(def port 3000)
(defn -main [& args]
  (println "Starting clarke DNS server.")
  (reset! server-shutdown-fn (httpkit/run-server app {:port port}))
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. #((println "Stopping Clarke DNS Web Server")
                               (@server-shutdown-fn 100))))
  (while true
    (future (pv/update-available-peers!))
    (Thread/sleep update-delay)))
