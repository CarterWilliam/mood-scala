package mood.scenes

import mood.{Assets, SceneLoader}
import org.phaser.scenes.Scene.{NoData, SceneKey}
import org.phaser.scenes._

class BootScene extends Scene(SceneConfig(BootScene.Key)) {
  override type Key = BootScene.Key.type
  override type Data = NoData

  private val loader = new SceneLoader(this)

  override def preload(): Unit = {
    loader.load(Assets.Textures.Splash)
    loader.load(Assets.Textures.LoadingBar)
    loader.load(Assets.Textures.MenuSkull)

    loader.load(Assets.Audio.Pistol)

    load.text("game-config", "assets/config/game.json")
  }

  override def create(): Unit = {
    scene.start[MenuScene](MenuScene.Key)
  }

}

object BootScene {
  case object Key extends SceneKey { val value = "boot" }
}
