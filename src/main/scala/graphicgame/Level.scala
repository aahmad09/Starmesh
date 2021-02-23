package graphicgame

import scala.util.Random

class Level(val maze: Maze) {

  val r: Random.type = scala.util.Random
  var enemyCount = 3
  private var _entities: Seq[Entity] = Nil
  while (enemyCount != 0) {
    val x = r.nextInt(40)
    val y = r.nextInt(40)
    val tempEnemy = new Enemy(x, y, this, false, 1)
    if (this.maze.isClear(tempEnemy.x, tempEnemy.y, tempEnemy.width, tempEnemy.height, tempEnemy)) {
      this += tempEnemy
      enemyCount -= 1
    }
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

}

