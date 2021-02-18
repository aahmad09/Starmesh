package graphicgame

import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.input.{KeyEvent, _}
import scalafx.scene.paint.Color

object Main extends JFXApp {

  val maze: Maze = RandomMaze(3, wrap = true, 25, 25, 0.6)
  val canvas = new Canvas(800, 800)
  val gc: GraphicsContext = canvas.graphicsContext2D
  val renderer = new Renderer2D(gc, 20)

  val currentLevel = new Level(maze)
  var player1 = new Player(20, 20, currentLevel)
  currentLevel += player1

  stage = new JFXApp.PrimaryStage {
    title = "StarMesh"
    scene = new Scene(800, 800) {
      fill = Color.Azure
      content += canvas

      onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Left => player1.leftPressed()
          case KeyCode.Right => player1.rightPressed()
          case KeyCode.Up => player1.upPressed()
          case KeyCode.Down => player1.downPressed()
          case _ =>
        }
      }

      onKeyReleased = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Left => player1.leftReleased()
          case KeyCode.Right => player1.rightReleased()
          case KeyCode.Up => player1.upReleased()
          case KeyCode.Down => player1.downReleased()
          case _ =>
        }
      }

      var lastTime: Long = -1L
      val timer: AnimationTimer = AnimationTimer(time => {
        if (lastTime >= 0) {
          val dt = (time - lastTime) / 1e9
          currentLevel.updateAll(dt)
          renderer.render(currentLevel, 20, 20)
        }
        lastTime = time
      })
      timer.start()

    }

  }
}
