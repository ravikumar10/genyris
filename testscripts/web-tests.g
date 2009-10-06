@prefix u   "http://www.genyris.org/lang/utilities#"
@prefix t   "http://www.genyris.org/lib/gunit#"
@prefix web "http://www.genyris.org/lang/web#"

include "testscripts/gunit.g"

def readPage(url)
    var wstream (web.get url)
    var count 0
    while (wstream(.hasData))
         count = (+ count 1)
         write (wstream(.read)) ^-
    wstream(.close)
    u:format "%nRead %a bytes%n" count
    count

def run-web()
    var thread-id (web.serve 7777 "testscripts/www-text.g")
    sleep 2000
    kill thread-id

def run-web-get()
    var thread-id (web.serve 7778 "testscripts/www-text.g")
    sleep 2000
    var result (readPage "http://localhost:7778/")
    kill thread-id
    result

def run-web-static-get()
    var thread-id (web.serve-static 7778 ".")
    sleep 2000
    var result (readPage "http://localhost:7778/")
    kill thread-id
    result

var overall-result
  t:test "Web Server"
    t:test "Spawn and Kill"
        t:given (run-web) expect "Terminated GenyrisHTTPD-7777"
        t:given (run-web) expect "Terminated GenyrisHTTPD-7777"

    t:test "Spawn and HTTP Get"
         t:given (run-web-get) expect 15
         t:given (run-web-get) expect 15

    t:test "Spawn static and HTTP Get"
         t:given (run-web-static-get) expect 2017
         t:given (run-web-static-get) expect 2017
or overall-result (raise "Web Suite Failed")