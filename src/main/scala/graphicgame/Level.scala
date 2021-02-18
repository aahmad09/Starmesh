package graphicgame

class Level(val maze: Maze) {

  private var _entities: Seq[Entity] = Seq(new Enemy(11, 12, this, false, 1),
    new Enemy(30, 29, this, false, 1), new Enemy(20, 20, this,
      false, 1))

  println("test start")

  def entities: Seq[Entity] = _entities

  def +=(e: Entity): Unit = {
    _entities = e +: _entities
    println(_entities)
  }

  println(entities)


  println("test end")

  def updateAll(delay: Double): Unit = {
    _entities.foreach(_.update(delay))
  }

}

