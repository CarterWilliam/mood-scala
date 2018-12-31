package mood

import com.google.webfontloader.WebFont
import mood.scenes.{BootScene, LoadScene, MenuScene}
import org.phaser.{Game, GameConfig}
import org.phaser.Phaser.RenderType

import scala.scalajs.js

object Main {

  def main(args: Array[String]): Unit = {
    println("MOOD...")

    loadFonts { () =>
      startGame()
    }
  }

  def loadFonts(andThen: () => Unit): Unit = {
    val config = js.Dynamic.literal(
      custom = js.Dynamic.literal(
        families = js.Array("Doom"),
        urls = js.Array("css/doom-font.css")
      ),
      active = andThen
    )

    WebFont.load(config)
  }

  def startGame(): Unit = {
    val config: GameConfig = GameConfig(
      renderType = RenderType.CANVAS,
      parent = Some("game-container"),
      scenes = Seq(new BootScene, new MenuScene, new LoadScene))
    new Game(config)
  }
}
