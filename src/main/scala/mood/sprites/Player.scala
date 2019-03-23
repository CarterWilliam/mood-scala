package mood.sprites

import mood.Assets
import mood.animation.MoodAnimations.Animation
import mood.scenes.GameScene
import mood.sprites.Direction._
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.scenes.Scene
import Player.Animations._
import mood.input.PlayerInput
import mood.sprites.Player.Action.{Firing, Normal}
import mood.sprites.Player._
import mood.sprites.projectiles.ProjectilesGroup
import mood.util.Coordinates
import org.phaser.animations.AnimationConfig.RepeatConfig.{Forever, Never, Repeat}

class Player(scene: Scene, origin: Coordinates, projectiles: ProjectilesGroup)
  extends Sprite(scene, origin.x, origin.y, Assets.SpriteSheets.Player.key) {

  private val velocity: Int = 160
  private val diagonalVelocity: Double = Math.floor(velocity / Math.sqrt(2))

  private var state: State = State(Normal, South, health = 100)

  scene.physics.world.enable(this)
  scene.add.existing(this)

  body.setSize(30, 40)
  body.setOffset(15, 20)
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
    projectiles.add(Coordinates(x, y), state.direction.radians)
    scene.sound.play("pistol")

    val shootKey = s"player-shoot-${state.direction.toString.toLowerCase}"
    anims.play(shootKey, ignoreIfPlaying = true)
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

    state.direction match {
      case North =>
        anims.play(MoveNorth, ignoreIfPlaying = true)
      case NorthEast =>
        anims.play(MoveNorthEast, ignoreIfPlaying = true)
      case East =>
        anims.play(MoveEast, ignoreIfPlaying = true)
      case SouthEast =>
        anims.play(MoveSouthEast, ignoreIfPlaying = true)
      case South =>
        anims.play(MoveSouth, ignoreIfPlaying = true)
      case SouthWest =>
        anims.play(MoveSouthWest, ignoreIfPlaying = true)
      case West =>
        anims.play(MoveWest, ignoreIfPlaying = true)
      case NorthWest =>
        anims.play(MoveNorthWest, ignoreIfPlaying = true)
    }
  }

  private def stop(): Unit = {
    anims.stop()
    body.stop()
  }

  private def switchState(action: Action): Unit = {
    state = state.copy(action = action)
  }

  private def turn(direction: Direction): Unit = {
    state = state.copy(direction = direction)
  }
}

object Player {

  case class State(action: Action, direction: Direction, health: Int)

  sealed trait Action
  object Action {
    case object Normal extends Action
    case object Firing extends Action
  }

  object Animations {
    val MoveNorth = Animation("player-move-north", 16 to 19, Forever)
    val MoveNorthEast = Animation("player-move-northeast", 20 to 23, Forever)
    val MoveEast = Animation("player-move-east", 24 to 27, Forever)
    val MoveSouthEast = Animation("player-move-southeast", 28 to 31, Forever)
    val MoveSouth = Animation("player-move-south", 0 to 3, Forever)
    val MoveSouthWest = Animation("player-move-southwest", 4 to 7, Forever)
    val MoveWest = Animation("player-move-west", 8 to 11, Forever)
    val MoveNorthWest = Animation("player-move-northwest", 12 to 15, Forever)

    val ShootNorth = Animation("player-shoot-north", Seq(41, 40), Never)
    val ShootNorthEast = Animation("player-shoot-northeast", Seq(43, 42), Never)
    val ShootEast = Animation("player-shoot-east", Seq(45, 44), Never)
    val ShootSouthEast = Animation("player-shoot-southeast", Seq(47, 46), Never)
    val ShootSouth = Animation("player-shoot-south", Seq(33, 32), Never)
    val ShootSouthWest = Animation("player-shoot-southwest", Seq(35, 34), Never)
    val ShootWest = Animation("player-shoot-west", Seq(37, 36), Never)
    val ShootNorthWest = Animation("player-shoot-northwest", Seq(39, 38), Never)

    val all: Set[Animation] =
      Set(MoveNorth, MoveNorthEast, MoveEast, MoveSouthEast, MoveSouth, MoveSouthWest, MoveWest, MoveNorthWest, ShootNorth, ShootNorthEast, ShootEast, ShootSouthEast, ShootSouth, ShootSouthWest, ShootWest, ShootNorthWest)
  }
}

object Direction {
  sealed trait Direction { def radians: Double }
  case object North extends Direction { val radians: Double = -Math.PI / 2 }
  case object NorthEast extends Direction { val radians: Double = -Math.PI / 4 }
  case object East extends Direction { val radians: Double = 0 }
  case object SouthEast extends Direction { val radians: Double = Math.PI / 4 }
  case object South extends Direction { val radians: Double = Math.PI / 2 }
  case object SouthWest extends Direction { val radians: Double = 3*Math.PI / 4 }
  case object West extends Direction { val radians: Double = Math.PI }
  case object NorthWest extends Direction { val radians: Double = -3*Math.PI / 4 }
}