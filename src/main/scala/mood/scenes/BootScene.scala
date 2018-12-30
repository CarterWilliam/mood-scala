package mood.scenes

import org.phaser.scenes.{Scene, SceneConfig}

import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
class BootScene extends Scene(BootScene.Config) {

  override def preload(): Unit = {
    println(s"Boot.preload()")
    load.image("splash", "assets/images/splash.jpg")
    load.image("loading-bar", "assets/images/loading.png")
    load.image("menu-skull", "assets/images/menu-select-skull.png")

    load.audio("pistol", "assets/audio/guns/pistol.ogg")
  }

  override def create(): Unit = {
    println("Boot.create()")
    scene.start(LoadScene.Config.key)
  }

  override def update(time: Double, delta: Double) = {

  }

}

object BootScene {
  val Config = SceneConfig("boot")
}
