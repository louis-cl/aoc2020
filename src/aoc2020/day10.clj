(ns aoc2020.day10
  (:require [clojure.string :as str]
            [aoc2020.utils :refer [read-file]]))

(def sample-1 [16
               10
               15
               5
               1
               11
               7
               19
               6
               12
               4])

(defn jumps [input]
   (->> (conj input 0 (+ 3 (apply max input)))
       sort
       (partition 2 1)
       (map reverse)
       (map #(apply - %))))

(defn part1 [input]
  (->> (jumps input)
       frequencies
       vals
       (apply *)))

(defn part2 [input]
  (->> input
       jumps
       (partition-by #{3})
       (filter #(= (first %) 1))
       (map count)
       (map {1 1 2 2 3 4 4 7})
       (apply *)))

(part1 sample-1)
;; => 35

(def input (->> (read-file "input-10.txt")
                str/split-lines
                (map read-string)))

(part1 input)
;; => 2310

(defn part2 [input]
  (->> input
       jumps
       (partition-by #{3})
       (filter #(= (first %) 1))
       (map count)
       (map {1 1 2 2 3 4 4 7})
       (apply *)))

(part2 input)
;; => 64793042714624
