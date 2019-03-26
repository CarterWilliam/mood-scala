package mood.sprites.projectiles

import mood.animation.MoodAnimations.Animation.AnimationKey
import mood.sprites.projectiles.Projectile.ProjectileConfig
import mood.util.Position.Coordinates
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.loader.LoaderPlugin.AssetKey
import org.phaser.scenes.Scene

class Projectile(scene: Scene,
                 group: ProjectilesGroup,
                 val config: ProjectileConfig
) extends Sprite(scene, config.origin.x, config.origin.y, config.texture) {

  scene.physics.world.enable(this)

  body.setCollideWorldBounds(false)
  body.setVelocityX(Math.cos(config.direction) * config.speed)
  body.setVelocityY(Math.sin(config.direction) * config.speed)

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
  case class ProjectileConfig(
    texture: AssetKey,
    damage: Int,
    origin: Coordinates,
    direction: Double,
    speed: Int,
    explodes: Option[AnimationKey] = None)
}
