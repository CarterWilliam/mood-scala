package mood.sprites.enemies

import mood.animation.MoodAnimations.Animation
import mood.sprites.components.Killable
import mood.sprites.projectiles.ProjectilesGroup
import mood.util.Coordinates
import org.phaser.animations.AnimationConfig.RepeatConfig.{Forever, Never}
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.loader.LoaderPlugin.AssetKey
import org.phaser.scenes.Scene

class Enemy(scene: Scene,
            origin: Coordinates,
            config: Enemy.Config,
            projectiles: ProjectilesGroup) extends Sprite(scene, origin.x, origin.y, config.key) {

  scene.physics.world.enable(this)

  anims.play(s"${config.key}-passive")

  val killable: Killable = new Killable(this, 5, { _: Sprite =>
    anims.play(Enemy.Animations.Die.key)
    on("animationcomplete", { () =>
      body.destroy()
    })
  })

  def update() = {

  }

}

object Enemy {

  case class Config(
    key: AssetKey,
    health: Int)

  object Animations {
    val Passive = Animation("soldier-passive", Seq(0, 2), Forever, frameRate = 2)

    val MoveNorth = Animation("soldier-move-north", 28 to 31, Forever)
    val MoveNorthEast = Animation("soldier-move-northeast", 35 to 38, Forever)
    val MoveEast = Animation("soldier-move-east", 42 to 45, Forever)
    val MoveSouthEast = Animation("soldier-move-southeast", 49 to 52, Forever)
    val MoveSouth = Animation("soldier-move-south", 0 to 3, Forever)
    val MoveSouthWest = Animation("soldier-move-southwest", 7 to 10, Forever)
    val MoveWest = Animation("soldier-move-west", 14 to 17, Forever)
    val MoveNorthWest = Animation("soldier-move-northwest", 21 to 24, Forever)

    val ShootNorth = Animation("soldier-shoot-north", Seq(32, 33, 32), Never)
    val ShootNorthEast = Animation("soldier-shoot-northeast", Seq(39, 40, 39), Never)
    val ShootEast = Animation("soldier-shoot-east", Seq(46, 47, 46), Never)
    val ShootSouthEast = Animation("soldier-shoot-southeast", Seq(53, 54, 53), Never)
    val ShootSouth = Animation("soldier-shoot-south", Seq(4, 5, 4), Never)
    val ShootSouthWest = Animation("soldier-shoot-southwest", Seq(11, 12, 11), Never)
    val ShootWest = Animation("soldier-shoot-west", Seq(18, 19, 18), Never)
    val ShootNorthWest = Animation("soldier-shoot-northwest", Seq(25, 26, 25), Never)

    val HitNorth = Animation("soldier-hit-north", Seq(34), Never)
    val HitNorthEast = Animation("soldier-hit-northeast", Seq(41), Never)
    val HitEast = Animation("soldier-hit-east", Seq(48), Never)
    val HitSouthEast = Animation("soldier-hit-southeast", Seq(55), Never)
    val HitSouth = Animation("soldier-hit-south", Seq(6), Never)
    val HitSouthWest = Animation("soldier-hit-southwest", Seq(13), Never)
    val HitWest = Animation("soldier-hit-west", Seq(20), Never)
    val HitNorthWest = Animation("soldier-hit-northwest", Seq(27), Never)

    val Die = Animation("soldier-die", 56 to 61, Never)

    val all = Seq(
      Passive,
      MoveNorth,
      MoveNorthEast,
      MoveEast,
      MoveSouthEast,
      MoveSouth,
      MoveSouthWest,
      MoveWest,
      MoveNorthWest,
      ShootNorth,
      ShootNorthEast,
      ShootEast,
      ShootSouthEast,
      ShootSouth,
      ShootSouthWest,
      ShootWest,
      ShootNorthWest,
      HitNorth,
      HitNorthEast,
      HitEast,
      HitSouthEast,
      HitSouth,
      HitSouthWest,
      HitWest,
      HitNorthWest,
      Die
    )
  }

}