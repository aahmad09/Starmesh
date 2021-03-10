package graphicgame

class Bullet(private var _x: Double, private var _y: Double,
             val level: Level,
             val dir: Int, val speed: Int) extends Entity {

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

    //    val target = level.closestEnemy(_x, _y)
    //    val path = level.shortestPath(_x, _y, target.x, target.y, width, height, this).get.reverse //TODO: get check
    //    var dx, dy = 0
    //    for (i <- path) {
    //      dx = (i._1 - _x).toInt
    //      dy = (i._2 - _y).toInt
    //      (dx,dy) match {
    //        case (1,0) => _x += speed * dt
    //        case (-1,0) => _x -= speed * dt
    //        case (0,1) => _y += speed * dt
    //        case (0,-1) => _y -= speed * dt
    //      }
    //    }

  }

  def width: Double = 1

  def height: Double = 1

  def stillHere(): Boolean = true

  def postCheck(): Unit = ???

  def x: Double = _x

  def y: Double = _y

}
