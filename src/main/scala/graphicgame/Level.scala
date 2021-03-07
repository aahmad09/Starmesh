package graphicgame

import scala.util.Random
import collection.mutable

class Level(val maze: Maze) {

  val offsets: Array[(Int, Int)] = Array((1, 0), (-1, 0), (0, 1),  (0, -1))

  val r: Random.type = scala.util.Random
  var enemyCount = 30
  private var _entities: Seq[Entity] = Nil
  while (enemyCount != 0) {
    val x = r.nextInt(100)
    val y = r.nextInt(100)
    val tempEnemy = new Enemy(x, y, this, false, 1)
    if (this.maze.isClear(tempEnemy.x, tempEnemy.y, tempEnemy.width, tempEnemy.height, tempEnemy)) {
      this += tempEnemy
      enemyCount -= 1
    }
  }

  def shortestPath(sx: Double, sy: Double, ex: Double, ey: Double, width: Double, height: Double, e: Entity): Int = {
    val queue = new ArrayQueue[(Double, Double, Double)]()
    val visited = mutable.Set[(Double, Double)]()
    queue.enqueue((sx, sy, 0))
    visited += sx -> sy
    while (!queue.isEmpty) {
      val (x, y, steps) = queue.dequeue()
      for ((ox, oy) <- offsets) {
        val nx = x + ox
        val ny = y + oy
        if (nx == ex && ny == ey) return steps.toInt + 1
        if (nx >= 0 && nx < maze.width && ny >= 0 && ny < maze.height
          && maze.isClear(nx, ny, width, height, e) && !visited(nx -> ny)) {
          queue.enqueue(nx, ny, steps + 1)
          visited += nx -> ny
        }
      }
    }
    1000000000
  }

  def entities: Seq[Entity] = _entities

  def +=(e: Entity): Unit = {
    _entities = e +: _entities
  }

  def -=(e: Entity): Unit = {
    _entities = _entities.filter(_ != e)
  }

  def updateAll(delay: Double): Unit = {
    _entities.foreach(_.update(delay))
  }

  def players: Seq[Player] = _entities.collect {case p: Player => p}

}

