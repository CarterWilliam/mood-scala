package mood.scenes

import org.phaser.{Scene, SceneConfig}

import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
class Boot extends Scene(Boot.Config) {

  override def preload(): Unit = {
    println(s"Boot.preload()")
  }

  override def create(): Unit = {
    println("Boot.create()")
  }

  override def update(time: Double, delta: Double) = {
    println(s"Boot.update($time)")
  }

}

object Boot {
  val Config = SceneConfig("boot")
}
