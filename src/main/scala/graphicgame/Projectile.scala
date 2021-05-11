package graphicgame

class Projectile(private var _x: Double, private var _y: Double,
                 val level: Level,
                 val dir: Int, val speed: Int, private var dead: Boolean,
                 private val playerGenerated: Boolean, val team: Int) extends Entity {


  def update(dt: Double): Unit = {
    val oppositeTeamTowers = if (team == Styles_Teams.red) level.towersBlue else if (team == Styles_Teams.blue) level.towersRed else Nil
    val playerTargetList: List[Player] = level.players
    val enemyTargetList: List[Enemy] = level.enemies
    val towersList: List[Tower] = oppositeTeamTowers
    val enemyProjectilesList: List[Projectile] = level.enemyProjectiles
    val playerProjectilesList: List[Projectile] = level.playerProjectiles

    // 0 -> right, 1 -> left, 2 -> down, 3 -> up
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


    //check for projectile collisions and remove the projectile
    if (isPlayerGenerated) {
      if (enemyProjectilesList.nonEmpty) enemyProjectilesList.foreach(tgt => if (Entity.intersect(this, tgt)) dead = true)
      if (enemyTargetList.nonEmpty) enemyTargetList.foreach(tgt => if (Entity.intersect(this, tgt)) dead = true)
      if (towersList.nonEmpty) towersList.foreach(tgt => if (Entity.intersect(this, tgt)) dead = true)
      else if (!isPlayerGenerated) {
        if (playerTargetList.nonEmpty) playerTargetList.foreach(tgt => if (Entity.intersect(this, tgt)) dead = true)
        if (playerProjectilesList.nonEmpty) playerProjectilesList.foreach(tgt => if (Entity.intersect(this, tgt)) dead = true)
      }
    }

  }

  def isPlayerGenerated: Boolean = playerGenerated

  def makePassable(): PassableEntity = PassableEntity(Styles_Teams.projectile, Styles_Teams.neutral, x, y, width, height)

  def width: Double = 0.5

  def height: Double = 0.5

  def x: Double = _x

  def y: Double = _y

  def isRemoved(): Boolean = dead

  def postCheck(): Unit = None

}
