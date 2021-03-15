package graphicgame

import scala.util.Random

class Enemy(private var _x: Double, private var _y: Double,
            val level: Level,
            private var _dead: Boolean) extends Entity {

  val speed = 3
  val r: Random.type = scala.util.Random

  def update(dt: Double): Unit = {

    if ((_x - level.players.head.x).abs < 13 && (_y - level.players.head.y).abs < 13) {
      Array(3 -> level.shortestPath(_x, _y - 1.0, level.players.head.x, level.players.head.y, width, height, this),
        2 -> level.shortestPath(_x, _y + 1.0, level.players.head.x, level.players.head.y, width, height, this),
        0 -> level.shortestPath(_x + 1.0, _y, level.players.head.x, level.players.head.y, width, height, this),
        1 -> level.shortestPath(_x - 1.0, _y, level.players.head.x, level.players.head.y, width, height, this)).minBy(_._2)._1
      match {
        case 0 => move(speed * dt, 0)
        case 1 => move(-speed * dt, 0)
        case 2 => move(0, speed * dt)
        case 3 => move(0, -speed * dt)
      }
      if (r.nextInt(200) == 5) level += new Bullet(_x, _y, level, r.nextInt(4), 6, false)
    }

  }

  def move(dx: Double, dy: Double): Unit = {
    if (level.maze.isClear(_x + dx, _y + dy, width, height, this)) {
      _x += dx
      _y += dy
    }
  }

  override def width: Double = 1.0

  override def height: Double = 1.0

  def isRemoved(): Boolean = _dead

  def postCheck(): Unit = None

  def x: Double = _x

  def y: Double = _y
}