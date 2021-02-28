package graphicgame

class Bullet(private var _x: Double, private var _y: Double) extends Entity {

  val speed: Int = 5

  def update(delay: Double): Unit = ???

  def stillHere(): Boolean = true

  def postCheck(): Unit = ???

  def x: Double = _x

  def y: Double = _y

  override def width: Double = 0.25

  override def height: Double = 0.25

}
