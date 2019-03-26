package mood.sprites.player

import mood.Assets
import mood.animation.MoodAnimations.{Animation, DirectedAnimations}
import mood.events.Events.{AmmoChanged, HealthChanged}
import mood.input.PlayerInput
import mood.scenes.GameScene
import mood.sprites.components.Killable
import mood.sprites.items.{AmmoItemConfig, ItemConfig}
import mood.sprites.player.Player.Action.{Dying, Firing, Normal}
import mood.sprites.player.Player.{Action, State}
import mood.sprites.player.guns.{AmmoBag, Pistol, Weapon}
import mood.sprites.projectiles.ProjectilesGroup
import mood.util.Coordinates
import mood.util.Direction._
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.loader.LoaderPlugin.AssetKey
import org.phaser.scenes.Scene

class Player(scene: Scene,
             origin: Coordinates,
             val config: Player.Config,
             projectiles: ProjectilesGroup,
             initialState: Option[State] = None)
  extends Sprite(scene, origin.x, origin.y, Assets.SpriteSheets.Player.key) {

  private val velocity: Int = config.speed
  private val diagonalVelocity: Double = Math.floor(config.speed / Math.sqrt(2))

  private var state: State = initialState.getOrElse(config.initialState)

  scene.physics.world.enable(this)
  scene.add.existing(this)

  body.setSize(config.size.width, config.size.height)
  body.setOffset(config.offset.x, config.offset.y)
  body.setCollideWorldBounds()

  setDepth(GameScene.Depth.Sprite)

  def update(input: PlayerInput): Unit = state.action match {
    case Normal =>
      if (input.isFiring) {
        fire()
      } else if (input.isMoving) {
        move(input)
      } else {
        stop()
      }
    case Firing =>
      whileFiring()
    case Dying =>
      // what can you do?
  }

  def pickup(item: ItemConfig): Unit = item match {
    case ammo: AmmoItemConfig =>
      scene.sound.play(ammo.pickupAudio)
      updateAmmo(state.ammo.add(ammo.ammoType, ammo.amount))
      val event = AmmoChanged(ammo.ammoType, state.ammo.remaining(ammo.ammoType))
      scene.events.emit(AmmoChanged.key, event)
  }

  private def fire(): Unit = {
    state.ammo.take(state.equipped.ammoType, state.equipped.ammoCost) match {
      case Some(updatedAmmoBag) =>
        updateAmmo(updatedAmmoBag)
        val event = AmmoChanged(state.equipped.ammoType, updatedAmmoBag.remaining(state.equipped.ammoType))
        scene.events.emit(AmmoChanged.key, event)
        body.stop()
        projectiles.add(Coordinates(x, y), state.direction)
        scene.sound.play("pistol")
        anims.play(config.animations.firing(state.direction), ignoreIfPlaying = true)
        switchState(Firing)
      case None =>
        // don't shoot...
    }
  }

  private def whileFiring(): Unit = {
    if (!anims.isPlaying) {
      switchState(Normal)
    }
  }

  private def move(input: PlayerInput): Unit = {
    if (!input.isStrafing) input.direction.foreach(turn)

    input.direction foreach {
      case North =>
        body.setVelocity(0, -velocity)
      case NorthEast =>
        body.setVelocity(diagonalVelocity, -diagonalVelocity)
      case East =>
        body.setVelocity(velocity, 0)
      case SouthEast =>
        body.setVelocity(diagonalVelocity, diagonalVelocity)
      case South =>
        body.setVelocity(0, velocity)
      case SouthWest =>
        body.setVelocity(-diagonalVelocity, diagonalVelocity)
      case West =>
        body.setVelocity(-velocity, 0)
      case NorthWest =>
        body.setVelocity(-diagonalVelocity, -diagonalVelocity)
    }

    anims.play(config.animations.movement(state.direction), ignoreIfPlaying = true)
  }

  private def stop(): Unit = {
    anims.stop()
    body.stop()
  }

  private def switchState(action: Action): Unit = {
    state = state.copy(action = action)
  }

  private def updateHealth(health: Int): Unit = {
    state = state.copy(health = health)
  }

  private def updateAmmo(ammo: AmmoBag): Unit = {
    state = state.copy(ammo = ammo)
  }

  private def turn(direction: CompassDirection): Unit = {
    state = state.copy(direction = direction)
  }
}

object Player {

  case class Config(
    size: Size,
    offset: Offset,
    maxHealth: Int,
    speed: Int,
    audio: Audio,
    animations: Animations,
    initialState: State)

  case class Size(width: Int, height: Int)
  case class Offset(x: Int, y: Int)

  case class Audio(
    hurt: AssetKey,
    die: AssetKey)

  case class Animations(
    movement: DirectedAnimations,
    firing: DirectedAnimations,
    die: Animation)

  case class State(
    action: Action = Normal,
    direction: CompassDirection,
    health: Int,
    equipped: Weapon = Pistol,
    ammo: AmmoBag = AmmoBag())

  sealed trait Action
  object Action {
    def apply(key: String): Option[Action] = key match {
      case "normal" => Some(Normal)
      case "firing" => Some(Firing)
      case "dying" => Some(Dying)
      case _ => None
    }
    case object Normal extends Action
    case object Firing extends Action
    case object Dying extends Action
  }

  implicit val playerKillable: Killable[Player] = new Killable[Player] {
    def painAudioKey(player: Player): AssetKey = player.config.audio.hurt
    def deathAudioKey(player: Player): AssetKey = player.config.audio.die
    def deathAnimationKey(player: Player): AssetKey = player.config.animations.die

    def currentHealth(player: Player): Int = player.state.health
    def onDamage(player: Player, health: Int): Unit = {
      player.updateHealth(health)
      player.scene.events.emit(HealthChanged.key, HealthChanged(health))
    }
    def onDeath(player: Player): Unit = {
      player.switchState(Dying)
      player.scene.events.emit(HealthChanged.key, HealthChanged(0))
    }
  }
}
