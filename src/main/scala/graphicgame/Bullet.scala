package graphicgame

class Bullet(private var _x: Double, private var _y: Double,
             val level: Level,
             val dir: Int, val speed: Int, private var dead: Boolean) extends Entity {

  def update(dt: Double): Unit = {
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
  }

  def width: Double = 1

  def height: Double = 1

  def stillHere(): Boolean = dead

  def postCheck(): Unit = ???

  def x: Double = _x

  def y: Double = _y

}
