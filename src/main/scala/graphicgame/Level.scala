package graphicgame

import scala.collection.mutable

class Level(val maze: Maze,
            private var _entities: List[Entity]) {

  //spawn in entities and objects
  List(new Generator(9, 9, this, 0),
    new Generator(51, 51, this, 1),
    new Tower(3, 3, this, false, 0),
    new Tower(57, 57, this, false, 1)) ::: this

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

  def players: List[Player] = _entities.collect { case p: Player => p }

  def enemies: List[Enemy] = _entities.collect { case e: Enemy => e }

  def playerProjectiles: List[Projectile] = _entities.collect { case b: Projectile => b }.filter(_.isPlayerGenerated)

  def enemyProjectiles: List[Projectile] = _entities.collect { case b: Projectile => b }.filter(!_.isPlayerGenerated)

  def allProjectiles: List[Projectile] = _entities.collect { case b: Projectile => b }

  def towers: List[Tower] = _entities.collect { case t: Tower => t }

  def generators: List[Generator] = _entities.collect { case g: Generator => g }

}

