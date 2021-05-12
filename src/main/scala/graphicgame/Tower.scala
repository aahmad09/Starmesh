package graphicgame

class Tower(private var _x: Double, private var _y: Double,
            val level: Level,
            private var destroyed: Boolean, val team: Int) extends Entity {

  private var health: Int = 10

  def update(dt: Double): Unit = {
    val oppositeTeamProjectiles = if (team == Styles_Teams.red) level.playerProjectilesBlue else level.playerProjectilesRed
    val targetList: List[Option[Projectile]] = for (e <-oppositeTeamProjectiles) yield Option(e)
    targetList.foreach {
      case None =>
      case Some(tgt) =>
        if (Entity.intersect(this, tgt)) health -= 1
        if (health <= 0) destroyed = true
    }
  }

  def makePassable(): PassableEntity = PassableEntity(Styles_Teams.tower, team, x, y, width, height)

  def width: Double = 3.5

  def height: Double = 3.5

  def x: Double = _x

  def y: Double = _y

  def getTeam: Int = team

  def getScore: Int = health

  def isRemoved(): Boolean = destroyed

  def postCheck(): Unit = None

}
