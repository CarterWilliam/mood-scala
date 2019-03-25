package mood.sprites

import mood.Assets
import mood.animation.MoodAnimations.{Animation, DirectedAnimations}
import mood.input.PlayerInput
import mood.scenes.GameScene
import mood.sprites.Player.Action.{Firing, Normal}
import mood.sprites.Player._
import mood.sprites.projectiles.ProjectilesGroup
import mood.util.Coordinates
import mood.util.Direction._
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.scenes.Scene

class Player(scene: Scene, origin: Coordinates, config: Player.Config, projectiles: ProjectilesGroup)
  extends Sprite(scene, origin.x, origin.y, Assets.SpriteSheets.Player.key) {

  private val velocity: Int = config.speed
  private val diagonalVelocity: Double = Math.floor(config.speed / Math.sqrt(2))

  private var state: State = State(Normal, South, health = 100)

  scene.physics.world.enable(this)
  scene.add.existing(this)

  body.setSize(config.size.width, config.size.height)
  body.setOffset(config.offset.x, config.offset.y)
  body.setCollideWorldBounds()

  setDepth(GameScene.Depth.Sprite)

  def update(input: PlayerInput): Unit = state.action match {
    case Firing =>
      whileFiring()
    case Normal =>
      if (input.isFiring) {
        fire()
      } else if (input.isMoving) {
        move(input)
      } else {
        stop()
      }
  }

  private def fire(): Unit = {
    body.stop()
    projectiles.add(Coordinates(x, y), state.direction)
    scene.sound.play("pistol")
    anims.play(config.animations.firing(state.direction), ignoreIfPlaying = true)
    switchState(Firing)
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
    animations: Animations)

  case class Size(width: Int, height: Int)
  case class Offset(x: Int, y: Int)

  case class Animations(
    movement: DirectedAnimations,
    firing: DirectedAnimations,
    die: Animation)

  case class State(action: Action, direction: CompassDirection, health: Int)

  sealed trait Action
  object Action {
    case object Normal extends Action
    case object Firing extends Action
  }

}
