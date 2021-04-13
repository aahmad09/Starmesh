package graphicgame

/**
 * bullets not always getting removed on collision with enemy
 * closing client window crashes server
 *
 */

import  java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.{ServerSocket, Socket}
import java.util.concurrent.LinkedBlockingQueue
import scala.concurrent.Future

case class ConnectedClient(player: Player, sock: Socket, ois: ObjectInputStream, oos: ObjectOutputStream)

object Server extends App {
  val maze: Maze = RandomMaze(6, wrap = false, 10, 10, 0.6)
  val thisLevel = new Level(maze, _entities = Nil)
  val startEntities = List(new Generator(9, 9, thisLevel, 0),
    new Generator(51, 51, thisLevel, 1),
    new Tower(3, 3, thisLevel, false, 0),
    new Tower(57, 57, thisLevel, false, 1))
  startEntities ::: thisLevel

  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  private val clientQueue = new LinkedBlockingQueue[ConnectedClient]()
  private var clients: List[ConnectedClient] = Nil

  val ss = new ServerSocket(8080)
  println(s"... Server is running using port ${ss.getLocalPort} ...")
  Future {
    while (true) {
      val sock = ss.accept()
      println("got player with IP: " + sock.getLocalAddress)
      val oos = new ObjectOutputStream(sock.getOutputStream)
      val ois = new ObjectInputStream(sock.getInputStream)
      val newPlayer = new Player(26, 26, thisLevel)
      clientQueue.put(ConnectedClient(newPlayer, sock, ois, oos))
    }
  }

  val sendInterval = 0.015
  private var sendDelay = 0.0
  private var lastTime = -1L
  while (true) {
    while (!clientQueue.isEmpty) {
      val cc = clientQueue.take()
      clients ::= cc
      thisLevel += cc.player
    }
    val time = System.nanoTime()
    if (lastTime >= 0) {
      val delay = (time - lastTime) / 1e9
      sendDelay += delay
      val sendLevels = sendDelay > sendInterval
      if (sendLevels) sendDelay = 0.0
      thisLevel.updateAll(delay)
      val plevel = thisLevel.makePassable()
      for (ConnectedClient(player, sock, ois, oos) <- clients) {
        val playerUpdateInfo = UpdateInfo(plevel, player.x, player.y)
        if (sendLevels) oos.writeObject(playerUpdateInfo)
        if (ois.available() > 0) {
          val pressRelease = ois.readInt()
          val key = ois.readInt()
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
      }
    }
    lastTime = time
  }
}

