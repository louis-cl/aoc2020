(ns aoc2020.day8.main
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (->> (io/resource "input-08.txt")
                (io/reader)
                (line-seq)
                (filter (complement empty?))))

(def input-sample (str/split-lines "nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6"))

(defn parse-instr [line]
  (let [[_ instr arg] (re-matches #"(\w{3})\s(.+)" line)]
    [(keyword instr) (Long/parseLong arg)]))

(defn run [program {:keys [ptr acc] :as data}]
  (let [[instr arg] (nth program ptr)]
    (case instr
      :nop (update data :ptr inc)
      :jmp (update data :ptr (partial + arg))
      :acc {:ptr (inc ptr) :acc (+ acc arg)})))

(defn inf-loop? [program]
  (loop [data {:ptr 0 :acc 0}
         executed #{}]
    (cond
      (contains? executed (:ptr data)) [true data]
      (= (count program) (:ptr data))  [false data]
      :else (recur (run program data) (conj executed (:ptr data))))))

;; part 1
(->> input (map parse-instr) inf-loop? second :acc)
;; => 1528

;; part 2
(let [p (mapv parse-instr input)]
  ((loop [[[i [instr arg]] & rest] (map-indexed vector p)]
     (case instr
       :nop (let [[inf data] (inf-loop? (assoc p i [:jmp arg]))]
              (if-not inf data (recur rest)))
       :jmp (let [[inf data] (inf-loop? (assoc p i [:nop arg]))]
              (if-not inf data (recur rest)))
       :acc (recur rest)))
   :acc))
;; => 640
