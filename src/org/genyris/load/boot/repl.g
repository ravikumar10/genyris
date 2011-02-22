@prefix u "http://www.genyris.org/lang/utilities#"
@prefix ver "http://www.genyris.org/lang/version#"
@prefix sys "http://www.genyris.org/lang/system#"
@prefix java 'http://www.genyris.org/lang/java#'

import versioninfo
print   versioninfo 
u:format "*** Welcome %a, %a version %a.%a is listening...%n"
    (System(.getProperties)).|user.name|
    versioninfo.title
    versioninfo.specification
    versioninfo.version
    
def sys:print-classnames(obj)
    define klasses (use obj (the .classes))
    while klasses
        u:format '%a ' ((left klasses).classname)
        setq klasses (cdr klasses)
 
def sys:printBackTrace(bt)
   setq bt (cdr (cdr bt))
   while bt
       print (left bt)
       setq bt (cdr bt)

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
               setq bt (sys:backtrace)
               u:format "*** Error - %s\n" errors
               sys:printBackTrace bt
               setq bt nil             
           else
                cond
                    (equal? result EOF)
                        setq looping nil
                u:format '%s # ' result
                sys:print-classnames result
                u:format '\n'

sys:read-eval-print-loop