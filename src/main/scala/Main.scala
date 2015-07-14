
object Main {
  def main(args: Array[String]) = {
    val julia = new Julia(Complex(-0.73, 0.19))
    val mandelbrot = Mandelbrot
    val win = new MandelbrotFrame(700, 500, mandelbrot)
  }
}
