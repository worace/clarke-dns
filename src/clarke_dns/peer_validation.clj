(ns clarke-dns.peer-validation
  (:require [cheshire.core :as json]
            [clj-http.client :as http]
            [schema.core :as s]
            [clarke-dns.db :as db]))

(s/defschema Peer
  {:port s/Int
   :host s/Str})

(s/defschema PeerSubmission
  {:port s/Int
   (s/optional-key :host) s/Str})

(defn current-time-millis [] (System/currentTimeMillis))

(defn url [peer path]
  (str "http://" (:host peer) ":" (:port peer) "/" path))

(defn available-peer? [peer]
  (try
    (let [t (current-time-millis)
          r (http/post (url peer "ping") {:form-params {:ping t}
                                          :content-type :json})]
      (-> r
          :body
          (json/parse-string true)
          :pong
          (= t)))
    (catch Exception e false)))

(defn update-available-peers! []
  (doseq [p (db/peers)]
    (when-not (available-peer? p) (db/remove-peer! p))))
