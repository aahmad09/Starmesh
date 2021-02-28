package graphicgame

class Player(private var _x: Double, private var _y: Double,
             val level: Level) extends Entity {

  val speed = 3
  private var upHeld, downHeld, leftHeld, rightHeld, fireHeld = false

  private var bulletReloadTimer: Double = 0
  private var bulletDir: Int = 0

  def update(dt: Double): Unit = {
    bulletReloadTimer += dt

    if (leftHeld) {
      move(-(speed * dt), 0); bulletDir = 1
    }
    if (rightHeld) {
      move(speed * dt, 0); bulletDir = 0
    }
    if (downHeld) {
      move(0, speed * dt); bulletDir = 2
    }
    if (upHeld) {
      move(0, -(speed * dt)); bulletDir = 3
    }
    if (fireHeld && bulletReloadTimer > 1.0) {
      level += new Bullet(_x, _y, level, bulletDir)
      bulletReloadTimer = 0
    }
  }

  def move(dx: Double, dy: Double): Unit = {
    if (level.maze.isClear(_x + dx, _y + dy, width, height, this)) {
      _x += dx
      _y += dy
    }
  }

  override def width = 2.5

  override def height = 2.5

  def stillHere(): Boolean = true

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

  def fireUpPressed(): Unit = fireHeld = true

  def fireUpReleased(): Unit = fireHeld = false

}