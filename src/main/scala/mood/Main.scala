package mood

import org.phaser.{Config, Game}
import org.phaser.Phaser.RenderType
import org.scalajs.dom._
import org.scalajs.dom.html.Canvas
import scala.scalajs.js.JSON
import scala.scalajs.js

object Main {

  def main(args: Array[String]): Unit = {
    println("MOOD...")
    val config: Config = Config(
      width = 800,
      height = 600,
      renderType = RenderType.CANVAS,
      parent = Some("game-container"))
    val game = new Game(config)
  }
}
