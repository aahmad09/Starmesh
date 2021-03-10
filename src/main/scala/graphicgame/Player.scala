package graphicgame

class Player(private var _x: Double, private var _y: Double,
             val level: Level) extends Entity {

  val speed = 5
  private var dead: Boolean = false
  private var upHeld, downHeld, leftHeld, rightHeld, fireUp, fireDown, fireRight, fireLeft = false

  private var bulletReloadTimer: Double = 0

  def update(dt: Double): Unit = {
    bulletReloadTimer += dt

    if (leftHeld) move(-(speed * dt), 0)
    if (rightHeld) move(speed * dt, 0)
    if (downHeld) move(0, speed * dt)
    if (upHeld) move(0, -(speed * dt))

    if (bulletReloadTimer > 0.6) {
      if (fireLeft) level += new Bullet(_x, _y, level, 1, 8, false); bulletReloadTimer = 0
      if (fireRight) level += new Bullet(_x, _y, level, 0, 8, false); bulletReloadTimer = 0
      if (fireUp) level += new Bullet(_x, _y, level, 3, 8, false); bulletReloadTimer = 0
      if (fireDown) level += new Bullet(_x, _y, level, 2, 8, false); bulletReloadTimer = 0
    }
  }

  def move(dx: Double, dy: Double): Unit = {
    if (level.maze.isClear(_x + dx, _y + dy, width, height, this)) {
      _x += dx
      _y += dy
    }
  }


  override def width = 1

  override def height = 1

  def stillHere(): Boolean = dead

  def postCheck(): Unit = ???

  def x: Double = _x

  def y: Double = _y

  def upPressed(): Unit = upHeld = true

  def downPressed(): Unit = downHeld = true

  def leftPressed(): Unit = leftHeld = true

  def rightPressed(): Unit = rightHeld = true

  def upReleased(): Unit = upHeld = false

  def downReleased(): Unit = downHeld = false

  def leftReleased(): Unit = leftHeld = false

  def rightReleased(): Unit = rightHeld = false

  def fireUpPressed(): Unit = fireUp = true

  def fireUpReleased(): Unit = fireUp = false

  def fireDownPressed(): Unit = fireDown = true

  def fireDownReleased(): Unit = fireDown = false

  def fireRightPressed(): Unit = fireRight = true

  def fireRightReleased(): Unit = fireRight = false

  def fireLeftPressed(): Unit = fireLeft = true

  def fireLeftReleased(): Unit = fireLeft = false

}