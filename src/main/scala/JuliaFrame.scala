
import java.awt.event.{InputEvent, KeyEvent, KeyListener}
import javax.swing.JFrame

class JuliaFrame(canvas_width: Int, canvas_height: Int, juliaSet: JuliaFamilySet) extends JFrame {
  setTitle("Bitmap")
  setSize(canvas_width, canvas_height)
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  val juliaSetDisplay = new MandelbrotCanvas(canvas_width, canvas_height, juliaSet)
  add(juliaSetDisplay)

  val keyboardListener = new KeyListener {
    override def keyTyped(e: KeyEvent) = ()
    override def keyReleased(e: KeyEvent) = ()
    override def keyPressed(e: KeyEvent) = {
      e.getModifiers() match {
        case InputEvent.SHIFT_MASK => {
          e.getKeyCode() match {
            case KeyEvent.VK_SEMICOLON => juliaSetDisplay.precision( 1)
            case KeyEvent.VK_QUOTE     => juliaSetDisplay.precision(-1)
            case _ => ()
          }
        }
        case _ => {
          e.getKeyCode() match {
            case KeyEvent.VK_UP    => juliaSetDisplay.pan( 0.0 , 0.2)
            case KeyEvent.VK_LEFT  => juliaSetDisplay.pan(-0.2 , 0.0)
            case KeyEvent.VK_RIGHT => juliaSetDisplay.pan( 0.2 , 0.0)
            case KeyEvent.VK_DOWN  => juliaSetDisplay.pan( 0.0 ,-0.2)
            case KeyEvent.VK_COMMA  => juliaSetDisplay.magnify(2.0)
            case KeyEvent.VK_PERIOD => juliaSetDisplay.magnify(0.5)
            case KeyEvent.VK_SEMICOLON  => juliaSetDisplay.precision( 50)
            case KeyEvent.VK_QUOTE      => juliaSetDisplay.precision(-50)
            case KeyEvent.VK_SLASH      => juliaSetDisplay.nextMode()
            case KeyEvent.VK_1 => juliaSetDisplay.juliaSet = new Mandelbrot
            case KeyEvent.VK_2 => juliaSetDisplay.juliaSet = new Julia(Complex(-0.73, 0.19))
            case KeyEvent.VK_3 => juliaSetDisplay.juliaSet = new BurningShip
            case KeyEvent.VK_Q => System.exit(0)
            case _ => ()
          }
        }
      }
    }
  }
  addKeyListener(keyboardListener)

  setVisible(true)
}
