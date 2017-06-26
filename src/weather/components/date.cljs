(ns weather.components.date
  (:require [reagent.core :as reagent]))

(defonce timer (reagent/atom (js/Date.)))

(defonce time-updater (js/setInterval
                       #(reset! timer (js/Date.)) 1000))

(defn clock []
  (let [time (-> @timer .toTimeString (clojure.string/split " ") first)]
    [:div.time time]))


(defn date []
  [:div.clock
   [:h1 (-> @timer .toDateString)]
   [clock]])
