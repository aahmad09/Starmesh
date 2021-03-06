package graphicgame

class Player(private var _x: Double, private var _y: Double,
             val level: Level, val team: Int) extends Entity {

  val speed = 5
  val reloadTimeConstant: Double = 1.0
  private var dead: Boolean = false
  private var upHeld, downHeld, leftHeld, rightHeld, fireUp, fireDown, fireRight, fireLeft = false
  private var bulletReloadTimer = reloadTimeConstant

  def update(dt: Double): Unit = {
    val oppositeTeam: Seq[Enemy] = if (team == Styles_Teams.red) level.enemiesBlue else level.enemiesRed

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

    oppositeTeam.par.foreach { x =>
      if (Entity.intersect(this, x)) {
        dead = true
      }
    }
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
    if (fireLeft) level += new Projectile(x, y, level, 1, 8, false, true, team)
    if (fireRight) level += new Projectile(x, y, level, 0, 8, false, true, team)
    if (fireUp) level += new Projectile(x, y, level, 3, 8, false, true, team)
    if (fireDown) level += new Projectile(x, y, level, 2, 8, false, true, team)
  }

  def x: Double = _x

  def y: Double = _y

  def isDead: Boolean = dead

  def makePassable(): PassableEntity = PassableEntity(Styles_Teams.player, team, x, y, width, height)

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