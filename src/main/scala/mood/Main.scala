package mood

import com.google.webfontloader.WebFont
import mood.scenes.{BootScene, Hud, LoadScene, MenuScene}
import org.phaser.{ArcadePhysicsConfig, Game, GameConfig, PhysicsConfig}
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
        families = js.Array("Doom", "Square"),
        urls = js.Array("css/doom-font.css", "css/square-font.css")
      ),
      active = andThen
    )

    WebFont.load(config)
  }

  def startGame(): Unit = {
    val config: GameConfig = GameConfig(
      renderType = RenderType.CANVAS,
      parent = Some("game-container"),
      physics = Some(PhysicsConfig(
        default = "arcade",
        arcade = Some(ArcadePhysicsConfig(gravity = 0)))),
      scenes = Seq(new BootScene, new MenuScene, new LoadScene, new Hud))
    new Game(config)
  }
}
