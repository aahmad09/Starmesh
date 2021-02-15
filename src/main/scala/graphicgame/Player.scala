package graphicgame

class Player(private var _x: Double, private var _y: Double, var level: Level) extends Entity {

  //  val level = new Level

  def update(delay: Double): Unit = ???

  override def width = 300.0

  override def height = 30.0

  def move(dx: Double, dy: Double): Unit = {
    _x += dx
    _y += dy
  }

  def stillHere(): Boolean = ???

  def postCheck(): Unit = ???

  def upPressed(): Unit = ???

  def downPressed(): Unit = ???

  def x: Double = _x

  def y: Double = _y

  //fireUpPressed():Unit
  //moveUpReleased():Unit
  //fireUpReleased():Unit

}