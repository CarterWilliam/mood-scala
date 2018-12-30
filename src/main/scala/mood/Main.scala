package mood

import mood.scenes.Boot
import org.phaser.{Game, GameConfig}
import org.phaser.Phaser.RenderType

object Main {

  def main(args: Array[String]): Unit = {
    println("MOOD...")
    val bootScene = new Boot

    val config: GameConfig = GameConfig(
      renderType = RenderType.CANVAS,
      parent = Some("game-container"),
      scenes = Seq(bootScene))
    val game = new Game(config)
  }
}
