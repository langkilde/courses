#Course summary

## Week 1 - Higher order functions

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

## Week 2 - Lazy evaluation

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

### Example of using Stream
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

## Week 3 - Function and State

An object has a state if its behaviour is influenced by its history. Mutable objects declared as ```var a = 5```.

Scala supports the ```while``` loop, but while is not necessary and instead could be described as functions.

Classical ```for``` loop can *not* be modeled by higher-order function. ```forEach``` is a building block for the same behaviour.

States are useful as they allow for powerful designs, but we lose referential transparency.

### Assignment Quickcheck

The key takeaway from the assignement is the power of generator-driven property checks. 

[Here's a link to a useful resource](http://www.scalatest.org/user_guide/generator_driven_property_checks)

The idea is to build a simple generator and then define a set of properties that need to be satisfied by the data generated.

## Week 4 - Timely Effects

A classic pattern for event handling is the Observer pattern. One example of this is publish/subscribe. Good things about the observer pattern are that 
- it decouples views from states
- you can have as many views of a state as you want
- simple to set up

Bad things are that
- forces imperative style
- requires coordination of lots of moving parts
- view updates happen immediately
- concurrency would make things more complicated
- higher than usual bug rate common

In order to improve the situation Functional Reactive Programming is introduced. A key concept to FRP is Signals.

## Signals
Values of type Signal are immutable. Signals have two fundamental operations

1. Obtain the value of the signal at the current time.

2. Define a signal in terms of other signals.

There's also Variable Signals ```Var()``` that implement the ```update()``` operation. 

Each signal maintains three things
- current value
- expression that defines the signal value
- set of observer signals

Implementing Signals requires a data structure accessed like a stack since signals depend on each other in a specific order.

Signals change value when
- update is called on a Var
- the value of a dependent signal changes

Important issue is how to deal with parallell signal calls. One solution is to use Thread-Local State through the API of DynamicVariable. The state is then global to the thread, but each thread then accesses it's own copy of the state.

A cleaner solution involves implicit paramters. instead of maintaining a thread-local variable, we pass its current value into a signal expression as an implicit parameter.

### Latency

Programs have to deal with latency. Never block your code, always work asynchronously. A common way to deal with latency is to have callback functions. With callbacks you basically send a request to a method along with a return adress for the resulting value.

An important building block is ```Future[T]```.
```Scala
trait Future[T] {
  def onComplete(callback : Try[T] => Unit)
        (implicit exector : ExecutionContext) : Unit
}
```
A ```Future[T]``` has an apply method
```Scala
object Future {
  def apply(body : => T)
    (implicit context : ExecutionContext) : Future[T]
}
```
Futures have a set of higher order functions defined on them. This can be used to simplify code.
