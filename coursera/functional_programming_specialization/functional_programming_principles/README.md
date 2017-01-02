# Course summary

## Week 1
**Functions are first-class.** Functions are first-class citizens, just like any other variable. 
Functions can therefor be passed around like arguments, returned from functions etc.
It's good functional programming style to break down code into many small functions.

**Call-by-name vs call-by-value** is important to remeber for deciding on how to call a method.
Def x = loop works but val x = loop will not work. Get Call-by-name by def function(x : => Type).

**Recursion.** In Scala you always need to define the return type in order for type inference to work.
Scala relies on Blocks to define scope. Blocks equal the last expression in their definition.

**Tail recursion.** If a function calls itself as the last action, you can reuse the function's stack frame. Use @tailrec notation to check this when you expect it to be the case.

## Week 2
**Higher order functions.** Allows you to define functions through functions. You can define functions through anonymous functions. The type of parameters can be omitted thanks to type inference.

**Currying.** A way to express higher order functions using less code. Example
```
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
