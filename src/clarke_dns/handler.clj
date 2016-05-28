(ns clarke-dns.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [clarke-dns.peer-validation :as pv]
            [clarke-dns.db :as db]))

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Clarke Coin DNS"
                    :description "Registry for discovering Clarke Coin peer nodes."}
             :tags [{:name "api", :description "Submit and Retrieve Peer Addresses"}]}}}

    (context "/api" []
      :tags ["api"]

      (GET "/peers" []
           :return [pv/Peer]
           :summary "List all currently online nodes."
           (ok (into [] (db/peers))))

      (POST "/recommended_peers" request
            :return [pv/Peer]
            :summary "Fetch a list of recommended nodes to connect to. Attempts to exclude the requesting node based on the provided host and port."
            :body [submission pv/PeerSubmission]
            (let [p {:port (:port submission)
                     :host (or (:host submission) (:remote-addr request))}]
              (ok (into [] (disj (db/peers) p)))))

      (POST "/peers" request
        :return pv/Peer
        :body [submission pv/PeerSubmission]
        :summary "Submit a node address. Node will be pinged to confirm availability."
        (let [p {:port (:port submission)
                 :host (or (:host submission) (:remote-addr request))}]
          (if (pv/available-peer? p)
            (do
              (db/add-peer! p)
              (ok p))
            (unprocessable-entity {:error "No Clarke Coin node available at supplied address."})))))))
