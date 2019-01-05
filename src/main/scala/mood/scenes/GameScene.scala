package mood.scenes

import mood.config.{LevelConfig, SceneConfig}
import org.phaser.scenes.Scene.SceneKey
import org.phaser.scenes.{Scene, SceneConfig => PhaserSceneConfig}
import org.phaser.tilemaps.TilemapConfig

import scala.scalajs.js

class GameScene(config: SceneConfig) extends Scene(PhaserSceneConfig(config.key)) {
  override type Key = GameScene.Key
  override type Data = LevelConfig

  override def create(): Unit = {
    val tilemap = make.tilemap(TilemapConfig(key = config.map.tilemap))
    val tileset = tilemap.addTilesetImage(tilesetName = config.map.tilemapName, key = config.map.image)

    val floor = tilemap.createStaticLayer("floor", tileset, 0, 0)
    floor.setCollisionByProperty(js.Dynamic.literal(collide = true))
  }
}

object GameScene {
  case class Key(value: String) extends SceneKey
}
