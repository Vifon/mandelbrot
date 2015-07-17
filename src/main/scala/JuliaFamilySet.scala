trait JuliaFamilySet {
  /** The initial call to the checking function */
  def check(point: Complex, maxIterations: Int): Option[Int]

  /** The modifier used on the z_n value in the equation */
  def modifier(z_n: Complex): Complex = z_n

  final def check(z_n: Complex, p: Complex, iteration: Int, maxIterations: Int): Option[Int] = {
    if (iteration == maxIterations) {
      None
    } else {
      val tmp = modifier(z_n)
      val z_n1 = tmp * tmp + p

      if (z_n1.abs >= 2)
        Some(iteration)
      else
        check(z_n1, p, iteration + 1, maxIterations)
    }
  }
}

trait ComplexAbsolute extends JuliaFamilySet {
  override def modifier(z_n: Complex): Complex = Complex(Math.abs(z_n.re), Math.abs(z_n.im))
}

class Mandelbrot extends JuliaFamilySet {
  override def check(point: Complex, maxIterations: Int): Option[Int] =
    check(Complex(0, 0), point, 0, maxIterations)
}

class BurningShip extends Mandelbrot with ComplexAbsolute

class Julia(z_0: Complex) extends JuliaFamilySet {
  override def check(point: Complex, maxIterations: Int): Option[Int] =
    check(point, z_0, 0, maxIterations)
}
