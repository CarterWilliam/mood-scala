package mood.sprites.player

import mood.Assets
import mood.animation.MoodAnimations.{Animation, DirectedAnimations}
import mood.config.GameConfig
import mood.events.Events.{AmmoChanged, HealthChanged, WeaponChanged, WeaponPickup}
import mood.input.PlayerInput
import mood.input.PlayerInput.{WeaponSwitchPistol, WeaponSwitchShotgun}
import mood.scenes.GameScene
import mood.sprites.components.Killable
import mood.sprites.items.{AmmoItemConfig, ItemConfig, WeaponItemConfig}
import mood.sprites.player.Player.Action.{Dying, Firing, Normal}
import mood.sprites.player.Player.{Action, State}
import mood.sprites.player.guns._
import mood.sprites.projectiles.ProjectilesGroup
import mood.spacial.Direction._
import mood.spacial.ExplicitDirection
import mood.spacial.Position.{Coordinates, Offset, Size}
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.loader.LoaderPlugin.AssetKey
import org.phaser.math.RND
import org.phaser.scenes.Scene

class Player(scene: Scene,
             origin: Coordinates,
             gameConfig: GameConfig,
             projectiles: ProjectilesGroup,
             initialState: Option[State] = None)
  extends Sprite(scene, origin.x, origin.y, Assets.SpriteSheets.Player.key) {

  private val config = gameConfig.player
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
    case Normal => whileNormal(input)
    case Firing => whileFiring()
    case Dying => // what can you do?
  }

  def pickup(item: ItemConfig): Unit = {
    scene.sound.play(item.pickupAudio)
    item match {
      case ammo: AmmoItemConfig => pickupAmmo(ammo)
      case weapon: WeaponItemConfig => pickupWeapon(weapon)
    }
  }

  private def pickupAmmo(ammo: AmmoItemConfig): Unit = {
    updateAmmo(state.ammo.add(ammo.ammoType, ammo.amount))
    val event = AmmoChanged(ammo.ammoType, state.ammo.remaining(ammo.ammoType))
    scene.events.emit(AmmoChanged.key, event)
  }

  private def pickupWeapon(weapon: WeaponItemConfig): Unit = {
    state = state.copy(weapons = state.weapons + weapon.weapon)
    updateAmmo(state.ammo.add(weapon.ammoType, weapon.ammoAmount))
    val event = WeaponPickup(weapon.weapon, weapon.ammoType, state.ammo.remaining(weapon.ammoType))
    scene.events.emit(WeaponPickup.key, event)
  }

  private def whileNormal(input: PlayerInput): Unit = {
    input.weaponSwitch.foreach {
      case WeaponSwitchPistol => equip(Pistol)
      case WeaponSwitchShotgun => equip(Shotgun)
    }

    if (input.isFiring) {
      fire()
    } else if (input.isMoving) {
      move(input)
    } else {
      stop()
    }
  }

  private def fire(): Unit = {
    state.ammo.take(currentWeapon.ammoType, currentWeapon.ammoCost) match {
      case Some(updatedAmmoBag) =>
        updateAmmo(updatedAmmoBag)
        val event = AmmoChanged(currentWeapon.ammoType, updatedAmmoBag.remaining(currentWeapon.ammoType))
        scene.events.emit(AmmoChanged.key, event)
        body.stop()
        scene.sound.play(currentWeapon.fireAudio)
        anims.play(config.animations.firing(state.direction), ignoreIfPlaying = true)
        switchState(Firing)
        (1 to currentWeapon.burst).foreach { i =>
          val direction = RND.realInRange(state.direction.radians - currentWeapon.maxMissRadians, state.direction.radians + currentWeapon.maxMissRadians)
          projectiles.add(currentWeapon.projectile, Coordinates(x, y), ExplicitDirection(direction))
        }
      case None =>
        // don't shoot...
    }
  }

  private def currentWeapon: WeaponConfig = {
    gameConfig.weapons(state.equipped)
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

  private def equip(weapon: WeaponKey): Unit = {
    if (state.weapons.contains(weapon)) {
      state = state.copy(equipped = weapon)
      val event = WeaponChanged(
        ammoType = currentWeapon.ammoType,
        remaining = state.ammo.remaining(currentWeapon.ammoType))
      scene.events.emit(WeaponChanged.key, event)
    }
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
    equipped: WeaponKey,
    weapons: Set[WeaponKey] = Set(Pistol),
    ammo: AmmoBag)

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
