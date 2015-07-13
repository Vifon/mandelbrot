case class Complex(re: Double, im: Double) {
  def +(rhs: Complex) = Complex(re + rhs.re, im + rhs.im)
  def -(rhs: Complex) = this + (-rhs)
  def *(rhs: Complex) = Complex(re * rhs.re - im * rhs.im, re * rhs.im + im * rhs.re)
  def abs: Double = Math.sqrt(re * re + im * im)

  def unary_- = Complex(-re, -im)
}
