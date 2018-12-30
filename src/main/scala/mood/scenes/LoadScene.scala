package mood.scenes

import org.phaser.scenes.{Scene, SceneConfig}

import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
class LoadScene extends Scene(LoadScene.Config) {

  override def preload(): Unit = {
    println(s"Load.preload()")
    val splash = add.sprite(0, 0, "splash")
    splash.setOrigin(0, 0)
  }

  override def create(): Unit = {
    println("Load.create()")
  }

}

object LoadScene {
  val Config = SceneConfig("load")
}