package graphicgame

class Projectile(private var _x: Double, private var _y: Double,
                 val level: Level,
                 val dir: Int, val speed: Int, private var dead: Boolean,
                 private val playerGenerated: Boolean) extends Entity {

  def update(dt: Double): Unit = {
    val playerTargetList: List[Option[Player]] = for (e <- level.players) yield Option(e)
    val enemyTargetList: List[Option[Enemy]] = for (e <- level.enemies) yield Option(e)
    val towersList: List[Option[Tower]] = for (e <- level.towers) yield Option(e)
    val enemyProjectilesList: List[Option[Projectile]] = for (e <- level.enemyProjectiles) yield Option(e)

    // 0 -> right, 1 -> left, 2 -> down, 3 -> up
    dir match {
      case 0 => if (level.maze.isClear(_x + speed * dt, _y, this.width, this.height, this))
        _x += speed * dt else dead = true
      case 1 => if (level.maze.isClear(_x - speed * dt, _y, this.width, this.height, this))
        _x -= speed * dt else dead = true
      case 2 => if (level.maze.isClear(_x, _y + speed * dt, this.width, this.height, this))
        _y += speed * dt else dead = true
      case 3 => if (level.maze.isClear(_x, _y - speed * dt, this.width, this.height, this))
        _y -= speed * dt else dead = true
    }

    if (!isPlayerGenerated) {
      playerTargetList.foreach {
        case None =>
        case Some(tgt) =>
          if (Entity.intersect(this, tgt)) dead = true
      }
    }

    if (isPlayerGenerated) {
      enemyTargetList.foreach {
        case None =>
        case Some(tgt) =>
          if (Entity.intersect(this, tgt)) dead = true
      }
    }

    if (isPlayerGenerated) {
      towersList.foreach {
        case None =>
        case Some(tgt) =>
          if (Entity.intersect(this, tgt)) dead = true
      }
    }

    if (isPlayerGenerated) {
      enemyProjectilesList.foreach {
        case None =>
        case Some(tgt) =>
          if (Entity.intersect(this, tgt)) dead = true
      }
    }


  }

  def isPlayerGenerated: Boolean = playerGenerated

  def width: Double = 0.5

  def height: Double = 0.5

  def makePassable(): PassableEntity = PassableEntity(2, 2, x, y, width, height)

  def x: Double = _x

  def y: Double = _y

  def isRemoved(): Boolean = dead

  def postCheck(): Unit = None

}
