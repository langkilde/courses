package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal {
      b() * b() - 4 * a() * c()
    }
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    Signal {
      val deltaRoot: Signal[Double] = Signal(math.sqrt(b()))
      val bNeg: Signal[Double]      = Signal(b() * -1)
      val a2: Signal[Double]        = Signal(a())
      if (deltaRoot() < 0) Set()
      else {
        Set(
          (bNeg() + deltaRoot()) / a2(),
          (bNeg() + deltaRoot()) / a2()
        )
      }
    }
  }
}