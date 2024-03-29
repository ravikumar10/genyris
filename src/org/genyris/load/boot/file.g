## Copyright 2008 Peter William Birch <birchb@genyis.org>
##
## This software may be used and distributed according to the terms
## of the Genyris License, in the file "LICENSE", incorporated herein by reference.
##

#
#  Add simple methods to the builtin File class
#
File
    def .new((filename = String))
        tag File (dict (.filename = filename))
    
    def .open((mode = SimpleSymbol))
       .static-open .filename mode 
       
    def .list()
       .static-list-dir .filename 
       
    def .abs-path()
       .static-abs-path .filename 
       
    def .exists((filename = String))
        catch error
            define fd ((File.static-open) filename ^read)
            fd(.close)
        if (equal? error nil)
            true
            nil