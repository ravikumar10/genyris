#
# Pipes Examples
#
@prefix u "http://www.genyris.org/lang/utilities#"

def killall(name)
   for task in (ps)
        cond
           (equal? (task.name) name)
               u:format "killing %s\n" task
               task(.kill)

# Write and read within the same task:
define apipe (Pipe!open 'mypipe')
Pipe!list
define out
   apipe(.output)
out(.format 'Hello from the output\n')
out(.flush)


define inpipe apipe
assert (equal? inpipe apipe)
define in (inpipe(.input))
assert (equal? 'Hello from the output' (in(.getline)))
Pipe!delete 'mypipe'
assert (equal? (Pipe!list) nil)


# share a text line using a pipe between this task and a reading task    
define shared (Pipe!open 'shared-pipe')
spawn 'testscripts/pipe-reader.g' 'shared-pipe'
define out
   shared(.output)
out(.format 'Hello from the first task\n')


# single reader, multiple writers - scrambled together at reader since no synchronisation
define shared (Pipe!open 'shared-pipe2')
for i in (range 0 5)
    spawn 'testscripts/pipe-writer.g' 'shared-pipe2'
define in (shared(.input))
for i in (range 0 50)
   in(.getline)
   u:format "."
u:format "\n"
         
# single reader, multiple writers - with synchronisation
define shared (Pipe!open 'shared-pipe3')
for i in (range 0 5)
    spawn 'testscripts/pipe-writer-synch.g' 'shared-pipe3'
define in (shared(.input))
for i in (range 0 100)
   var line (in(.getline))
   assert (equal? 'Hello from task.' line)
killall 'testscripts/pipe-writer-synch.g'
       
print
   Pipe!list

