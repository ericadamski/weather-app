(ns weather.components.day
  (:require [reagent.core :as reagent]))

(defn day [day]
  [:div.forecast-day
   [:span {:className (str "icon w"(:icon day))}]
   [:div
    [:h4.desc (str (:condition day))]
    [:h2.temp (str (:temp day))]]])
