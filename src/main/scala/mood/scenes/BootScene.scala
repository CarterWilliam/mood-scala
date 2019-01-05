package mood.scenes

import mood.scenes.BootScene.BootId
import mood.scenes.MenuScene.MenuId
import mood.{Assets, SceneLoader}
import org.phaser.scenes.Scene.{NoData, SceneKey}
import org.phaser.scenes._

class BootScene extends Scene(BootScene.Config) {
  override type Key = BootId.type
  override type Data = NoData

  private val loader = new SceneLoader(this)

  override def preload(): Unit = {
    loader.load(Assets.Textures.Splash)
    loader.load(Assets.Textures.LoadingBar)
    loader.load(Assets.Textures.MenuSkull)

    loader.load(Assets.Audio.Pistol)
  }

  override def create(): Unit = {
    scene.start[MenuScene](MenuId)
  }

}

object BootScene {
  val Config = SceneConfig("boot")
  case object BootId extends SceneKey { val value = "boot" }
}
