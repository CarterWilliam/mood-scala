package mood.scenes

import mood.Assets
import mood.config.{LevelConfig, SceneConfig}
import mood.scenes.GameScene.Depth
import mood.sprites.Player
import org.phaser.animations.{AnimationConfig, GenerateFrameNumbersConfig}
import org.phaser.input.keyboard.CursorKeys
import org.phaser.scenes.Scene.SceneKey
import org.phaser.scenes.{Scene, SceneConfig => PhaserSceneConfig}
import org.phaser.tilemaps.TilemapConfig

import scala.scalajs.js

class GameScene(config: SceneConfig) extends Scene(PhaserSceneConfig(config.key)) {
  override type Key = GameScene.Key
  override type Data = LevelConfig

  var keys: CursorKeys = _
  var player: Player = _

  var killAfter: Int = 10

  override def create(): Unit = {
    val tilemap = make.tilemap(TilemapConfig(key = config.map.tilemap))
    val tileset = tilemap.addTilesetImage(tilesetName = config.map.tilemapName, key = config.map.image)

    val collideConfig = js.Dynamic.literal(collide = true)
    val floor = tilemap.createStaticLayer("floor", tileset, 0, 0)
    floor.setCollisionByProperty(collideConfig)

    val lowObstacles = tilemap.createStaticLayer("obstacle-low", tileset, 0, 0)
    lowObstacles.setCollisionByProperty(collideConfig)

    val highObstacles = tilemap.createStaticLayer("obstacle-high", tileset, 0, 0)
    highObstacles.setCollisionByProperty(collideConfig)

    val foreground = tilemap.createStaticLayer("foreground", tileset, 0, 0)
    foreground.setDepth(Depth.Foreground)

    physics.world.setBounds(0, 0, config.map.tileSize * config.map.width, config.map.tileSize * config.map.height)

    player = new Player(this, config.map.tileSize*config.map.playerStartX, config.map.tileSize*config.map.playerStartY)
    cameras.main.startFollow(player, roundPixels = true)

    createAnimations()

    keys = input.keyboard.createCursorKeys()
  }

  private def createAnimations(): Unit = {
    anims.create(AnimationConfig(
      key = "player-move-south",
      frames = anims.generateFrameNumbers(Assets.SpriteSheets.Player.key, GenerateFrameNumbersConfig(start = 0, end = 3)),
      frameRate = Some(10),
      repeat = -1
    ))
  }

  override def update(time: Double, delta: Double): Unit = {
    player.update(keys)
  }
}

object GameScene {
  case class Key(value: String) extends SceneKey

  object Depth {
    val Floor = 0
    val Obstacles = 1
    /* Pick your body up and drop it */ val OnTheFloor = 2
    val Sprite = 3
    val Foreground = 4
  }
}
