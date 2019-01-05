package mood.scenes

import mood.config.LevelConfig
import mood.scenes.BootScene.BootId
import mood.{Assets, SceneLoader}
import org.phaser.gameobjects.graphics.{GraphicsLineStyle, GraphicsOptions}
import org.phaser.scenes.Scene.SceneKey
import org.phaser.scenes.{Scene, SceneConfig}

import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
class LoadScene extends Scene(LoadScene.Config) {
  import LoadScene._

  override type Key = LoadId.type
  override type Data = LevelConfig

  private val loader = new SceneLoader(scene = this)

  override def preload(): Unit = {

    val splash = add.sprite(x = 0, y = 0, texture = "splash")
    splash.setOrigin(0, 0)

    createLoadBar()

    loadImages()
    loadMaps(sys.settings.data)
    loadSprites()
    loadAudio()
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

  private def loadImages(): Unit = {
    loader.load(Assets.Textures.HUD)

    loader.load(Assets.Textures.Clip)
    loader.load(Assets.Textures.Shotgun)
    loader.load(Assets.Textures.Chaingun)
  }

  private def loadMaps(levelConfig: LevelConfig) {
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

  override def create(): Unit = {
    scene.start[BootScene](BootId)
  }

}

object LoadScene {
  case object LoadId extends SceneKey { val value = "loading" }

  val Config = SceneConfig("loading")

  val LoadBarX: Int = 200
  val LoadBarY: Int = 400
  val LoadBarWidth: Int = 800 - LoadBarX*2
  val LoadBarHeight: Int = 30
}