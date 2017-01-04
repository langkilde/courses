#Course summary

## Week 1

**Relation between for-expressions and database queries.** For-expressions are closely related to map, flatMap and filter. The Scala compiler actually compiles for-expressions into high-order functions. Example
```Scala
for (x <- e1) yield e2
e1.map(x => e2)
```

**Generators.** Useful to build random generators for testing. Can generate complicated data structures.

**Monads.** A monad is a very general type of data structure. It has two operations: ```flatMap``` and ```unit``` that satisfy some required laws. These laws are associativity, left unit and right unit. Lists, Set, Option, Generator are monads.
```Scala
m flatMap f flatMap g == m flatMap (( x => f(x) flatMap g))
unit(x) flatMap f == f(x)
m flatMap unit == m
```

*Why are monads useful?*
