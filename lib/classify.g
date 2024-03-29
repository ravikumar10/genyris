## Copyright 2008 Peter William Birch <birchb@genyis.org>
##
## This software may be used and distributed according to the terms
## of the Genyris License, in the file "LICENSE", incorporated herein by reference.
##

# Function to automatically tag an object with classes
# starting with the current class.
# Only tags the most specific derived classes.
#
# returns true if a valid class was found, nil otherwise
#
def classify(klass thing)
   cond
     (klass (and (bound? ^.valid?) (.valid? thing))) # is the thing a member of this class?
        define subclass-results nil                    # analyse sub-classes for matches
        map-left (klass.subclasses)
           lambda (subklass)
              setq subclass-results
                or subclass-results
                   classify subklass thing
        cond
           (null? subclass-results)         # no subclasses were valid so tag with this class
               tag klass thing
        true

