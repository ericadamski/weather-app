(ns weather.components.forecast
    (:require [reagent.core :as reagent]
              [cljs-http.client :as http]
              [weather.components.day :as day]
              [cljs.core.async :refer [<!]])
    (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce weather-data (reagent/atom (hash-map)))

(defn fetch-weather []
  (http/get "//api.openweathermap.org/data/2.5/forecast"
            {:with-credentials? false
             :query-params {
                            "id" 6094817
                            "APPID" "5f76af14cd2b833834cab55b98ed7e41"
                            "units" "metric"}}))

(defonce time-updater (js/setInterval
                       #(go (let [response (<! (fetch-weather))]
                              (reset! weather-data response)))
                        10000))

(defn format-day [day-vec]
  (let [
        day (second day-vec)
        temp (get-in day [:main :temp])
        weather (first (:weather day))]
    (vector (first day-vec) (day/day {
                                       :temp (str (Math/round temp))
                                       :condition (:description weather)
                                       :icon (:icon weather)}))))

(defn days [list]
  [:div.forecast
   (let [days (mapv format-day (rest list))]
    (for [day days]
     ^{:key (first day)} (second day)))])

(defn weather []
  (let [
        raw (get-in @weather-data [:body :list])
        data (filterv #(= (mod (first %) 7) 0) (map-indexed vector raw))]
    [:div
     [:div.weather (second (format-day (first data)))]
     (days data)]))
