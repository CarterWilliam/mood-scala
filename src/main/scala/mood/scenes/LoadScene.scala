package mood.scenes

import io.circe.parser.decode
import mood.config.{GameConfig, LevelConfig, LevelKey}
import mood.error.GameError
import mood.{Assets, SceneLoader}
import org.phaser.gameobjects.graphics.{GraphicsLineStyle, GraphicsOptions}
import org.phaser.scenes.Scene.SceneKey
import org.phaser.scenes.{Scene, SceneConfig}

class LoadScene extends Scene(SceneConfig(LoadScene.Key)) {
  import LoadScene._

  override type Key = LoadScene.Key.type
  override type Data = LevelKey

  private val loader = new SceneLoader(scene = this)

  override def preload(): Unit = {

    val splash = add.sprite(x = 0, y = 0, texture = "splash")
    splash.setOrigin(0, 0)

    createLoadBar()

    loadImages()
    loadMaps()
    loadSprites()
    loadAudio()
    loadScenes()
  }

  private def createLoadBar(): Unit = {
    val border = add.graphics(GraphicsOptions(
      lineStyle = GraphicsLineStyle(width = 8, color = 0)
    ))
    border.strokeRect(LoadBarX, LoadBarY, LoadBarWidth, LoadBarHeight)

    val progress = add.graphics()
    load.on("progress", { value: Double =>
      progress.clear()
      progress.fillStyle(0x0A1C52, 0.8)
      progress.fillRect(LoadBarX, LoadBarY, LoadBarWidth * value, LoadBarHeight)
    })

  }

  lazy val gameConfig = decode[GameConfig](cache.text.get(GameConfig.LoadKey)) match {
    case Left(error) =>
      val gameError = GameError(s"Failed to parse game config: ${error.getMessage}", error)
      GameError.fatal(game, gameError)
    case Right(config) =>
      config
  }

  lazy val levelConfig = {
    import mood.config.parse.circe.LevelConfigDecoder._
    val levelKey: LevelKey = sys.settings.data
    decode[LevelConfig](cache.text.get(levelKey.key)) match {
      case Left(error) =>
        val gameError = GameError(s"Failed to parse level config: ${error.getMessage}", error)
        GameError.fatal(game, gameError)
      case Right(config) =>
        config
    }
  }


  private def loadImages(): Unit = {
    loader.load(Assets.Textures.HUD)

    loader.load(Assets.Textures.Clip)
    loader.load(Assets.Textures.Shotgun)
    loader.load(Assets.Textures.Chaingun)

    loader.load(Assets.Textures.Blood)
  }

  private def loadMaps() {
    levelConfig.assets.images.foreach { asset =>
      load.image(asset.key, asset.url)
    }
    levelConfig.assets.tilemaps.foreach { asset =>
      load.tilemapTiledJSON(asset.key, asset.url)
    }
  }

  private def loadSprites(): Unit = {
    loader.load(Assets.SpriteSheets.Player)
    loader.load(Assets.SpriteSheets.Soldier)
    loader.load(Assets.SpriteSheets.Imp)

    loader.load(Assets.Textures.Bullet)
    loader.load(Assets.SpriteSheets.FireBall)

    loader.load(Assets.SpriteSheets.ArmourBonus)
  }

  private def loadAudio(): Unit = {
    loader.load(Assets.Audio.Pistol)
    loader.load(Assets.Audio.Shotgun)

    loader.load(Assets.Audio.SoldierSight)
    loader.load(Assets.Audio.SoldierHurt)
    loader.load(Assets.Audio.SoldierDie)

    loader.load(Assets.Audio.ImpSight)
    loader.load(Assets.Audio.ImpHurt)
    loader.load(Assets.Audio.ImpDie)

    loader.load(Assets.Audio.PlayerDie)
    loader.load(Assets.Audio.PlayerHurt)

    loader.load(Assets.Audio.ItemPickup)
    loader.load(Assets.Audio.WeaponPickup)
  }

  private def loadScenes(): Unit = {
    levelConfig.scenes.foreach { sceneConfig =>
      this.scene.add(sceneConfig.key, new GameScene(sceneConfig, gameConfig))
    }
  }

  override def create(): Unit = {
    scene.start[GameScene](levelConfig.initialScene)
  }

}

object LoadScene {
  case object Key extends SceneKey { val value = "loading" }

  val LoadBarX: Int = 200
  val LoadBarY: Int = 400
  val LoadBarWidth: Int = 800 - LoadBarX*2
  val LoadBarHeight: Int = 30
}