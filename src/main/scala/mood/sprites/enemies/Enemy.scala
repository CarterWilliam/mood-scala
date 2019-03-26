package mood.sprites.enemies

import mood.animation.MoodAnimations.{Animation, DirectedAnimations}
import mood.error.GameError
import mood.sprites.components.Killable
import mood.sprites.enemies.Enemy._
import mood.sprites.items.{ItemKey, ItemsGroup}
import mood.sprites.player.Player
import mood.sprites.projectiles.{Projectile, ProjectilesGroup}
import mood.spacial.Direction._
import mood.spacial.Position.{Coordinates, Offset}
import mood.spacial.ExplicitDirection
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.gameobjects.sprite.Sprite._
import org.phaser.loader.LoaderPlugin.AssetKey
import org.phaser.math.{Angle, Distance}
import org.phaser.scenes.Scene

class Enemy(scene: Scene,
            origin: Coordinates,
            val config: Enemy.Config,
            projectiles: ProjectilesGroup,
            val items: ItemsGroup) extends Sprite(scene, origin.x, origin.y, config.spritesheet) {

  private var state: State = Passive
  private var health: Int = config.health
  private var lastShot: Double = 0

  scene.physics.world.enable(this)

  anims.play(config.animations.passive.key)

  def update(player: Player, time: Double): Unit = state match {
    case Passive => passiveUpdate(player)
    case Aggressive => aggressiveUpdate(player, time)
    case Shooting => // do nothing
    case Dead => // do nothing
  }

  private def passiveUpdate(player: Player): Unit = {
    if (Distance.Between(x, y, player.x, player.y) < config.sightRadius) {
      scene.sound.play(config.audio.sight)
      state = Aggressive
    }
  }

  private def aggressiveUpdate(player: Player, time: Double): Unit = {
    if (canShoot(time)) {
      shootAtPlayer(player, time)
    } else {
      moveToPlayer(player)
    }
  }

  private def canShoot(now: Double): Boolean = {
    lastShot < now - config.firingInterval
  }

  private def shootAtPlayer(player: Player, time: Double): Unit = {
    state = Shooting
    body.setVelocity(0, 0)
    val angleToPlayer = Angle.Between(x, y, player.x, player.y)
    val closestDirection = directionForAngle(angleToPlayer)

    anims.play(config.animations.firing(closestDirection))

    this.onAnimationComplete {
      projectiles.add(config.projectile, Coordinates(x, y), ExplicitDirection(angleToPlayer))
      scene.sound.play(config.audio.fire)
      lastShot = time
      state = Aggressive
    }
  }

  private def moveToPlayer(player: Player): Unit = {
    val angleToPlayer = Angle.Between(x, y, player.x, player.y)
    val closestDirection = directionForAngle(angleToPlayer)

    body.setVelocity(Math.cos(closestDirection.radians) * config.speed, Math.sin(closestDirection.radians) * config.speed)

    anims.play(config.animations.movement(closestDirection), ignoreIfPlaying = true)
  }

  private def directionForAngle(angle: Double): CompassDirection = {
    Seq(East, SouthEast, South, SouthWest, West, NorthWest, North, NorthEast).find { direction =>
      Math.abs(angle - direction.radians) <= PiOver8 || Math.abs(angle - direction.radians + TwoPi) <= PiOver8
    }.getOrElse {
      GameError.fatal(scene.game, GameError(s"Could not determine compass direction for angle '$angle'"))
    }
  }

}

object Enemy {

  case class Config(
    firingInterval: Int,
    health: Int,
    itemDrop: Option[ItemDrop] = None,
    sightRadius: Int,
    speed: Int,
    projectile: Projectile.Config,
    spritesheet: AssetKey,
    audio: Audio,
    animations: Animations)

  case class ItemDrop(item: ItemKey, offset: Offset)

  case class Audio(sight: AssetKey, hurt: AssetKey, die: AssetKey, fire: AssetKey)
  case class Animations(
    passive: Animation,
    movement: DirectedAnimations,
    firing: DirectedAnimations,
    hit: DirectedAnimations,
    die: Animation)

  sealed trait State
  case object Passive extends State
  case object Aggressive extends State
  case object Shooting extends State
  case object Dead extends State // TODO: replace sprite with image and remove from game

  implicit val playerKillable: Killable[Enemy] = new Killable[Enemy] {
    def painAudioKey(enemy: Enemy): AssetKey = enemy.config.audio.hurt
    def deathAudioKey(enemy: Enemy): AssetKey = enemy.config.audio.die
    def deathAnimationKey(enemy: Enemy): AssetKey = enemy.config.animations.die

    def currentHealth(enemy: Enemy): Int = enemy.health
    def onDamage(enemy: Enemy, health: Int): Unit = {
      enemy.health = health
      enemy.state = Aggressive
    }
    def onDeath(enemy: Enemy): Unit = {
      enemy.state = Dead
      enemy.config.itemDrop.foreach { drop =>
        enemy.onAnimationComplete {
          enemy.items.add(drop.item, Coordinates(enemy.x, enemy.y) + drop.offset)
        }
      }
    }
  }

}