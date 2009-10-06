@prefix u "http://www.genyris.org/lang/utilities#"
def u:format(&rest args)
   stdout
      apply .format args

defmacro u:debug(thing)
  template
    u:format "%a => %a%n" ^,thing ,thing

def u:getLocalTime()
   System
      .exec "cmd.exe" "/c" "echo %date% %time%"

def u:printSymbolTable()
  (symlist)
    .each (x)
      (stdout (.format "%a%n" x))