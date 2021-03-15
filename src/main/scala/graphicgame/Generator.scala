package graphicgame

class Generator(private var _x: Double, private var _y: Double,
                val level: Level) extends Entity {

  val timerConstant = 0.1
  val spawnTimeConstant = 5.0
  private var timer = timerConstant
  private var _animationTexture = 0
  private var spawnTime = spawnTimeConstant

  def update(dt: Double): Unit = {
    if (timer <= 0) {
      _animationTexture = (_animationTexture + 1) % 20
      timer = timerConstant
    } else {
      timer -= dt
    }

    if (spawnTime <= 0) {
      spawnEnemies()
      spawnTime = spawnTimeConstant
    } else {
      spawnTime -= dt
    }
  }

  def spawnEnemies(): Unit = {
    level += new Enemy(x, y, level, false)
  }

  def x: Double = _x

  def y: Double = _y

  def width = 2

  def height = 2

  def postCheck(): Unit = None

  def isRemoved(): Boolean = false

  def animationTexture: Int = _animationTexture

}
