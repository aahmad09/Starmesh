package graphicgame

class Generator(private var _x: Double, private var _y: Double,
                val level: Level, val team: Int) extends Entity {

  val spawnTimeConstant = 5.0
  val spawnProximity = 30

  private var spawnTime = spawnTimeConstant

  def update(dt: Double): Unit = {
    val oppositeTeamPlayers: List[Player] = if (team == Styles_Teams.red) level.playersBlue else if (team == Styles_Teams.blue) level.playersRed else Nil
    val targetList: List[Option[Player]] = for (e <- oppositeTeamPlayers) yield Option(e)

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

  def spawnEnemies(): Unit = level += new Enemy(x, y, level, false, team)

  def x: Double = _x

  def y: Double = _y

  def makePassable(): PassableEntity = PassableEntity(Styles_Teams.generator, team, x, y, width, height)

  def width = 2

  def height = 2

  def postCheck(): Unit = None

  def isRemoved(): Boolean = false

}
