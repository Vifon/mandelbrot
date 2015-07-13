
import java.awt.{Canvas, Color, Graphics}
import javax.swing.JFrame
import scala.util.Random

case class Complex(re: Double, im: Double) {
  def +(rhs: Complex) = Complex(re+rhs.re, im+rhs.im)
  def -(rhs: Complex) = this + (-rhs)
  def *(rhs: Complex) = Complex(re*rhs.re - im*rhs.im, re*rhs.im + im*rhs.re)
  def abs: Double = Math.sqrt(re*re + im*im)

  def unary_- = Complex(-re, -im)
}

object Mandelbrot {
  def check(point: Complex): Boolean = check(Complex(0,0), point, 50)

  private def check(z_n: Complex, p: Complex, iterationsLeft: Int): Boolean = {
    if (iterationsLeft == 0) {
      true
    } else {
      val z_n1: Complex = (z_n*z_n) + p

      if (z_n1.abs >= 2)
        false
      else
        check(z_n1, p, iterationsLeft - 1)
    }
  }
}

class MyCanvas extends JFrame {
  val canvas_width = 500
  val canvas_height = 500

  setTitle("Bitmap")
  setSize(canvas_width, canvas_height)
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  class MyCanvas extends Canvas {
    override def paint(g: Graphics) = {
      def drawPixel(x: Int, y: Int, size: Int) = g.fillRect(x, y, size, size)

      for {
        y <- 0 until canvas_height
        x <- 0 until canvas_width
      } {
        val w = canvas_width.toDouble
        val h = canvas_height.toDouble
        val re: Double = ((x/w)*2 - 1.5)
        val im: Double = ((y/h)*2 - 1)
        if (Mandelbrot.check(Complex(re, im)))
          g.setColor(Color.RED)
        else
          g.setColor(Color.GREEN)
        drawPixel(x, y, 1)
      }
    }
  }

  val canvas = new MyCanvas
  add(canvas)

  setVisible(true)
}

object Main {
  def main(args: Array[String]) = {
    val win = new MyCanvas
  }
}
