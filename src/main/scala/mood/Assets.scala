package mood

import org.phaser.loader.LoaderPlugin.{AudioKey, TextureKey, Url}
import org.phaser.scenes.Scene

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

case class AudioAsset(key: AudioKey, url: Url)
case class TextureAsset(key: TextureKey, url: Url)
case class SpritesheetAsset(key: TextureKey, url: Url, width: Int, height: Int)

object Assets {

  object Audio {
    val Pistol = AudioAsset("pistol", "assets/audio/guns/pistol.ogg")
    val Shotgun = AudioAsset("shotgun", "assets/audio/guns/shotgun.ogg")

    val SoldierSight = AudioAsset("soldier-sight", "assets/audio/enemy/soldier/sight.ogg")
    val SoldierHurt = AudioAsset("soldier-hurt", "assets/audio/enemy/soldier/injured.ogg")
    val SoldierDie = AudioAsset("soldier-die", "assets/audio/enemy/soldier/death1.ogg")

    val ImpSight = AudioAsset("imp-sight", "assets/audio/enemy/imp/sight.ogg")
    val ImpHurt = AudioAsset("imp-hurt", "assets/audio/enemy/imp/pain.ogg")
    val ImpDie = AudioAsset("imp-die", "assets/audio/enemy/imp/death.ogg")

    val PlayerDie = AudioAsset("player-die", "assets/audio/player/death.ogg")
    val PlayerHurt = AudioAsset("player-hurt", "assets/audio/player/pain.ogg")

    val ItemPickup = AudioAsset("item-pickup", "assets/audio/player/item-pickup.ogg")
    val WeaponPickup = AudioAsset("weapon-pickup", "assets/audio/player/weapon-pickup.ogg")
  }

  object Textures {
    val Splash = TextureAsset("splash", "assets/images/splash.jpg")
    val LoadingBar = TextureAsset("loading-bar", "assets/images/loading.png")
    val MenuSkull = TextureAsset("menu-skull", "assets/images/menu-select-skull.png")

    val HUD = TextureAsset("hud", "assets/images/hud.png")

    val Clip = TextureAsset("clip", "assets/images/items/ammo/clip.png")
    val Shotgun = TextureAsset("shotgun", "assets/images/items/guns/shotgun.png")
    val Chaingun = TextureAsset("chaingun", "assets/images/items/guns/chaingun.png")

    val Bullet = TextureAsset("bullet", "assets/images/bullet.png")
  }

  object Spritesheets {
    //    val Player = TextureAsset("player", "assets/images/player.png", { frameWidth: 60, frameHeight: 60, endFrame: 63 })
    //    val Soldier = TextureAsset("soldier", "assets/images/soldier.png", { frameWidth: 60, frameHeight: 60, endFrame: 60 })
    //    val Imp = TextureAsset("imp", "assets/images/imp.png", { frameWidth: 60, frameHeight: 60, endFrame: 68 })
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
