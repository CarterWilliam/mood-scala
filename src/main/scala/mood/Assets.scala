package mood

import mood.Assets.{AudioAsset, TextureAsset}
import org.phaser.loader.LoaderPlugin.{AudioKey, TextureKey, Url}
import org.phaser.scenes.Scene

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

object Assets {

  case class AudioAsset(key: AudioKey, url: Url)
  case class TextureAsset(key: TextureKey, url: Url)

  object Audio {
    val Pistol = AudioAsset("pistol", "assets/audio/guns/pistol.ogg")
  }

  object Textures {
    val Splash = TextureAsset("splash", "assets/images/splash.jpg")
    val LoadingBar = TextureAsset("loading-bar", "assets/images/loading.png")
    val MenuSkull = TextureAsset("menu-skull", "assets/images/menu-select-skull.png")
  }
}

@ScalaJSDefined
class SceneLoader(scene: Scene) extends js.Object {

  def load(audio: AudioAsset): Unit = {
    scene.load.audio(audio.key, audio.url)
  }

  def load(texture: TextureAsset): Unit = {
    scene.load.image(texture.key, texture.url)
  }
}
