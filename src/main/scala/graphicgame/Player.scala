package graphicgame

class Player(private var _x: Double, private var _y: Double,
             val level: Level) extends Entity {

  val speed = 3
  private var upHeld, downHeld, leftHeld, rightHeld = false

  def update(dt: Double): Unit = {

    if (leftHeld) move(-(speed * dt), 0)
    if (rightHeld) move(speed * dt, 0)
    if (downHeld) move(0, speed * dt)
    if (upHeld) move(0, -(speed * dt))

  }

  def move(dx: Double, dy: Double): Unit = {
    if (level.maze.isClear(_x + dx, _y + dy, width, height, this)) {
      _x += dx
      _y += dy
    }
  }

  def stillHere(): Boolean = true

  def postCheck(): Unit = ???

  def x: Double = _x

  def y: Double = _y

  override def width = 1.0

  override def height = 1.0

  def upPressed(): Unit = upHeld = true

  def downPressed(): Unit = downHeld = true

  def leftPressed(): Unit = leftHeld = true

  def rightPressed(): Unit = rightHeld = true

  def upReleased(): Unit = upHeld = false

  def downReleased(): Unit = downHeld = false

  def leftReleased(): Unit = leftHeld = false

  def rightReleased(): Unit = rightHeld = false


  //fireUpPressed():Unit
  //fireUpReleased():Unit

}