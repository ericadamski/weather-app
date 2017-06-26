(ns weather.core
    (:require [reagent.core :as reagent]
              [cljs-http.client :as http]
              [weather.components.date :as date]
              [weather.components.forecast :as forecast]
              [cljs.core.async :refer [<!]])
    (:require-macros [cljs.core.async.macros :refer [go]]))

;; -------------------------
;; Views

(defn home-page []
  [:div
    [date/date]
    [forecast/weather]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
