package graphicgame

import scala.util.Random

class Enemy(private var _x: Double, private var _y: Double,
            val level: Level,
            private var dead: Boolean,
            private var dir: Int) extends Entity {

  val speed = 4

  val r: Random.type = scala.util.Random
  dir = r.nextInt(4)

  def update(dt: Double): Unit = {
    //move in another random direction if enemy hits a wall
    dir match {
      case 0 => if (level.maze.isClear(_x + speed * dt, _y, this.width, this.height, this))
        _x += speed * dt else dir = r.nextInt(4)
      case 1 => if (level.maze.isClear(_x - speed * dt, _y, this.width, this.height, this))
        _x -= speed * dt else dir = r.nextInt(4)
      case 2 => if (level.maze.isClear(_x, _y + speed * dt, this.width, this.height, this))
        _y += speed * dt else dir = r.nextInt(4)
      case 3 => if (level.maze.isClear(_x, _y - speed * dt, this.width, this.height, this))
        _y -= speed * dt else dir = r.nextInt(4)
    }

    if (r.nextInt(200) == 5) {
      level += new Bullet(_x, _y, level, r.nextInt(4))
    }
  }

  override def width: Double = 3.0

  override def height: Double = 3.0

  def stillHere(): Boolean = true

  def postCheck(): Unit = ???

  def x: Double = _x

  def y: Double = _y
}