package mood.scenes

import mood.{Assets, SceneLoader}
import org.phaser.scenes.{Scene, SceneConfig}

import scala.scalajs.js.annotation.ScalaJSDefined

class BootScene extends Scene(BootScene.Config) {
  private val loader = new SceneLoader(this)

  override def preload(): Unit = {
    println(s"Boot.preload()")
    loader.load(Assets.Textures.Splash)
    loader.load(Assets.Textures.LoadingBar)
    loader.load(Assets.Textures.MenuSkull)

    loader.load(Assets.Audio.Pistol)
  }

  override def create(): Unit = {
    println("Boot.create()")
    scene.start(MenuScene.Config.key)
  }

  override def update(time: Double, delta: Double) = {

  }

}

object BootScene {
  val Config = SceneConfig("boot")
}
