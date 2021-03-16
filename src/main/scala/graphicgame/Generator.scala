package graphicgame

class Generator(private var _x: Double, private var _y: Double,
                val level: Level) extends Entity {

  val timerConstant = 0.1
  val spawnTimeConstant = 5.0
  val spawnProximity = 30
  private var spawnTime = spawnTimeConstant

  def update(dt: Double): Unit = {
    val targetList: Seq[Option[Player]] = for (e <- level.players) yield Option(e)

    targetList.foreach {
      case None =>
      case Some(tgt) =>
        if (spawnTime <= 0 && level.distanceFrom(x, y, tgt) < spawnProximity) {
          spawnEnemies()
          spawnTime = spawnTimeConstant
        } else {
          spawnTime -= dt
        }
    }
  }

  def spawnEnemies(): Unit = level += new Enemy(x, y, level, false)

  def y: Double = _y

  def x: Double = _x

  def width = 2

  def height = 2

  def postCheck(): Unit = None

  def isRemoved(): Boolean = false

}
