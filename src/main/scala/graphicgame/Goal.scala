package graphicgame

class Goal(private var _x: Double, private var _y: Double,
           val level: Level,
           private var picked: Boolean) extends Entity {

  def update(dt: Double): Unit = {
    val targetList: Seq[Option[Player]] = for (e <- level.players) yield Option(e)
    targetList.foreach {
      case None =>
      case Some(tgt) =>
        if (Entity.intersect(this, tgt)) picked = true
    }
  }

  def width: Double = 1

  def height: Double = 1

  def isRemoved(): Boolean = picked

  def postCheck(): Unit = None

  def x: Double = _x

  def y: Double = _y

}
