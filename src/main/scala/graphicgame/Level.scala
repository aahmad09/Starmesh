package graphicgame

class Level(val maze: Maze) {

  private var _entities: List[Entity] = List(new Enemy(20,30, this, false, 1),
    new Enemy(20,40, this, false, 1), new Enemy(20,50, this, false, 1))

  println("test start")

  def entities: List[Entity] = _entities

  def +=(e: Entity): Unit = {
    _entities = e :: _entities
    println(_entities)
  }

  println(entities)


  println("test end")

  def updateAll(delay: Double): Unit = ???

}

