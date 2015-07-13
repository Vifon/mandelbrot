
import java.awt.{Canvas, Color, Graphics}
import java.awt.image.BufferedImage

class MandelbrotCanvas(width: Int, height: Int) extends Canvas {
  val buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

  var pan_x = -0.5
  var pan_y =  0.0
  var zoom  =  1.0
  var iterations = 50
  var bloom = false

  def pan(x: Double, y: Double) = {
    pan_x += x / zoom
    pan_y -= y / zoom
    draw()
    repaint()
  }

  def magnify(n: Double) = {
    zoom *= n
    draw()
    repaint()
  }

  def precision(n: Int) = {
    iterations += n
    draw()
    repaint()
  }

  def toggleBloom() = {
    bloom = !bloom
    draw()
    repaint()
  }

  private def draw() = {
    for {
      y <- 0 until height
      x <- 0 until width
    } {
      val w = width.toDouble
      val h = height.toDouble
      val re: Double = (((x / w) * 2 - 1) / zoom + pan_x)
      val im: Double = (((y / h) * 2 - 1) / zoom + pan_y)

      var color: Float = 0
      Mandelbrot.check(Complex(re, im), iterations) match {
        case None => { color = 1 }
        case Some(m) => { color = m.toFloat / iterations }
      }

      if (bloom)
        color = color.floor

      buffer.setRGB(x, y, new Color(color, 0, 0).getRGB())
    }
  }

  draw()

  override def paint(g: Graphics) = {
    def drawPixel(x: Int, y: Int, size: Int) = g.fillRect(x, y, size, size)

    g.drawImage(buffer, 0, 0, width, height, null)
  }
}
