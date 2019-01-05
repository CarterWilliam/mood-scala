package mood.config

import mood.config.LevelConfig.LevelKey
import org.phaser.loader.LoaderPlugin.AssetKey
import org.phaser.scenes.Scene.SceneData

import scala.scalajs.js

@js.annotation.ScalaJSDefined
class LevelConfig(
  val key: LevelKey,
  val loadingImage: Option[AssetKey] = None,
  val assets: LevelAssets,
  val scenes: Seq[SceneConfig]) extends SceneData

case class LevelAssets(tilemaps: Seq[LevelAsset], images: Seq[LevelAsset])
case class LevelAsset(key: String, url: String)

object LevelConfig {
  type LevelKey = String
}

case class SceneConfig(key: String)