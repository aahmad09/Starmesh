package graphicgame

import scala.util.Random

class Enemy(private var _x: Double, private var _y: Double,
            val level: Level,
            private var dead: Boolean,
            private var dir: Int) extends Entity {

  val speed = 1 //TODO: change to 4

  val r: Random.type = scala.util.Random
  dir = r.nextInt(4)


  def update(dt: Double): Unit = {
    //move in another random direction if enemy hits a wall
    //TODO: IF (Manhattan Distance) < Some value


//    println(_x + ", " + _y) //TODO:remove

    val offset: Option[List[(Double, Double)]] = level.shortestPath(_x, _y, level.players(0).x,
      level.players(0).y, width, height, this)

    offset match {
      case Some(dirs: List[(Double, Double)]) => for (i <- dirs.reverse.indices) {
        if (dirs(i)._1 > _x) move(speed * dt, 0)
        if (dirs(i)._1 < _x) move(-(speed * dt), 0)
        if (dirs(i)._2 > _y) move(0, speed * dt)
        if (dirs(i)._2 < _y) move(0, -(speed * dt))
      }
      case None => None
    }


    def move(dx: Double, dy: Double): Unit = {
      if (level.maze.isClear(_x + dx, _y + dy, width, height, this)) {
        _x += dx
        _y += dy
      }
    }

    //    offset match {
    //      case Some(dirs: List[(Double,Double)]) =>  for (i <- dirs.reverse.indices) {
    //        if ((dirs(i)._1 - _x) <= 1 && (dirs(i)._2 - _y) <= 0) _x += speed * dt
    //        if ((dirs(i)._1 - _x) <= -1 && (dirs(i)._2 - _y) <= 0) _x -= speed * dt
    //        if ((dirs(i)._1 - _x) <= 0 && (dirs(i)._2 - _y) <= 1) _y += speed * dt
    //        if ((dirs(i)._1 - _x) <= 0 && (dirs(i)._2 - _y) <= -1) _y -= speed * dt
    //      }
    //      case None => None
    //    }
    //

    if (r.nextInt(200) == 5) {
      level += new Bullet(_x, _y, level, r.nextInt(4), 6)
    }
  }

  override def width: Double = 3.0

  override def height: Double = 3.0

  def stillHere(): Boolean = true

  def postCheck(): Unit = ???

  def x: Double = _x

  def y: Double = _y
}