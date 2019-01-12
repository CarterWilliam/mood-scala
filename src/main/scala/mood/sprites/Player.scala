package mood.sprites

import mood.Assets
import mood.animation.MoodAnimations.Animation
import mood.input.MoodInput
import mood.scenes.GameScene
import mood.sprites.Direction._
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.scenes.Scene
import Player.Animations._
import mood.sprites.Player.Action.{Firing, Normal}
import mood.sprites.Player.{Action, State}
import org.phaser.animations.AnimationConfig.RepeatConfig.{Forever, Never, Repeat}

class Player(scene: Scene, x: Int, y: Int)
  extends Sprite(scene, x, y, Assets.SpriteSheets.Player.key) {

  private val velocity: Int = 160
  private val diagonalVelocity: Double = Math.floor(velocity / Math.sqrt(2))

  private var state: State = State(Normal, South, health = 100)

  scene.physics.world.enable(this)
  scene.add.existing(this)

  body.setSize(30, 40)
  body.setOffset(15, 20)
  body.setCollideWorldBounds()

  setDepth(GameScene.Depth.Sprite)

  def update(input: MoodInput): Unit = state.action match {
    case Firing =>
      whileFiring()
    case Normal =>
      if (input.space.isDown) {
        fire()
      } else {
        move(input)
      }
  }

  private def fire(): Unit = {
    body.stop()

    val shootKey = s"player-shoot-${state.direction.toString.toLowerCase}"
    anims.play(shootKey, ignoreIfPlaying = true)
    switchState(Firing)
  }

  private def whileFiring(): Unit = {
    if (!anims.isPlaying) {
      switchState(Normal)
    }
  }

  private def move(input: MoodInput): Unit = {
    val newDirection = direction(input)
    newDirection.foreach(turn)
    newDirection match {
      case None =>
        body.setVelocity(0, 0)
        anims.stop()
      case Some(North) =>
        body.setVelocity(0, -velocity)
        anims.play(MoveNorth, ignoreIfPlaying = true)
      case Some(NorthEast) =>
        body.setVelocity(diagonalVelocity, -diagonalVelocity)
        anims.play(MoveNorthEast, ignoreIfPlaying = true)
      case Some(East) =>
        body.setVelocity(velocity, 0)
        anims.play(MoveEast, ignoreIfPlaying = true)
      case Some(SouthEast) =>
        body.setVelocity(diagonalVelocity, diagonalVelocity)
        anims.play(MoveSouthEast, ignoreIfPlaying = true)
      case Some(South) =>
        body.setVelocity(0, velocity)
        anims.play(MoveSouth, ignoreIfPlaying = true)
      case Some(SouthWest) =>
        body.setVelocity(-diagonalVelocity, diagonalVelocity)
        anims.play(MoveSouthWest, ignoreIfPlaying = true)
      case Some(West) =>
        body.setVelocity(-velocity, 0)
        anims.play(MoveWest, ignoreIfPlaying = true)
      case Some(NorthWest) =>
        body.setVelocity(-diagonalVelocity, -diagonalVelocity)
        anims.play(MoveNorthWest, ignoreIfPlaying = true)
    }
  }

  private def directionVector(input: MoodInput): (Int, Int) = {
    var movingX = 0
    var movingY = 0

    if (input.left.isDown) movingX -= 1
    if (input.right.isDown) movingX += 1
    if (input.up.isDown) movingY += 1
    if (input.down.isDown) movingY -= 1

    (movingX, movingY)
  }

  private def direction(input: MoodInput): Option[Direction.Direction] = {
    directionVector(input) match {
      case (0, 0) => None
      case (0, 1) => Some(North)
      case (1, 1) => Some(NorthEast)
      case (1, 0) => Some(East)
      case (1, -1) => Some(SouthEast)
      case (0, -1) => Some(South)
      case (-1, -1) => Some(SouthWest)
      case (-1, 0) => Some(West)
      case (-1, 1) => Some(NorthWest)
    }
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
    case object Dead extends Action
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
  sealed trait Direction
  case object North extends Direction
  case object NorthEast extends Direction
  case object East extends Direction
  case object SouthEast extends Direction
  case object South extends Direction
  case object SouthWest extends Direction
  case object West extends Direction
  case object NorthWest extends Direction
}