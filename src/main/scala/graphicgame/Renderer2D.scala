package graphicgame

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.image.Image
import scalafx.scene.paint.Color

class Renderer2D(gc: GraphicsContext, blockSize: Double) {
  // Put variables for images here
  private val floorImage = Renderer2D.loadImage("/images/floor.png")
  private val wallImage = Renderer2D.loadImage("/images/wall.png")
  private val playerImageRed = Renderer2D.loadImage("/images/playerRed.png")
  private val playerImageBlue = Renderer2D.loadImage("/images/playerBlue.png")
  private val enemyImageBlue = Renderer2D.loadImage("/images/enemyBlue.png")
  private val enemyImageRed = Renderer2D.loadImage("/images/enemyRed.png")
  private val generatorImageRed = Renderer2D.loadImage("/images/generatorRed.png")
  private val generatorImageBlue = Renderer2D.loadImage("/images/generatorBlue.png")
  private val projectileImage = Renderer2D.loadImage("/images/bullet.png")
  private val towerImageRed = Renderer2D.loadImage("/images/towerRed.png")
  private val towerImageBlue = Renderer2D.loadImage("/images/towerBlue.png")
  private var lastCenterX = 0.0
  private var lastCenterY = 0.0

  /**
   * These two methods are used to go from coordinates to blocks. You need them if you have mouse interactions.
   */
  def pixelsToBlocksX(px: Double): Double = (px - gc.canvas.getWidth / 2) / blockSize + lastCenterX

  def pixelsToBlocksY(py: Double): Double = (py - gc.canvas.getHeight / 2) / blockSize + lastCenterY

  def render(pLevel: PassableLevel, cx: Double, cy: Double): Unit = {
    lastCenterX = cx
    lastCenterY = cy

    val drawWidth = (gc.canvas.getWidth / blockSize).toInt + 1
    val drawHeight = (gc.canvas.getWidth / blockSize).toInt + 1

    // Draw walls and floors
    for {
      x <- cx.toInt - drawWidth / 2 - 1 to cx.toInt + drawWidth / 2 + 1
      y <- cy.toInt - drawHeight / 2 - 1 to cy.toInt + drawHeight / 2 + 1
    } {
      val img = pLevel.maze(x, y) match {
        case Wall => wallImage
        case Floor => floorImage
      }
      gc.drawImage(img, blocksToPixelsX(x), blocksToPixelsY(y), blockSize, blockSize)
    }

    // Draw entities
    for (e <- pLevel.entities) {
      val img = e.style match {
        case Styles_Teams.player => if (e.team == Styles_Teams.red) playerImageRed else playerImageBlue
        case Styles_Teams.enemy => if (e.team == Styles_Teams.red) enemyImageRed else enemyImageBlue
        case Styles_Teams.projectile => projectileImage
        case Styles_Teams.generator => if (e.team == Styles_Teams.red) generatorImageRed else generatorImageBlue
        case Styles_Teams.tower => if (e.team == Styles_Teams.red) towerImageRed else towerImageBlue
      }

      if (pLevel.maze.wrap) {
        for (rx <- -1 to 1; ry <- -1 to 1)
          gc.drawImage(img, blocksToPixelsX(e.x - e.width / 2 + rx * pLevel.maze.width),
            blocksToPixelsY(e.y - e.height / 2 + ry * pLevel.maze.height), e.width * blockSize, e.height * blockSize)
      } else {
        gc.drawImage(img, blocksToPixelsX(e.x - e.width / 2), blocksToPixelsY(e.y - e.height / 2), e.width * blockSize,
          e.height * blockSize)
      }

      //TODO: tower health
      gc.setStroke(Color.OrangeRed)
      gc.strokeText(pLevel.redScore.toString, 1180, 10)
      gc.setStroke(Color.Blue)
      gc.strokeText(pLevel.blueScore.toString, 10, 10)
    }

  }

  /**
   * methods to figure out where to draw things. They are used by the render.
   */
  def blocksToPixelsX(bx: Double): Double = gc.canvas.getWidth / 2 + (bx - lastCenterX) * blockSize

  def blocksToPixelsY(by: Double): Double = gc.canvas.getHeight / 2 + (by - lastCenterY) * blockSize


}

object Renderer2D {
  /**
   * This method assumes that you are putting your images in src/main/resources. This directory is
   * packaged into the JAR file. Eclipse doesn't use the JAR file, so this will go to the file in
   * the directory structure if it can't find the resource in the classpath. The argument should be the
   * path inside of the resources directory.
   */
  def loadImage(path: String): Image = {
    val res = getClass.getResource(path)
    if (res == null) {
      new Image("file:src/main/resources" + path)
    } else {
      new Image(res.toExternalForm)
    }
  }
}
