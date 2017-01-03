# Course summary

## Week 1
**Functions are first-class.** Functions are first-class citizens, just like any other variable. 
Functions can therefor be passed around like arguments, returned from functions etc.
It's good functional programming style to break down code into many small functions.

**Call-by-name vs call-by-value** is important to remeber for deciding on how to call a method.
```Def x = loop``` works but ```val x = loop``` will not work. Get Call-by-name by ```def function(x : => Type)```

**Recursion.** In Scala you always need to define the return type in order for type inference to work.
Scala relies on Blocks to define scope. Blocks equal the last expression in their definition.

**Tail recursion.** If a function *calls itself as the last action*, you can reuse the function's stack frame. Use ```@tailrec``` notation to check this when you expect it to be the case.

## Week 2
**Higher order functions.** Allows you to define functions through functions. You can define functions through anonymous functions. The type of parameters can be omitted thanks to type inference.

**Currying.** A way to express higher order functions using less code. Example
```Scala
object CurryTest extends App {
  def filter(xs: List[Int], p: Int => Boolean): List[Int] =
    if (xs.isEmpty) xs
    else if (p(xs.head)) xs.head :: filter(xs.tail, p)
    else filter(xs.tail, p)
  def modN(n: Int)(x: Int) = ((x % n) == 0)
  val nums = List(1, 2, 3, 4, 5, 6, 7, 8)
  println(filter(nums, modN(2)))
  println(filter(nums, modN(3)))
}
```
Useful in Scala
* ```require(cond, "msg")``` - enforce precondition
* ```assert(cond)``` - check that the code functions

## Week 3
**Class hierarchies.** Any user-defined class extends another class. If no superclass is given the standard Object in java.lang is assumed.

**Abstract types.** Types whose identity is not precisely known. Useful to know that ```type T <: Seq[U]``` indicates that T must be a subtype of Seq[U].

**Base classes.** The direct and indirect superclasses of a class C are called base classes.

**Dynamic method dispacth.** The code invoked by a method call depends on the runtime type of the object that contains the method.

**Trait.** In Scala (and Java) a class can only have *one* supertype (single inheritance). A trait is declared like an abstract class, just with the trait instead of the abstract class. A class can inhertice from arbitrarily many traits.
```Scala
class Square extends Shape with planar with Movable
```
Traits can have function implementations. Classes can have value parameters, traits cannot.

**Parametric Polymorphism.** Generalize classes with type parameters. All type parameters and type arguments are removed before evaluating the program. This is called *type erasue*. This means that the runtime type of ```List[Int]``` and ```List[Boolean]```.

[type basics from twitter github](https://twitter.github.io/scala_school/type-basics.html)

## Week 4

**Functions are treated as objects in Scala.** Functions are objects. Methods themself are not function values, but are converted automatically to the function value if used in a place where a Function type is expected.

**Subtypes and supertypes.** ```S <: T``` means S is subtype of T, ```S >: T``` is a supertype of T. They can also be mixed.

**Decomposition.** Type Tests and Type Casts exist but are discouraged. Instead Pattern Matching is the recommended method.
Relies on **case classes**. Case classes will automatically have companion objects with apply methods. The nice thing about this is you can drop the new keyword. An example of pattern matching is
```Scala
def eval(e : Expr) : Int = e match {
  case Number(n) => n
  case Sum(e1, e2) => eval(e1) + eval(e2)
}
```
Remember you can use wildcard patterns _

**Lists.** Lists are immutable and recursive. Lists are constructed from the empty list ```Nil``` and the construction operation ```::``` (*cons*). ```x :: xs``` gives a new list with the first element x, followed by the elements of xs.

NOTE: Operators ending in ":" are different in that they are seen as method calls of the right-hand operand.
I.e.  ```1 :: 2``` is equivalent to ```2.::1```

## Week 5

**List methods.** Length, last, init, take, drop, xs(n), xs ++ ys, reverse, xs updated (n, x), indexOf, contains, head, tail.

**Pairs and Tuples.** Can be used for pattern matching. Example
```Scala
def sillyExample(xs : List[Int], ys : List[Int]) = (xs, ys) match {
  case (Nil, ys) => ys
  case (xs, Nil) => xs
  case _ => xs :: ys
}
```
**Implicit parameters.** If you write an implicit parameter the compiler will figure out the right argument to pass based on the required type. If it can't it's an error.

**Higher order functions of lists.** Lots available such as map, filter, span etc.

**Fold and reduce operators.** Lets say you want to make a sum of a list. This can be done recursively.
```Scala
def sum(l : List[Int]) : Int = l match {
  case Nil => 0
  case x :: xs => x + sum(xs)
}
```
We can generalize this pattern by using for example ```reduceLeft``` like this
```Scala
def sum(xs : List[Int]) = (0 :: xs) reduceLeft ((x, y) => x + y)
```
foldLeft is like reduceLeft but taakes an accumulator z. General structure is
```Scala
(List(x1, ..., xn) foldLeft z)(op) = (...(z op x1) op ...) op xn
```
which means we can write our sum function as
```Scala
def sum(xs : List[Int]) = (xs foldLeft 0) (_ + _)
```

## Week 6
```flatten``` is useful to take a collection of collections and turn it into a concatenation of the collections.
Note that ```xs flatMap f = (xs map f).flatten```.

**For-expressions.** Lets say we want to find all pairs of numbers such that the sum of the pair is a prime. We could do this the following way
```Scala
(1 until n) flatMap (i =>
  (1 until i) map (j => (i,j))) filter (pair => 
    isPrime(pair._1 + pair._2))
```
For-expressions can help simplify this method. A for-expression is of the form ```for (s) yield e``` where s is a sequence of generators and filters, and e is an expression whose value is returned by an iteration. The new way of writing our method above would be
```Scala
for {
  i <- 1 until n
  j <- 1 until i
  if isPrime(i + j)
} yield (i,j)
```
