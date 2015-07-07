# Frequently Asked Questions #

## 1. What's with the name? ##
'Genyris' is a made-up word chosen to be unique. When the project started a Google search for "Genyris" returned zero results. Now the search returns over 16,000 pages. It's a melange of 'generous' and 'generic'.  Pronounce it however you want.

## 2. Why doesn't the interpreter care if a Procedure is a method or not? ##
Currently the interpreter does not record that a function is expecting dynamic symbols like `.self` to be bound.
Example:

```
> File.open
:
<EagerProc: <.open>> # EagerProcedure
```
and
```
> use File!abs-path
:  .source
:
(lambda nil (.static-abs-path .filename)) # Pair
```

Somehow it doesn't seem to need it. It would be better to record this at least for debugging. It would be easy enough to tag Procedure as dynamic, but to do a proper job you'd need to scan the source for `DynamicSymbol`s , but that's impossible since code can be generated on the fly.

So you never know if a function uses dynamic bindings until it's called.