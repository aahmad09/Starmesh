package graphicgame

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.input.KeyEvent
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
      renderer.render(currentLevel, 20, 20)
//      onKeyPressed = (ke: KeyEvent) => {
//        ke.code match {
//          case KeyCode.Up => box.y = box.y.value - 2
//          case KeyCode.Down => box.y = box.y.value + 2
//          case KeyCode.Left => box.x = box.x.value - 2
//          case KeyCode.Right => box.x = box.x.value + 2
//          case _ =>
//        }
//      }
    }
  }
}
