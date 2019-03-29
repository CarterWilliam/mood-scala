package mood.config.parse.circe

import io.circe.Decoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveDecoder
import mood.config._
import mood.scenes.GameScene

object LevelConfigDecoder {
  import WithDefaultsConfig._
  import ItemDecoders._
  import SpacialDecoders._

  private implicit val levelAssetDecoder: Decoder[LevelAsset] = deriveDecoder[LevelAsset]
  private implicit val levelAssetsDecoder: Decoder[LevelAssets] = deriveDecoder[LevelAssets]
  private implicit val itemLocationDecoder: Decoder[ItemLocation] = deriveDecoder[ItemLocation]
  private implicit val mapConfigDecoder: Decoder[MapConfig] = deriveDecoder[MapConfig]

  private implicit val decodeLevelKey: Decoder[LevelKey] = {
    Decoder.decodeString.map(LevelKey)
  }

  private implicit val decodeSceneKey: Decoder[GameScene.Key] = {
    Decoder.decodeString.map(GameScene.Key)
  }

  private implicit val sceneConfigDecoder: Decoder[SceneConfig] = deriveDecoder[SceneConfig]

  implicit val levelConfigDecoder: Decoder[LevelConfig] = deriveDecoder[LevelConfig]
}
