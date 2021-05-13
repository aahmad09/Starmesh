package graphicgame

import scalafx.Includes._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.control.TextInputDialog
import scalafx.scene.input.{KeyCode, KeyEvent}

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.Socket
import scala.concurrent.Future

object Client extends JFXApp {
  val canvas = new Canvas(800, 800)
  val gc: GraphicsContext = canvas.graphicsContext2D
  val renderer = new Renderer2D(gc, blockSize = 20)

  val dialog = new TextInputDialog("localhost")
  dialog.contentText = "What machine is the server running on?"
  dialog.title = "Server"
  dialog.headerText = "Server Machine"
  val serverMachine: String = dialog.showAndWait().getOrElse("localhost")

  val sock = new Socket(serverMachine, 8080)
  val ois = new ObjectInputStream(sock.getInputStream)
  val oos = new ObjectOutputStream(sock.getOutputStream)

  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  Future {
    while (true) {
      ois.readObject() match {
        case UpdateInfo(plevel, cx, cy) =>
          Platform.runLater(renderer.render(plevel, cx, cy))
        case _ =>
      }
    }
  }

  stage = new JFXApp.PrimaryStage {
    title = "StarMesh"
    scene = new Scene(800, 800) {
      content += canvas

      onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Left => oos.writeInt(ControlKeys.Pressed); oos.writeInt(ControlKeys.MoveLeft); oos.flush()
          case KeyCode.Right => oos.writeInt(ControlKeys.Pressed); oos.writeInt(ControlKeys.MoveRight); oos.flush()
          case KeyCode.Up => oos.writeInt(ControlKeys.Pressed); oos.writeInt(ControlKeys.MoveUp); oos.flush()
          case KeyCode.Down => oos.writeInt(ControlKeys.Pressed); oos.writeInt(ControlKeys.MoveDown); oos.flush()
          case KeyCode.W => oos.writeInt(ControlKeys.Pressed); oos.writeInt(ControlKeys.ShootUp); oos.flush()
          case KeyCode.A => oos.writeInt(ControlKeys.Pressed); oos.writeInt(ControlKeys.ShootLeft); oos.flush()
          case KeyCode.S => oos.writeInt(ControlKeys.Pressed); oos.writeInt(ControlKeys.ShootDown); oos.flush()
          case KeyCode.D => oos.writeInt(ControlKeys.Pressed); oos.writeInt(ControlKeys.ShootRight); oos.flush()
          case _ =>
        }
      }
      onKeyReleased = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Left => oos.writeInt(ControlKeys.Released); oos.writeInt(ControlKeys.MoveLeft); oos.flush()
          case KeyCode.Right => oos.writeInt(ControlKeys.Released); oos.writeInt(ControlKeys.MoveRight); oos.flush()
          case KeyCode.Up => oos.writeInt(ControlKeys.Released); oos.writeInt(ControlKeys.MoveUp); oos.flush()
          case KeyCode.Down => oos.writeInt(ControlKeys.Released); oos.writeInt(ControlKeys.MoveDown); oos.flush()
          case KeyCode.W => oos.writeInt(ControlKeys.Released); oos.writeInt(ControlKeys.ShootUp); oos.flush()
          case KeyCode.A => oos.writeInt(ControlKeys.Released); oos.writeInt(ControlKeys.ShootLeft); oos.flush()
          case KeyCode.S => oos.writeInt(ControlKeys.Released); oos.writeInt(ControlKeys.ShootDown); oos.flush()
          case KeyCode.D => oos.writeInt(ControlKeys.Released); oos.writeInt(ControlKeys.ShootRight); oos.flush()
          case _ =>
        }
      }
      oos.flush()
    }
  }
}
