object Mandelbrot {
  def check(point: Complex, maxIterations: Int): Option[Int] =
    check(Complex(0, 0), point, 0, maxIterations)

  private def check(z_n: Complex, p: Complex, iteration: Int, maxIterations: Int): Option[Int] = {
    if (iteration == maxIterations) {
      None
    } else {
      val z_n1: Complex = (z_n * z_n) + p

      if (z_n1.abs >= 2)
        Some(iteration)
      else
        check(z_n1, p, iteration + 1, maxIterations)
    }
  }
}