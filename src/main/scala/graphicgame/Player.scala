package graphicgame

class Player(private var _x: Double, private var _y: Double,
             val level: Level) extends Entity {

  val speed = 5
  val reloadTimeConstant: Double = 1.0
  private var dead: Boolean = false
  private var upHeld, downHeld, leftHeld, rightHeld, fireUp, fireDown, fireRight, fireLeft = false
  private var bulletReloadTimer = reloadTimeConstant

  def update(dt: Double): Unit = {

    if (leftHeld) move(-(speed * dt), 0)
    if (rightHeld) move(speed * dt, 0)
    if (downHeld) move(0, speed * dt)
    if (upHeld) move(0, -(speed * dt))

    if (bulletReloadTimer <= 0) {
      shootBullet()
      bulletReloadTimer = reloadTimeConstant
    } else {
      bulletReloadTimer -= dt
    }

    level.enemyProjectiles.par.foreach { x =>
      if (Entity.intersect(this, x)) {
        dead = true
      }
    }

    level.enemies.par.foreach { x =>
      if (Entity.intersect(this, x)) {
        dead = true
      }
    }

    isGameOver()
  }

  def move(dx: Double, dy: Double): Unit = {
    if (level.maze.isClear(x + dx, y + dy, width, height, this)) {
      _x += dx
      _y += dy
    }
  }

  def width = 1

  def height = 1

  def shootBullet(): Unit = {
    if (fireLeft) level += new Projectile(x, y, level, 1, 8, false, true)
    if (fireRight) level += new Projectile(x, y, level, 0, 8, false, true)
    if (fireUp) level += new Projectile(x, y, level, 3, 8, false, true)
    if (fireDown) level += new Projectile(x, y, level, 2, 8, false, true)
  }

  def x: Double = _x

  def y: Double = _y

  def isGameOver(): Unit = if (dead) println("Game Over!")

  def makePassable(): PassableEntity = PassableEntity(0, 2, x, y, width, height)

  def isRemoved(): Boolean = dead

  def postCheck(): Unit = None

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