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

**Streams.** Like lists but tail is only evaluated on demand. This means that they can be innfinitely long.
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

### Assignment Bloxorz
Design game with intial state S, and target end state T. Find shortest path from S to T.
*Depth-first vs breadth-first search.* Most important lesson is to build all the components at a low level and then let the solution flow from the components.

## Example of using Stream
Lets say you want the N first numbers of the Fibonacci sequence. Then you could define a function

```Scala
def fib(a : Int, b : Int) : Stream[Int] = a #:: fib(b, a + b)
```
if we then ask for
```Scala
scala> val fibs = fib(1,1).take(7)
```
we get
```Scala
fibs : scala.collection.immutable.Stream[Int] = Stream(1, ?)
```
We can make sure we get the actual first 7 values
```Scala
scala> fibs.toList
res: List[Int] = List(1, 1, 2, 3, 5, 8, 11)
```
