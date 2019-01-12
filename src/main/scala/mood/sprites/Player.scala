package mood.sprites

import mood.Assets
import mood.animation.MoodAnimations.Animation
import mood.input.MoodInput
import mood.scenes.GameScene
import mood.sprites.Direction._
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.scenes.Scene

import Player.Animations._

class Player(scene: Scene, x: Int, y: Int)
  extends Sprite(scene, x, y, Assets.SpriteSheets.Player.key) {

  private val velocity: Int = 160
  private val diagonalVelocity: Double = Math.floor(velocity / Math.sqrt(2))

  var health: Int = 100

  scene.physics.world.enable(this)
  scene.add.existing(this)

  body.setSize(30, 40)
  body.setOffset(15, 20)
  body.setCollideWorldBounds()

  setDepth(GameScene.Depth.Sprite)

  def update(input: MoodInput): Unit = {
    move(input)
  }

  private def move(input: MoodInput): Unit = {
    direction(input) match {
      case None =>
        body.setVelocity(0, 0)
        anims.stop()
      case Some(North) =>
        body.setVelocity(0, -velocity)
        anims.play(MoveNorth.key, ignoreIfPlaying = true)
      case Some(NorthEast) =>
        body.setVelocity(diagonalVelocity, -diagonalVelocity)
        anims.play(MoveNorthEast.key, ignoreIfPlaying = true)
      case Some(East) =>
        body.setVelocity(velocity, 0)
        anims.play(MoveEast.key, ignoreIfPlaying = true)
      case Some(SouthEast) =>
        body.setVelocity(diagonalVelocity, diagonalVelocity)
        anims.play(MoveSouthEast.key, ignoreIfPlaying = true)
      case Some(South) =>
        body.setVelocity(0, velocity)
        anims.play(MoveSouth.key, ignoreIfPlaying = true)
      case Some(SouthWest) =>
        body.setVelocity(-diagonalVelocity, diagonalVelocity)
        anims.play(MoveSouthWest.key, ignoreIfPlaying = true)
      case Some(West) =>
        body.setVelocity(-velocity, 0)
        anims.play(MoveWest.key, ignoreIfPlaying = true)
      case Some(NorthWest) =>
        body.setVelocity(-diagonalVelocity, -diagonalVelocity)
        anims.play(MoveNorthWest.key, ignoreIfPlaying = true)
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
}

object Player {
  object Animations {
    val MoveNorth = Animation("player-move-north", 16 to 19)
    val MoveNorthEast = Animation("player-move-northeast", 20 to 23)
    val MoveEast = Animation("player-move-east", 24 to 27)
    val MoveSouthEast = Animation("player-move-southeast", 28 to 31)
    val MoveSouth = Animation("player-move-south", 0 to 3)
    val MoveSouthWest = Animation("player-move-southwest", 4 to 7)
    val MoveWest = Animation("player-move-west", 8 to 11)
    val MoveNorthWest = Animation("player-move-northwest", 12 to 15)

    val all: Set[Animation] =
      Set(MoveNorth, MoveNorthEast, MoveEast, MoveSouthEast, MoveSouth, MoveSouthWest, MoveWest, MoveNorthWest)
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