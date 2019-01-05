package mood.config

import mood.config.LevelConfig.LevelKey
import mood.scenes.GameScene
import org.phaser.loader.LoaderPlugin.AssetKey
import org.phaser.scenes.Scene.SceneData

import scala.scalajs.js

@js.annotation.ScalaJSDefined
class LevelConfig(
  val key: LevelKey,
  val loadingImage: Option[AssetKey] = None,
  val assets: LevelAssets,
  val initialScene: GameScene.Key,
  val scenes: Seq[SceneConfig]) extends SceneData

case class LevelAssets(tilemaps: Seq[LevelAsset], images: Seq[LevelAsset])
case class LevelAsset(key: String, url: String)

object LevelConfig {
  type LevelKey = String
}

case class SceneConfig(key: GameScene.Key, map: MapConfig)
case class MapConfig(
  tilemap: String,
  tilemapName: String,
  image: String,
  tileSize: Int,
  width: Int,
  height: Int,
  scale: Int = 1)
