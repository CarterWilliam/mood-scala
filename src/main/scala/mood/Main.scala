package mood

import mood.scenes.{BootScene, LoadScene, MenuScene}
import org.phaser.{Game, GameConfig}
import org.phaser.Phaser.RenderType

object Main {

  def main(args: Array[String]): Unit = {
    println("MOOD...")

    val config: GameConfig = GameConfig(
      renderType = RenderType.CANVAS,
      parent = Some("game-container"),
      scenes = Seq(new BootScene, new MenuScene, new LoadScene))
    val game = new Game(config)
  }
}
