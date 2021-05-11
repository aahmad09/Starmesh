package graphicgame

import scala.collection.mutable

class Level(val maze: Maze,
            private var _entities: List[Entity]) {

  //spawn in entities and objects
  List(new Generator(9, 9, this, Styles_Teams.blue),
    new Generator(51, 51, this, Styles_Teams.red),
    new Tower(3, 3, this, false, Styles_Teams.blue),
    new Tower(57, 57, this, false, Styles_Teams.red)) ::: this

  def shortestPath(sx: Double, sy: Double, ex: Double, ey: Double,
                   width: Double, height: Double, e: Entity): Int = {
    val offsets: Array[(Int, Int)] = Array((1, 0), (-1, 0), (0, 1), (0, -1))
    val queue = new ArrayQueue[(Double, Double, Int)]()
    val visited = mutable.Set[(Double, Double)](sx -> sy)
    if (maze.isClear(sx, sy, width, height, e)) {
      queue.enqueue((sx, sy, 0))
    }
    visited += sx -> sy
    while (!queue.isEmpty) {
      val (x, y, steps) = queue.dequeue()
      for ((ox, oy) <- offsets) {
        val nx = x + ox
        val ny = y + oy
        if ((nx - ex).abs < 0.5 && (ny - ey).abs < 0.5) return steps + 1
        if (maze.isClear(nx, ny, width, height, e) && !visited(nx -> ny)) {
          queue.enqueue(nx, ny, steps + 1)
          visited += nx -> ny
        }
      }
    }
    10000000
  }

  def entities: List[Entity] = _entities

  def +=(e: Entity): Unit = _entities = e +: _entities

  def :::(listEntities: List[Entity]): Unit = _entities = _entities ::: listEntities

  def updateAll(delay: Double): Unit = {
    _entities.foreach(_.update(delay))
    _entities = _entities.filterNot(_.isRemoved())
  }

  def distanceFrom(x: Double, y: Double, e: Entity): Double = math.abs(x - e.x) + math.abs(y - e.y)

  def makePassable(): PassableLevel = PassableLevel(maze, _entities.map(_.makePassable()))

  def players: List[Player] = playersBlue ::: playersRed

  def playersBlue: List[Player] = _entities.collect { case p: Player => p }.filter(_.team == Styles_Teams.blue)

  def playersRed: List[Player] = _entities.collect { case p: Player => p }.filter(_.team == Styles_Teams.red)

  def enemies: List[Enemy] = enemiesBlue ::: enemiesRed

  def enemiesBlue: List[Enemy] = _entities.collect { case e: Enemy => e }.filter(_.team == Styles_Teams.blue)

  def enemiesRed: List[Enemy] = _entities.collect { case e: Enemy => e }.filter(_.team == Styles_Teams.red)

  def playerProjectiles: List[Projectile] = _entities.collect { case b: Projectile => b }.filter(_.isPlayerGenerated)

  def playerProjectilesBlue: List[Projectile] = _entities.collect { case b: Projectile => b }.filter(x => x.isPlayerGenerated && x.team == Styles_Teams.blue)

  def playerProjectilesRed: List[Projectile] = _entities.collect { case b: Projectile => b }.filter(x => x.isPlayerGenerated && x.team == Styles_Teams.red)

  def enemyProjectiles: List[Projectile] = _entities.collect { case b: Projectile => b }.filter(!_.isPlayerGenerated)

  def allProjectiles: List[Projectile] = _entities.collect { case b: Projectile => b }

  def towersBlue: List[Tower] = _entities.collect { case t: Tower => t }.filter(_.team == Styles_Teams.blue)

  def towersRed: List[Tower] = _entities.collect { case t: Tower => t }.filter(_.team == Styles_Teams.red)

  def generatorsBlue: List[Generator] = _entities.collect { case g: Generator => g }.filter(_.team == Styles_Teams.blue)

  def generatorsRed: List[Generator] = _entities.collect { case g: Generator => g }.filter(_.team == Styles_Teams.red)

}

