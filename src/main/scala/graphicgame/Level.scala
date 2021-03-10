package graphicgame

import scala.collection.mutable
import scala.util.Random

class Level(val maze: Maze) {

  val offsets: Array[(Int, Int)] = Array((1, 0), (-1, 0), (0, 1), (0, -1))

  val r: Random.type = scala.util.Random
  private var enemyCount = 50
  private var _entities: Seq[Entity] = Nil
  while (enemyCount != 0) {
    val x = r.nextInt(100)
    val y = r.nextInt(100)
    val tempEnemy = new Enemy(x, y, this, false)
    if (this.maze.isClear(tempEnemy.x, tempEnemy.y, tempEnemy.width, tempEnemy.height, tempEnemy)) {
      this += tempEnemy
      enemyCount -= 1
    }
  }

  def shortestPath(sx: Double, sy: Double, ex: Double, ey: Double,
                   width: Double, height: Double, e: Entity): Int = {
    val queue = new ArrayQueue[(Double, Double, Int)]()
    var visited = mutable.Set[(Double, Double)](sx -> sy)
    if (maze.isClear(sx,sy,width,height,e)) {
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
          queue.enqueue(nx, ny,  steps+1)
          visited += nx -> ny
        }
      }
    }
    10000000
  }

  def entities: Seq[Entity] = _entities

  def +=(e: Entity): Unit = {
    _entities = e +: _entities
  }

  def updateAll(delay: Double): Unit = {
    _entities.foreach(_.update(delay))
    _entities = _entities.filterNot(_.stillHere())

  }

  def players: Seq[Player] = _entities.collect { case p: Player => p }

  def enemies: Seq[Enemy] = _entities.collect { case e: Enemy => e }

  def bullets: Seq[Bullet] = _entities.collect { case e: Bullet => e }


}

