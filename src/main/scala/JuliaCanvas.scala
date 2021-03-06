
import java.awt.{Color, Graphics}
import java.awt.image.BufferedImage
import javax.swing.JPanel

private abstract class Mode {
  def next: Mode
}

private case class BloomMode extends Mode {
  override def next: Mode = OutlineMode()
}
private case class OutlineMode extends Mode {
  override def next: Mode = FlatMode()
}
private case class FlatMode extends Mode {
  override def next: Mode = InvertedMode()
}
private case class InvertedMode extends Mode {
  override def next: Mode = SemiInvertedMode()
}
private case class SemiInvertedMode extends Mode {
  override def next: Mode = BloomMode()
}

class MandelbrotCanvas(
  width: Int,
  height: Int,
  var _juliaSet: JuliaFamilySet
) extends JPanel {

  def juliaSet = _juliaSet
  def juliaSet_=(set: JuliaFamilySet) {
    _juliaSet = set
    redraw()
  }


  private val buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

  private var pan_x = -0.5
  private var pan_y =  0.0
  private var zoom  =  1.0
  private var iterations = 50
  private var mode: Mode = BloomMode()

  def redraw() = {
    draw()
    repaint()
  }

  def pan(x: Double, y: Double) = {
    pan_x += x / zoom
    pan_y -= y / zoom
    redraw()
  }

  def magnify(n: Double) = {
    zoom *= n
    redraw()
  }

  def precision(n: Int) = {
    iterations = Math.max(iterations + n, 1)
    redraw()
  }

  def nextMode() = {
    mode = mode.next
    redraw()
  }

  private def draw() = {
    for {
      y <- Range(0, height).par
      x <- Range(0, width).par
    } {
      val color = getPixelColor(x, y)
      buffer.setRGB(x, y, color.getRGB())
    }
  }

  private def getPixelColor(x: Int, y: Int): Color = {
    val w = width.toDouble
    val h = height.toDouble
    val ratio = w / h
    val re: Double = ((ratio*((x / w) * 2 - 1)) / zoom + pan_x)
    val im: Double = (((y / h) * 2 - 1) / zoom + pan_y)

    var color: Float = 0
    juliaSet.check(Complex(re, im), iterations) match {
      case None => { color = 1 }
      case Some(m) => { color = m.toFloat / iterations }
    }

    mode match {
      case BloomMode()        => ()
      case OutlineMode()      => { if (color == 1) color = 0 }
      case FlatMode()         => { color = color.floor }
      case InvertedMode()     => { color = 1 - color }
      case SemiInvertedMode() => { if (color < 1) color = 1 - color }
    }

    new Color(color, 0, 0)
  }

  draw()

  override def paint(g: Graphics) =
    g.drawImage(buffer, 0, 0, width, height, null)
}
