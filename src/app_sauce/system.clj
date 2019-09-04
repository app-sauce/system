;; Copyright © App Sauce, LLC
;;
;; All rights reserved. This program and the accompanying materials are made
;; available under the terms of the Eclipse Public License v2.0 which
;; accompanies this distribution, and is available at
;; http://www.eclipse.org/legal/epl-v20.html

(ns app-sauce.system)


(defn on-uncaught-exception!
  [handler]
  (Thread/setDefaultUncaughtExceptionHandler
    (reify Thread$UncaughtExceptionHandler
      (uncaughtException [_ thread ex]
        (handler thread ex)))))

(defmacro log-uncaught-exceptions!
  [logger]
  `(on-uncaught-exception!
     (fn [thread# ex#]
       (~logger (str "Uncaught exception on " (.getName thread#) ":") ex#))))


(defmacro on-shutdown!
  [& body]
  `(doto (Runtime/getRuntime)
     (.addShutdownHook (Thread. #(do ~@body)))))

(defn await-shutdown!
  []
  (let [lock (promise)]
    (on-shutdown!
      (deliver lock :release))
    @lock))
