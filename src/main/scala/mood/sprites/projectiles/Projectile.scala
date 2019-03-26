package mood.sprites.projectiles

import mood.animation.MoodAnimations.Animation.AnimationKey
import mood.spacial.Direction
import mood.spacial.Position.Coordinates
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.loader.LoaderPlugin.AssetKey
import org.phaser.scenes.Scene

class Projectile(scene: Scene,
                 group: ProjectilesGroup,
                 origin: Coordinates,
                 direction: Direction,
                 val config: Projectile.Config
) extends Sprite(scene, origin.x, origin.y, config.texture) {

  scene.physics.world.enable(this)

  body.setCollideWorldBounds(false)
  body.setVelocityX(Math.cos(direction.radians) * config.speed)
  body.setVelocityY(Math.sin(direction.radians) * config.speed)

  def impact(): Unit = config.explodes match {
    case Some(explosionAnimationKey) =>
      body.stop()
      group.remove(this)
      anims.play(explosionAnimationKey)
      body.destroy()
      this.onAnimationComplete { this.destroy() }
    case None =>
      destroy()
  }
}

object Projectile {
  case class Config(
    texture: AssetKey,
    damage: Int,
    speed: Int,
    explodes: Option[AnimationKey] = None)
}
