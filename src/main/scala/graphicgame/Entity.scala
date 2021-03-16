package graphicgame

trait Entity extends Serializable {

  def x: Double

  def y: Double

  def width: Double

  def height: Double

  def update(dt: Double): Unit

  def postCheck(): Unit // post check to check intersections
  def isRemoved(): Boolean // remove entities from the level.
}

object Entity {
  def intersect(e1: Entity, e2: Entity): Boolean = {
    ((e1.x - e2.x).abs < (e1.width + e2.width) / 2) && ((e1.y - e2.y).abs < (e1.height + e2.height) / 2)
  }


}