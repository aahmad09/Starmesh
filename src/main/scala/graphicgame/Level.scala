package graphicgame

import scala.collection.mutable
import scala.util.Random

class Level(val maze: Maze) {

  val offsets: Array[(Int, Int)] = Array((1, 0), (-1, 0), (0, 1), (0, -1))

  val r: Random.type = scala.util.Random
  private var enemyCount = 20
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


  def shortestPath(sx: Double, sy: Double, ex: Double, ey: Double,
                   width: Double, height: Double, e: Entity): Option[List[(Double, Double)]] = {
    val queue = new ArrayQueue[List[(Double, Double)]]()
    var visited = mutable.Set[(Double, Double)](sx -> sy)
    var solution: Option[List[(Double, Double)]] = None
    queue.enqueue(List(sx -> sy))
    visited += sx -> sy
    while (!queue.isEmpty && solution.isEmpty) {
      val steps@(x, y) :: _ = queue.dequeue()
      for ((ox, oy) <- offsets) {
        val nx = x + ox
        val ny = y + oy
        if ((nx - ex).abs < 1 && (ny - ey).abs < 1) solution = Some((nx -> ny) :: steps)
        if (maze.isClear(nx, ny, width, height, e) && !visited(nx -> ny)) {
          queue.enqueue((nx -> ny) :: steps)
          visited += nx -> ny
        }
      }
    }
    solution
  }

//  def closestEnemy(xPos: Double, yPos: Double): Enemy = {
//    val distList: List[(Double, Enemy)] = Nil
//    var min = 100000.0
//    var ret: Enemy = new Enemy(0, 0, this, false, 1)
//    enemies.foreach {
//      e => (math.sqrt((e.x - xPos) * (e.x - xPos) + (e.y - yPos) * (e.y - yPos)), e) :: distList
//    }
//
//    for (x <- distList) if (x._1 < min) {
//      min = x._1
//      ret = x._2
//    }
//    ret
//  }

  def enemies: Seq[Enemy] = _entities.collect { case e: Enemy => e }

  def entities: Seq[Entity] = _entities

  def +=(e: Entity): Unit = {
    _entities = e +: _entities
  }

  def -=(e: Entity): Unit = {
    _entities = _entities.filter(_ != e)
  }

  def updateAll(delay: Double): Unit = {
    _entities.foreach(_.update(delay))
    _entities = _entities.filterNot(_.stillHere())

  }

  def players: Seq[Player] = _entities.collect { case p: Player => p }


}

