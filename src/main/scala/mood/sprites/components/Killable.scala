package mood.sprites.components

import org.phaser.gameobjects.sprite.Sprite
import org.phaser.loader.LoaderPlugin.AssetKey

trait Killable[K <: Sprite] {
  def painAudioKey(killable: K): AssetKey
  def deathAudioKey(killable: K): AssetKey
  def deathAnimationKey(killable: K): AssetKey

  def currentHealth(killable: K): Int
  def onDamage(killable: K, health: Int): Unit
  def onDeath(killable: K): Unit

  final def takeDamage(killable: K, hitPoints: Int): Unit = {
    val newHealth = currentHealth(killable) - hitPoints
    if (newHealth < 1) {
      die(killable)
    } else {
      killable.scene.sound.play(painAudioKey(killable))
      killable.body.stop()
      onDamage(killable, newHealth)
    }
  }

  private def die(killable: K): Unit = {
    killable.scene.sound.play(deathAudioKey(killable))
    killable.body.stop()
    killable.anims.play(deathAnimationKey(killable))
    killable.body.destroy()
    onDeath(killable)
  }

}
