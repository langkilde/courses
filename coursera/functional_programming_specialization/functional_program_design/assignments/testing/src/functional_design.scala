/**
  * Created by langkilde on 1/4/17.
  */
object functional_design {

  object week1 {

    def streamRange(lo : Int, hi : Int) : Stream[Int] =
      if (lo >= hi) Stream.empty
      else Stream.cons(lo, streamRange(lo + 1, hi))

    def listRange(lo : Int, hi : Int) : List[Int] =
      if (lo >= hi) Nil
      else lo :: listRange(lo + 1, hi)

    def expr = {
      val x = { print("x"); 1}
      lazy val y = { print("y"); 2}
      def z = { print("z"); 3}
      z + y + x + z + y + x
    }


    def from(n : Int) : Stream[Int] = n #:: from(n + 1)



  }

  def main(args: Array[String]) {
    println(week1)
  }

}