@prefix u   "http://www.genyris.org/lang/utilities#"
@prefix web "http://www.genyris.org/lang/web#"
@prefix sys "http://www.genyris.org/lang/system#"

#
# Start with:
# task:httpd 80 'examples/triple-browser.g' 'http://<server>:<port>/<path to triples file>.csv'
#
# Windows example:
#
#
def myparse (string)
   # parse a string returning the expression
   (ParenParser(.new string))
      .prefix "rdf" "http://w3c/rdf#"
      .prefix "rdfs" "http://w3c/rdfs#"
      .read

def tryToParseSymbol (str)
    define result
       catch errors (myparse (str))
    if errors
        do
            print errors
            intern str
        result

def tryToParse (str)
    define result
       catch errors (myparse (str))
    if (equal? (asString result) str)
        result
        str

define store nil

def loadDataFromCSV(url)
    var in (web:get url)
    setq store (graph)
    define line ""
    while (not (equal? EOF (setq line (in(.getline)))))
        define aslist (line(.split ","))
        cond
            (equal? (length aslist) 1)
                u:format "%s - %s %a%n" (length aslist) line aslist
            else
                if (equal? (length aslist) 2)
                    define object ""
                    define object (nth 2 aslist)
                define subject (tryToParseSymbol (nth 0 aslist))
                define predicate (tryToParseSymbol (nth 1 aslist))
                catch derror
                   define tr (triple subject predicate object)
                cond
                   derror
                      u:format "triple error: %s %s %s %s%n" derror subject predicate object
                store
                   .add tr
    in (.close)

def convertToRegex (str)
    cond
       (str(.match '".*"'))
            # exact
            setq str
                (cadr (str(.split '"')))
                    .toLowerCase
       else
            setq str (str(.toLowerCase))
            setq str (str (.+ ".*"))
            setq str (".*" (.+ str))
    print str
    str

def searchStore(str)
    setq str (convertToRegex str)
    def matchFunc(s o p)
       #u:format "%s %s %s %s%n" s o p str
        or
          ((asString s)(.toLowerCase)) (.match str)
          ((asString p)(.toLowerCase)) (.match str)
          ((asString o)(.toLowerCase)) (.match str)
    store(.select nil nil nil matchFunc)

def searchURL (str)
   "/?op=Search&query="(.+ str)

def renderTriple (t)
   define s (asString(t(.subject)))
   define p (asString(t(.predicate)))
   cond
        (p (.match ".*_password.*"))
            define o "XXXXXXXXX"
        else
            define o (asString(t(.object)))
   template
      tr()
         td()
            a((href = $(searchURL s))) $s
         td()
            a((href = $(searchURL p))) $p
         td()
            a((href = $(searchURL o))) $o

def renderTripleList (ts)
    define result nil
    for trip in ts
        setq result (cons (renderTriple trip) result)
    template
            table()
                tr()
                    td()
                        strong() "Subject"
                    td()
                        strong() "Property"
                    td()
                        strong() "Object"
                    $result
define DEFAULT "\"Environment\""


df httpd-serve (request)
   var params (request(.getParameters))
   define query DEFAULT
   define operation "Search"
   define searchResults nil
   define renderedResults ""
   setq operation (params (.lookup "op"))
   setq query (params (.lookup "query"))
   cond
       (or (null? query) (equal? query ""))
           setq query DEFAULT
   cond
       (equal? operation "Reload")
            loadDataFromCSV CSVURL
            setq renderedResults
               template
                   "Reloaded from "
                      a((href = $CSVURL))
                         $CSVURL
       else
            setq searchResults ((searchStore query)(.asTriples))
            setq renderedResults (renderTripleList searchResults)
   var result
    list 200 "text/html"
      template
          html()
             head()
               title() "CMDB Search"
             body()
                h2() "CMDB Search"
                form()
                   input((name ="query") (size ="100") (value = $query)) ""
                   verbatim() '&nbsp;&nbsp;&nbsp;'
                   input((type ="submit") (name ="op") (value ="Search"))
                   input((type ="submit") (name ="op") (value ="Reload"))
                div()
                    $renderedResults
   result
catch errors
  define CSVURL (nth 1 sys:argv)
if errors
   u:format 'Usage: %s missing URL to CSV file' sys:argv
   nil
loadDataFromCSV CSVURL