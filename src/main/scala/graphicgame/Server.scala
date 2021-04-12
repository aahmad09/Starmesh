package graphicgame

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.{ServerSocket, Socket}
import java.util.concurrent.LinkedBlockingQueue
import scala.concurrent.Future

case class ConnectedClient(player: Player, sock: Socket, ois: ObjectInputStream, oos: ObjectOutputStream, level: Level)

object Server extends App {
  val maze: Maze = RandomMaze(6, wrap = false, 10, 10, 0.6)
  val currentLevel = new Level(maze, _entities = Nil)
  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global
  val ss = new ServerSocket(8080)
  val sendInterval = 0.03
  private val clientQueue = new LinkedBlockingQueue[ConnectedClient]()
  println(s"... Server is running using port ${ss.getLocalPort} ...")

  Future {
    while (true) {
      val sock = ss.accept()
      println("got player with IP: " + sock.getLocalAddress)
      val oos = new ObjectOutputStream(sock.getOutputStream)
      val ois = new ObjectInputStream(sock.getInputStream)
      val newPlayer = new Player(2, 2, currentLevel)
      clientQueue.put(ConnectedClient(newPlayer, sock, ois, oos, currentLevel))
    }
  }
  private var clients: List[ConnectedClient] = Nil
  private var sendDelay = 0.0
  private var lastTime = -1L
  while (true) {
    while (!clientQueue.isEmpty) {
      val cc = clientQueue.take()
      clients ::= cc
    }
    val time = System.nanoTime()
    if (lastTime >= 0) {
      val delay = (time - lastTime) / 1e9
      sendDelay += delay
      val sendLevels = sendDelay > sendInterval
      if (sendLevels) sendDelay = 0.0
      for (ConnectedClient(player, sock, ois, oos, level) <- clients) {
        val playerUpdateInfo = UpdateInfo(level.makePassable(), player.x, player.y)
        if (ois.available() > 0) {
          val pressRelease = ois.readInt()
          val key = ois.readInt()
          println(pressRelease, key)
          if (pressRelease == ControlKeys.Pressed) {
            key match {
              case ControlKeys.MoveLeft => player.leftPressed()
              case ControlKeys.MoveRight => player.rightPressed()
              case ControlKeys.MoveUp => player.upPressed()
              case ControlKeys.MoveDown => player.downPressed()
              case ControlKeys.ShootLeft => player.fireLeftPressed()
              case ControlKeys.ShootRight => player.fireRightPressed()
              case ControlKeys.ShootUp => player.fireUpPressed()
              case ControlKeys.ShootDown => player.fireDownPressed()
              case _ =>
            }
          } else {
            key match {
              case ControlKeys.MoveLeft => player.leftReleased()
              case ControlKeys.MoveRight => player.rightReleased()
              case ControlKeys.MoveUp => player.upReleased()
              case ControlKeys.MoveDown => player.downReleased()
              case ControlKeys.ShootLeft => player.fireLeftReleased()
              case ControlKeys.ShootRight => player.fireRightReleased()
              case ControlKeys.ShootUp => player.fireUpReleased()
              case ControlKeys.ShootDown => player.fireDownReleased()
              case _ =>
            }
          }
        }
        level.updateAll(delay)
        if (sendLevels) oos.writeObject(playerUpdateInfo)
      }
    }
    lastTime = time
  }
}

