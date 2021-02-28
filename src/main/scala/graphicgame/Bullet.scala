package graphicgame

class Bullet(private var _x: Double, private var _y: Double,
             val level: Level,
             private val dir: Int) extends Entity {

  val speed: Int = 8

  def update(dt: Double): Unit = {
    dir match {
      case 0 => if (level.maze.isClear(_x + speed * dt, _y, this.width, this.height, this))
        _x += speed * dt else level -= this
      case 1 => if (level.maze.isClear(_x - speed * dt, _y, this.width, this.height, this))
        _x -= speed * dt else level -= this
      case 2 => if (level.maze.isClear(_x, _y + speed * dt, this.width, this.height, this))
        _y += speed * dt else level -= this
      case 3 => if (level.maze.isClear(_x, _y - speed * dt, this.width, this.height, this))
        _y -= speed * dt else level -= this
    }
  }

  override def width: Double = 1

  override def height: Double = 1

  def stillHere(): Boolean = true

  def postCheck(): Unit = ???

  def x: Double = _x

  def y: Double = _y

}
