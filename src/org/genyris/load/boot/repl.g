@prefix u "http://www.genyris.org/lang/utilities#"
@prefix ver "http://www.genyris.org/lang/version#"
@prefix sys "http://www.genyris.org/lang/system#"
@prefix java 'http://www.genyris.org/lang/java#'

print sys:path

import versioninfo
   
u:format "*** Welcome %a, %a version %a is listening...%n"
    (System(.getProperties)).|user.name|
    versioninfo.title
    versioninfo.version
    
def sys:print-classnames(obj)
    define klasses (use obj (the .classes))
    while klasses
        u:format '%a ' ((left klasses).classname)
        klasses = (cdr klasses)
 
def sys:printBackTrace(bt)
   bt = (cdr bt)
   bt = (cdr bt)
   while bt
       print (left bt)
       bt = (cdr bt)

def sys:read-eval-print-loop()
   def sys:prompt()
      u:format '> '
   define looping true          
   while looping
       sys:prompt
       define bt nil
       catch errors
           define expression (read)
           define result (eval expression)
       cond
           errors
               bt = (sys:backtrace)
               u:format "*** Error - %s\n" errors
               sys:printBackTrace bt
               bt = nil             
           else
                cond
                    (equal? result EOF)
                        looping = nil
                u:format '%s # ' result
                sys:print-classnames result
                u:format '\n'

sys:read-eval-print-loop