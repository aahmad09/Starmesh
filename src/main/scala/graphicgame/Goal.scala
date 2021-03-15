package graphicgame

class Goal(private var _x: Double, private var _y: Double,
           val level: Level,
           private var picked: Boolean) extends Entity {

  def update(dt: Double): Unit = {
    if (Entity.intersect(this, level.players.head)) picked = true
  }

  def width: Double = 2

  def height: Double = 2

  def isRemoved(): Boolean = picked

  def postCheck(): Unit = None

  def x: Double = _x

  def y: Double = _y

}
