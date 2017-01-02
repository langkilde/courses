package funsets

object Main extends App {
  import FunSets._

//  println(contains(x => Math.abs(x) < 5, 3))
//  println(union(x => Math.abs(x) < 5, x => x < 10)(7))

//  filter(x => Math.abs(x) < 5, x => x % 2 == 0)

  val lista = Seq(1,2,3,4,5)

  println(lista)

  lista.filter(i => i % 2 == 0).foreach(i => println(i*2))


  def sum(intToInt: (Int) => Int, lista: Seq[Int]) = {
    lista.map(i => intToInt(i)).sum
  }

  println(sum((x : Int) => x*x+2, lista))

//  println(contains(singletonSet(1), 1))
//  println(union(singletonSet(2), singletonSet(3))(2))
//  println(exists(x => x % 3 == 0, x => x == 2))
}