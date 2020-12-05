(ns aoc2020.day5.main
  (:require [clojure.java.io :as io]))

(defn count-bin
  ([bins] (count-bin bins 0))
  ([[bin & bins] partial]
   (if (nil? bin) partial
       (count-bin bins 
                  (->> partial
                       (* 2)
                       (+ (if bin 1 0)))))))

(count-bin [true false false])

(defn seat [code]
  (let [[_ up-down left-right] (re-matches #"(.{7})(.{3})" code) ]
    {:row (count-bin (map (partial = \B) up-down))
     :col (count-bin (map (partial = \R) left-right))}))

(= (seat "BFFFBBFRRR") {:row 70 :col 7})
(= (seat "FFFBBBFRRR") {:row 14 :col 7})
(= (seat "BBFFBBFRLL") {:row 102 :col 4})

(def input (->> (io/resource "input-05.txt")
                (io/reader)
                (line-seq)))

(defn seat-id [{:keys [row col]}]
  (+ (* row 8) col))

(->> input
     (map seat)
     (map seat-id)
     (reduce max))
;; => 818

(->> input
     (map seat)
     (map seat-id)
     sort
     (partition 2 1)
     (filter #(not= -1 (apply - %)))
     first
     first
     (+ 1))
;; => 559