package recfun

object Main {
  def main(args: Array[String]) {
    println(countChange(4,List(1,2)))
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if (r == 0) return 1
      if ((c % r) == 0) {
        1
      } else {
        pascal(c-1, r-1) + pascal(c, r-1)
      }
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      val filteredChars = chars.filter(c => c == '(' || c == ')')
      if (filteredChars.isEmpty) return true
      balance(filteredChars, List())
    }

    def balance(chars : List[Char], stack : List[Char]) : Boolean = {
      if (chars.isEmpty)     { return stack.isEmpty }
      if (chars.head == '(') {
        balance(chars.tail, stack :+ chars.head)
      } else {
        stack.nonEmpty && (chars.head != stack.last) && balance(chars.tail, stack.dropRight(1))
      }
    }
  
  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int =
    if (money < 0) {
      0
    } else if (coins.isEmpty) {
      if (money == 0) 1 else 0
    } else {
      countChange(money, coins.tail) + countChange(money - coins.head, coins)
    }

}