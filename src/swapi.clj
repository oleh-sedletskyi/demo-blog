(ns swapi
  (:require [cheshire.core :as json]
            [org.httpkit.client :as hk-client]
            [camel-snake-kebab.core :as csk]))

(->> @(hk-client/request
       {:url "https://swapi.dev/api/films/"
        :method :get
        :headers
        {"Content-Type" "application/json"}})
     :body
     (#(json/decode % keyword))
     :results
     (map #(select-keys % [:director :opening_crawl :release_date :title]))

     (def films))

(->> films
     (map (fn [{:keys [opening_crawl release_date title]}]
            {:date release_date
             :title title
             :text opening_crawl
             :alias (csk/->kebab-case title)}))

     (def films-upd))

(->> {:director "George Lucas",
      :opening_crawl
      "It is a period of civil war.\r\nRebel spaceships, striking\r\nfrom a hidden base, have won\r\ntheir first victory against\r\nthe evil Galactic Empire.\r\n\r\nDuring the battle, Rebel\r\nspies managed to steal secret\r\nplans to the Empire's\r\nultimate weapon, the DEATH\r\nSTAR, an armored space\r\nstation with enough power\r\nto destroy an entire planet.\r\n\r\nPursued by the Empire's\r\nsinister agents, Princess\r\nLeia races home aboard her\r\nstarship, custodian of the\r\nstolen plans that can save her\r\npeople and restore\r\nfreedom to the galaxy....",
      :release_date "1977-05-25",
      :title "A New Hope"}
     keys
     (map name))

#_(->> (client/get "https://swapi.dev/api/starships/?search=wing" {:as :auto})
       :body
       :results
       (map :pilots)
       flatten
     ;; (filter seq?)
       )
