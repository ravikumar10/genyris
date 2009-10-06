## Copyright 2008 Peter William Birch <birchb@genyis.org>
##
## This software may be used and distributed according to the terms
## of the Genyris License, in the file "LICENSE", incorporated herein by reference.
##
load "testscripts/gunit.g"
@prefix t "http://www.genyris.org/lib/gunit#"
@prefix u "http://www.genyris.org/lang/utilities#"

## Test built-in interning
@prefix p "http://server/#"

var overall-result
  t:test "Symbol Tests"
      t:test "Escaped Intern tests"
        equal? (intern "foo") ^|foo|
        not (equal? (gensym "foo") ^|foo|)
        equal? (intern "http://server/#w") ^|http://server/#w|
      t:test "Intern tests"
        equal? ((intern "foo").classes) (list SimpleSymbol)
        equal? ((intern "http://server/#w").classes) (list URISymbol)
        t:given ^(intern "A")       expect ^A
        t:given ^(intern "http://server/#w")       expect ^p:w
        t:given (left (member? SimpleSymbol ((intern 23).classes)))       expect SimpleSymbol
      t:test "gensym tests"
        t:given (eq? (gensym 23) (gensym 23)) expect nil
        t:given (equal? (gensym 23) (gensym 23)) expect nil
    
or overall-result (raise "Intern Failed")