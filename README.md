# app-sauce.system

Utility functions for Clojure processes on the JVM.


## Getting Started

For Leiningen or Boot:

```clojure
[app-sauce/repl "0.1.0"]
```

For deps.edn:

```clojure
app-sauce/repl {:mvn/version "0.1.0"}
```

Use the library to handle uncaught exceptions or wait for shutdown signals. For
example, if you are using [component](https://github.com/stuartsierra/component),
you can use this library like so:

```clojure
(ns app.main
  (:require
    [clojure.tools.logging :as log]
    [com.stuartsierra.component :as component]
    [app-sauce.system :as sys]))

; ... code to define your component system ...

(defn -main
  [& args]
  (sys/log-uncaught-exceptions! log/error)
  (let [system (component/start ...)]
    (sys/await-shutdown!)
    (component/stop system))
  (shutdown-agents) ; or other work
  )
```


## License

Copyright © 2015-2019 App Sauce, LLC

Distributed under the Eclipse Public License.
