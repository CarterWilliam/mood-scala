package mood.sprites.enemies

import mood.animation.MoodAnimations.{Animation, DirectedAnimations}
import mood.sprites.Player
import mood.sprites.components.Killable
import mood.sprites.components.Killable.KillableConfig
import mood.sprites.enemies.Enemy._
import mood.sprites.projectiles.ProjectilesGroup
import mood.util.Coordinates
import mood.util.Direction._
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.loader.LoaderPlugin.AssetKey
import org.phaser.math.{Angle, Distance}
import org.phaser.scenes.Scene

import scala.scalajs.js.JavaScriptException

class Enemy(scene: Scene,
            origin: Coordinates,
            config: Enemy.Config,
            projectiles: ProjectilesGroup) extends Sprite(scene, origin.x, origin.y, config.spritesheet) {

  private var state: State = Passive
  private var lastShot: Double = 0

  scene.physics.world.enable(this)

  anims.play(config.animations.passive.key)

  val killable: Killable = new Killable(this, KillableConfig(
    maxHealth = 11,
    painAudioKey = config.audio.hurt,
    deathAudioKey = config.audio.die,
    deathAnimationKey = config.animations.die.key,
    onHit = { _ => state = Aggressive },
    onDeath = { _ => state = Dead }
  ))

  def update(player: Player, time: Double): Unit = state match {
    case Passive => passiveUpdate(player)
    case Aggressive => aggressiveUpdate(player, time)
    case Dead => (): Unit
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
    scene.sound.play(config.audio.fire)
    lastShot = time
  }

  private def moveToPlayer(player: Player): Unit = {
    val angleToPlayer = Angle.Between(x, y, player.x, player.y)
    val closestDirection = directionForAngle(angleToPlayer)

    body.setVelocity(Math.cos(closestDirection.radians) * config.speed, Math.sin(closestDirection.radians) * config.speed)

    anims.play(config.animations.movement(closestDirection), ignoreIfPlaying = true)
  }

  private def directionForAngle(angle: Double): Direction = {
    Seq(East,
      SouthEast,
      South,
      SouthWest,
      West,
      NorthWest,
      North,
      NorthEast
    ).find { direction =>
      Math.abs(angle - direction.radians) <= PiOver8 ||
        Math.abs(angle - direction.radians + TwoPi) <= PiOver8
    }.getOrElse {
      throw JavaScriptException("angle out of bounds???")
    }
  }

}

object Enemy {

  case class Config(
    spritesheet: AssetKey,
    health: Int,
    sightRadius: Int,
    speed: Int,
    firingInterval: Int,
    audio: Audio,
    animations: Animations)

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
  case object Dead extends State // TODO: replace sprite with image and remove from game

}