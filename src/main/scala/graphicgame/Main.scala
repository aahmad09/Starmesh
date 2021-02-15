package graphicgame

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.scene.control._
import scalafx.scene.shape._
import scalafx.event.ActionEvent
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.input._
import scalafx.animation.AnimationTimer

/* ✓
1. Get the maze drawing in the window. This requires floor and wall images to be
displayed.
2. Get a Player showing up on the maze.
3. Get an Enemy showing up on the maze.
4. Make the Player move with user input.
5. Make the Enemy move randomly.
6. Make it so they don’t go through walls.
*/

object Main extends JFXApp {
  val maze: Maze = new BasicMaze(Array(Array(Floor,Floor),Array(Wall,Floor)))
  val canvas = new Canvas(1000, 800)
  val gc: GraphicsContext = canvas.graphicsContext2D
  val renderer = new Renderer2D(gc, 30)
  var entities: Seq[Entity] = List[Entity]()
  var currentLevel = new Level(maze, entities)
  val player1 = new Player(1, 1, currentLevel)

  stage = new JFXApp.PrimaryStage {
    title = "Starmesh"
    scene = new Scene(1000, 800) {
      // Put your code here.
      fill = Color.Coral
      content += canvas
      renderer.render(player1.level,20,20)
    }
  }
}
