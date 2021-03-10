package graphicgame

import scala.util.Random

class Enemy(private var _x: Double, private var _y: Double,
            val level: Level,
            private var dead: Boolean) extends Entity {

  val speed = 3
  val r: Random.type = scala.util.Random

  def update(dt: Double): Unit = {

    if ((this.x - level.players.head.x) < 20 && (this.y - level.players.head.y) < 20) {
      Array(3 -> level.shortestPath(_x, _y - 1, level.players.head.x, level.players.head.y, width, height, this),
        2 -> level.shortestPath(_x, _y + 1, level.players.head.x, level.players.head.y, width, height, this),
        0 -> level.shortestPath(_x + 1, _y, level.players.head.x, level.players.head.y, width, height, this),
        1 -> level.shortestPath(_x - 1, _y, level.players.head.x, level.players.head.y, width, height, this)).minBy(_._2)._1
      match {
        case 0 => if (level.maze.isClear(_x + speed * dt, _y, this.width, this.height, this)) _x += speed * dt
        case 1 => if (level.maze.isClear(_x - speed * dt, _y, this.width, this.height, this)) _x -= speed * dt
        case 2 => if (level.maze.isClear(_x, _y + speed * dt, this.width, this.height, this)) _y += speed * dt
        case 3 => if (level.maze.isClear(_x, _y - speed * dt, this.width, this.height, this)) _y -= speed * dt
      }
    }


    if (r.nextInt(200) == 5) level += new Bullet(_x, _y, level, r.nextInt(4), 6, true)

  }

  override def width: Double = 1.0

  override def height: Double = 1.0

  def stillHere(): Boolean = dead

  def postCheck(): Unit = ???

  def x: Double = _x

  def y: Double = _y
}