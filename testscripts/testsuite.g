## Copyright 2008 Peter William Birch <birchb@genyis.org>
##
## This software may be used and distributed according to the terms
## of the Genyris License, in the file "LICENSE", incorporated herein by reference.
##
#
# Run Regression Tests
#
define genyris
   (dict)
      def .run-all-tests()
         load "examples/queens.g"
         queens 4
         self-test-runner
      .self

(genyris .run-all-tests)