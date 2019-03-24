package mood.sprites.components

import mood.animation.MoodAnimations.Animation.AnimationKey
import mood.sprites.components.Killable.KillableConfig
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.loader.LoaderPlugin.AssetKey

import scala.scalajs.js

class Killable(sprite: Sprite, config: KillableConfig) extends js.Object {

  var health: Int = config.maxHealth

  def takeDamage(hitPoints: Int): Unit = {
    health -= hitPoints
    if (health < 1) {
      die()
    } else {
      sprite.scene.sound.play(config.painAudioKey)
      sprite.body.stop()
      config.onHit(sprite)
    }
  }

  private def die(): Unit = {
    sprite.scene.sound.play(config.deathAudioKey)
    sprite.body.stop()
    sprite.anims.play(config.deathAnimationKey)
    sprite.body.destroy()
    config.onDeath(sprite)
  }

}

object Killable {

  case class KillableConfig(
    maxHealth: Int,
    painAudioKey: AssetKey,
    deathAudioKey: AssetKey,
    deathAnimationKey: AnimationKey,
    onHit: Sprite => Unit = _ => (): Unit,
    onDeath: Sprite => Unit = _ => (): Unit)
}
