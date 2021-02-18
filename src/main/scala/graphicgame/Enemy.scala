package graphicgame

class Enemy(private var _x: Double, private var _y: Double,
            var level: Level,
            private var dead: Boolean,
            private var dir: Int) extends Entity {

  override def width = 1.5

  override def height = 1.5


  def update(delay: Double): Unit = None

  def stillHere(): Boolean = ???

  def postCheck(): Unit = ???

  def x: Double = _x

  def y: Double = _y
}