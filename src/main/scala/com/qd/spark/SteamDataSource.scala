package com.qd.spark

import java.io.PrintWriter
import java.net.ServerSocket

/**
 * 模拟随记发送A-G 7个字母中的一个，时间间隔可以指定
 */
object SteamDataSource {

  def generateContent(index: Int): String = {
    import scala.collection.mutable.ListBuffer
    val charList = ListBuffer[Char]()
    for (i <- 65 to 90) {
      charList += i.toChar
    }
    val charArray = charList.toArray
    charArray(index).toString
  }

  def index = {
    import java.util.Random
    val rdm = new Random
    //rdm.nextInt(26)
    rdm.nextInt(7)
  }

  def main(args: Array[String]) {
    /*if (args.length != 2) {
      System.err.println("Usage: <port> <millisecond>")
      System.exit(1)
    }*/

    val listener = new ServerSocket(9999)
    while (true) {
      val socket = listener.accept()
      new Thread() {
        override def run = {
          println("Got client connected from: " + socket.getInetAddress)
          val out = new PrintWriter(socket.getOutputStream(), true)
          while (true) {
            Thread.sleep(1000)
            val content = generateContent(index)
            println(content)
            out.write(content + '\n')
            out.flush()
          }
          socket.close()
        }
      }.start()
    }
  }
}