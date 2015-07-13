
import java.awt.event.{KeyEvent, KeyListener}
import javax.swing.JFrame

class MandelbrotFrame extends JFrame {
  val canvas_width = 500
  val canvas_height = 500

  setTitle("Bitmap")
  setSize(canvas_width, canvas_height)
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  val mandelbrot = new MandelbrotCanvas(canvas_width, canvas_height)
  add(mandelbrot)

  val arrowListener = new KeyListener {
    override def keyTyped(e: KeyEvent) = ()
    override def keyReleased(e: KeyEvent) = ()
    override def keyPressed(e: KeyEvent) =
      e.getKeyCode() match {
        case KeyEvent.VK_UP    => mandelbrot.pan( 0.0 , 0.2)
        case KeyEvent.VK_LEFT  => mandelbrot.pan(-0.2 , 0.0)
        case KeyEvent.VK_RIGHT => mandelbrot.pan( 0.2 , 0.0)
        case KeyEvent.VK_DOWN  => mandelbrot.pan( 0.0 ,-0.2)
        case KeyEvent.VK_COMMA  => mandelbrot.magnify(2.0)
        case KeyEvent.VK_PERIOD => mandelbrot.magnify(0.5)
        case KeyEvent.VK_SEMICOLON  => mandelbrot.precision( 50)
        case KeyEvent.VK_QUOTE      => mandelbrot.precision(-50)
        case KeyEvent.VK_SLASH      => mandelbrot.nextMode()
        case KeyEvent.VK_Q => System.exit(0)
        case _ => ()
      }
  }
  addKeyListener(arrowListener)

  setVisible(true)
}
