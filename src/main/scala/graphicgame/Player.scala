package graphicgame

class Player(private var _x: Double, private var _y: Double, val level: Level) extends Entity {

  val speed = 100
  private var upHeld = false
  private var downHeld = false
  private var leftHeld = false
  private var rightHeld = false

  override def width = 2.0

  override def height = 2.0

  def update(dt: Double): Unit = {
    if (leftHeld) move(-(speed * dt), 0)
    if (rightHeld) move(speed * dt, 0)
    if (downHeld) move(0, speed * dt)
    if (upHeld) move(0, -(speed * dt))
  }

  def move(dx: Double, dy: Double): Player = {
    println(s"${dx} , ${dy}")
    new Player(_x + dx, _y + dy, level)
  }

  def x: Double = _x

  def y: Double = _y

  def stillHere(): Boolean = ???

  def postCheck(): Unit = ???

  def upPressed(): Unit = upHeld = true

  def downPressed(): Unit = downHeld = true

  def leftPressed(): Unit = leftHeld = true

  def rightPressed(): Unit = rightHeld = true

  def upReleased(): Unit = upHeld = false

  def downReleased(): Unit = downHeld = false

  def leftReleased(): Unit = leftHeld = false

  def rightReleased(): Unit = rightHeld = false


  //fireUpPressed():Unit
  //moveUpReleased():Unit
  //fireUpReleased():Unit

}