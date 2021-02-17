package graphicgame

import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.input.{KeyEvent, _}
import scalafx.scene.paint.Color

/**
 * ✓ Get the maze drawing in the window. This requires floor and wall images to be displayed.
 * ✓ Get a Player showing up on the maze.
 * ✓ Get an Enemy showing up on the maze.
 * 4. Make the Player move with user input.
 * 5. Make the Enemy move randomly.
 * 6. Make it so they don’t go through walls.
 * */

object Main extends JFXApp {

  val maze: Maze = RandomMaze(3, wrap = false, 20, 20, 0.6)
  val canvas = new Canvas(800, 800)
  val gc: GraphicsContext = canvas.graphicsContext2D
  val renderer = new Renderer2D(gc, 20)

  val currentLevel = new Level(maze)
  val player1 = new Player(20, 20, currentLevel)
  currentLevel += player1

  stage = new JFXApp.PrimaryStage {
    title = "StarMesh"
    scene = new Scene(800, 800) {
      fill = Color.Azure
      content += canvas

      onKeyPressed = (ke: KeyEvent) => {
        if (ke.code == KeyCode.Left) player1.leftPressed()
        if (ke.code == KeyCode.Right) player1.rightPressed()
        if (ke.code == KeyCode.Up) player1.upPressed()
        if (ke.code == KeyCode.Down) player1.downPressed()
      }
      onKeyReleased = (ke: KeyEvent) => {
        if (ke.code == KeyCode.Left) player1.leftReleased()
        if (ke.code == KeyCode.Right) player1.rightReleased()
        if (ke.code == KeyCode.Up) player1.upReleased()
        if (ke.code == KeyCode.Down) player1.downReleased()
      }

      var lastTime: Long = -1L
      val timer: AnimationTimer = AnimationTimer { time =>
        if (lastTime >= 0) {
          val dt = (time - lastTime) / 1e9
          currentLevel.updateAll(dt)
          renderer.render(currentLevel, 20, 20)
        }
        lastTime = time
      }
      timer.start()

    }

  }
}
