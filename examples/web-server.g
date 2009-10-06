##
##  Simple script to start a static web server.
## Arguments:
## port - e.g. 80
## root path directory name - e.g. "F:\\foo\\bar\\"
##
##
@prefix web "http://www.genyris.org/lang/web#"
@prefix sys "http://www.genyris.org/lang/system#"
@prefix u "http://www.genyris.org/lang/utilities#"

cond
   (not (equal? (length sys:argv) 3))
       error "Usage: web-server.g <port> <root directory>"

var port (parse (cadr sys:argv))
var rootdir (cadr (cdr sys:argv))
u:format "Serving web pages on port %a from %a%n" port rootdir
web.serve-static port rootdir
while true
   sleep (* 1000 60)
