#Course summary

## Week 1

**Relation between for-expressions and database queries.** For-expressions are closely related to map, flatMap and filter. The Scala compiler actually compiles for-expressions into high-order functions. Example
```Scala
for (x <- e1) yield e2
e1.map(x => e2)
```

**Generators.** Useful to build random generators for testing. Can generate complicated data structures.

**Monads.** A monad is a very general type of data structure. It has two operations: ```flatMap``` and ```unit``` that satisfy some required laws. These laws are associativity, left unit and right unit. 
```Scala
m flatMap f flatMap g == m flatMap (( x => f(x) flatMap g))
unit(x) flatMap f == f(x)
m flatMap unit == m
```
Lists, Set, Option, Generator are monads.

## Week 2

You can do induction on trees.

**Streams.** Like lists but tail is only evaluated on demand.
Take for example the task of finding the second prime in a sequence. This could be done like this
```Scala
((1000 to 10000) filter isPrime)(1)
```
but is seriously inefficient. This is a case where streams are useful.  Transformation to stream is simple.
```Scala
((1000 to 10000).toStream filter isPrime)(1)
```
The above solution introduces another problem. Multiple calls results in recomputation. The solution to this is lazy evaluation.

**Lazy evaluation.** ```lazy val x = expr```. Defers computation until value or variable is needed.

**Infinite data.** Lazy evaluation allows working on infinite data without worrying about termination criteria (as much). 

**Designing functional programs.** An example is explored which is based on a set of states, moves that can change states, generator of moves, paths of moves, state exploration and finally solution selection of generated paths.
