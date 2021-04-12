package graphicgame

import scala.util.Random


class Enemy(private var _x: Double, private var _y: Double,
            val level: Level,
            private var dead: Boolean, val team: Int) extends Entity {

  val speed = 3
  val shootDelayConstant = 1.5
  val targetProximity = 31
  private var shootDelay = shootDelayConstant

  def update(dt: Double): Unit = {
    val closestTarget: Player = if (level.players.nonEmpty) level.players.minBy(level.distanceFrom(x, y, _)) else null

    if (closestTarget != null && level.distanceFrom(x, y, closestTarget) < targetProximity) {
      Array(3 -> level.shortestPath(x, y - 1, closestTarget.x, closestTarget.y, width, height, this),
        2 -> level.shortestPath(x, y + 1, closestTarget.x, closestTarget.y, width, height, this),
        0 -> level.shortestPath(x + 1, y, closestTarget.x, closestTarget.y, width, height, this),
        1 -> level.shortestPath(x - 1, y, closestTarget.x, closestTarget.y, width, height, this)).minBy(_._2)._1
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

    level.playerProjectiles.foreach { x =>
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

  def height: Double = 1.0

  def shootBullet(): Unit = level += new Projectile(x, y, level, Random.nextInt(4), 8, false, false)

  def width: Double = 1.0

  def x: Double = _x

  def y: Double = _y

  def makePassable(): PassableEntity = PassableEntity(1, team, x, y, width, height)

  def isRemoved(): Boolean = dead

  def postCheck(): Unit = None
}