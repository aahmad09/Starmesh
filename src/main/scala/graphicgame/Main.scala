package graphicgame

import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.input.{KeyEvent, _}
import scalafx.scene.paint.Color

object Main extends JFXApp {

  val maze: Maze = RandomMaze(6, wrap = false, 10, 10, 0.6)
  val canvas = new Canvas(1200, 800)
  val gc: GraphicsContext = canvas.graphicsContext2D
  val renderer = new Renderer2D(gc, 20)

  val currentLevel = new Level(maze, List())
  val gen1 = new Generator(9, 9, currentLevel, 0)
  var player1 = new Player(22, 22, currentLevel)
  currentLevel += player1
  val gen2 = new Generator(51, 51, currentLevel, 1)
  currentLevel += new Tower(3, 3, currentLevel, false, 0)
  currentLevel += gen1
  currentLevel += gen2
  currentLevel += new Tower(57, 57, currentLevel, false, 1)


  stage = new JFXApp.PrimaryStage {
    title = "StarMesh"
    scene = new Scene(1200, 800) {
      fill = Color.Azure
      content += canvas

      var lastTime: Long = -1L
      val timer: AnimationTimer = AnimationTimer(time => {
        if (lastTime >= 0) {
          val dt = (time - lastTime) / 1e9
          currentLevel.updateAll(dt)
          renderer.render(currentLevel.makePassable(), player1.x, player1.y)
        }
        lastTime = time
      })
      timer.start()


      onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Left => player1.leftPressed()
          case KeyCode.Right => player1.rightPressed()
          case KeyCode.Up => player1.upPressed()
          case KeyCode.Down => player1.downPressed()
          case KeyCode.W => player1.fireUpPressed()
          case KeyCode.A => player1.fireLeftPressed()
          case KeyCode.S => player1.fireDownPressed()
          case KeyCode.D => player1.fireRightPressed()
          case _ =>
        }
      }

      onKeyReleased = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Left => player1.leftReleased()
          case KeyCode.Right => player1.rightReleased()
          case KeyCode.Up => player1.upReleased()
          case KeyCode.Down => player1.downReleased()
          case KeyCode.W => player1.fireUpReleased()
          case KeyCode.A => player1.fireLeftReleased()
          case KeyCode.S => player1.fireDownReleased()
          case KeyCode.D => player1.fireRightReleased()
          case _ =>
        }
      }

    }

  }
}
