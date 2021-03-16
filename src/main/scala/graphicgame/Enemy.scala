package graphicgame

import scala.util.Random


class Enemy(private var _x: Double, private var _y: Double,
            val level: Level,
            private var dead: Boolean) extends Entity {

  val speed = 3
  val shootDelayConstant = 1.5
  val targetProximity = 31
  private var shootDelay = shootDelayConstant

  def update(dt: Double): Unit = {
    val targetList: Seq[Option[Player]] = for (e <- level.players) yield Option(e)

    targetList.foreach {
      case None =>
      case Some(tgt) =>
        if (level.distanceFrom(x, y, tgt) < targetProximity) {
          Array(3 -> level.shortestPath(x, y - 1, tgt.x, tgt.y, width, height, this),
            2 -> level.shortestPath(x, y + 1, tgt.x, tgt.y, width, height, this),
            0 -> level.shortestPath(x + 1, y, tgt.x, tgt.y, width, height, this),
            1 -> level.shortestPath(x - 1, y, tgt.x, tgt.y, width, height, this)).minBy(_._2)._1
          match {
            case 0 => move(speed * dt, 0)
            case 1 => move(-speed * dt, 0)
            case 2 => move(0, speed * dt)
            case 3 => move(0, -speed * dt)
          }
          if (shootDelay <= 0) {
            shootBullet()
            shootDelay = shootDelayConstant
          } else {
            shootDelay -= dt
          }
        }
    }

    level.playerBullets.foreach { x =>
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

  def x: Double = _x

  def y: Double = _y

  def width: Double = 1.0

  def height: Double = 1.0

  def shootBullet(): Unit = {
    level += new Bullet(x, y, level, Random.nextInt(4), 8, false, false)
  }

  def isRemoved(): Boolean = dead

  def postCheck(): Unit = None
}