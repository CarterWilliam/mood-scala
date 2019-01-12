package mood.scenes

import mood.scenes.Hud.Depth
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.scenes.{Scene, SceneConfig}
import org.phaser.scenes.Scene.SceneKey

class Hud extends Scene(Hud.Config) {

  override def create(): Unit = {
    val background: Sprite = add.sprite(0, 520, "hud")
    background.setOrigin(0, 0)
    background.setDepth(Depth.Background)


  }

  def startWatching(scene: GameScene): Unit = {

  }

}

object Hud {
  case object Key extends SceneKey { val value = "key" }
  val Config = SceneConfig(Key)

  object Depth {
    val Background: Int = 0
    val Display: Int = 1
  }
}
