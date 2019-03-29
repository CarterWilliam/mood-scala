package mood.config

import mood.scenes.GameScene
import mood.spacial.Position.Coordinates
import mood.sprites.items.ItemKey
import org.phaser.loader.LoaderPlugin.AssetKey
import org.phaser.scenes.Scene.SceneData

case class LevelKey(key: String) extends SceneData

case class LevelConfig(
  key: LevelKey,
  loadingImage: Option[AssetKey] = None,
  assets: LevelAssets,
  initialScene: GameScene.Key,
  scenes: Seq[SceneConfig])

case class LevelAssets(tilemaps: Seq[LevelAsset], images: Seq[LevelAsset])
case class LevelAsset(key: String, url: String)

case class SceneConfig(key: GameScene.Key, map: MapConfig)
case class MapConfig(
  tilemap: String,
  tilemapName: String,
  image: String,
  tileSize: Int,
  width: Int,
  height: Int,
  scale: Int = 1,
  playerStartX: Int = 0,
  playerStartY: Int = 0,
  items: Seq[ItemLocation])

case class ItemLocation(key: ItemKey, location: Coordinates)
