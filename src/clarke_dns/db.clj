(ns clarke-dns.db)

(def available-peers (atom #{}))

(defn add-peer! [peer]
  (swap! available-peers conj peer))

(defn peers [] @available-peers)

(defn remove-peer! [peer]
  (swap! available-peers disj peer))
