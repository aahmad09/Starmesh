package graphicgame

import scalafx.scene.input.{KeyCode, KeyEvent}

class Player(private var _x: Double, private var _y: Double, private var _level: Level) extends Entity {

  def x: Double = _x

  def y: Double = _y

  override def width = 2.0

  override def height = 2.0

  def update(delay: Double): Unit = ???

  def level: Level = _level

  def move(dx: Double, dy: Double): Unit = {
    _x += dx
    _y += dy
  }

  def stillHere(): Boolean = ???

  def postCheck(): Unit = ???

  def upPressed(): Unit = ???

  def downPressed(): Unit = ???

//  onKeyPressed = (ke: KeyEvent) => {
//    ke.code match {
//      case KeyCode.Up => box.y = box.y.value - 2
//      case KeyCode.Down => box.y = box.y.value + 2
//      case KeyCode.Left => box.x = box.x.value - 2
//      case KeyCode.Right => box.x = box.x.value + 2
//      case _ =>
//    }
//  }
  //fireUpPressed():Unit
  //moveUpReleased():Unit
  //fireUpReleased():Unit

}