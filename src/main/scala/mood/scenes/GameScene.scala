package mood.scenes

import mood.animation.MoodAnimations
import mood.config.{GameConfig, LevelConfig, SceneConfig}
import mood.input.{MoodKeyboardInput, PlayerInput}
import mood.scenes.GameScene.Depth
import mood.sprites.Player
import mood.sprites.enemies.{EnemiesGroup, Enemy}
import mood.sprites.projectiles.{Projectile, ProjectilesGroup}
import mood.util.Coordinates
import org.phaser.scenes.Scene.SceneKey
import org.phaser.scenes.{Scene, SceneConfig => PhaserSceneConfig}
import org.phaser.tilemaps.{StaticTilemapLayer, TilemapConfig}

import scala.scalajs.js

class GameScene(config: SceneConfig, gameConfig: GameConfig) extends Scene(PhaserSceneConfig(config.key)) {
  override type Key = GameScene.Key
  override type Data = LevelConfig

  var keys: MoodKeyboardInput = _
  var playerInput: PlayerInput = _
  var player: Player = _
  var enemies: EnemiesGroup = _

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

    val playerProjectiles = new ProjectilesGroup(scene = this)
    player = new Player(
      scene = this,
      origin = Coordinates(
        x = config.map.tileSize*config.map.playerStartX,
        y = config.map.tileSize*config.map.playerStartY),
      projectiles = playerProjectiles)
    cameras.main.startFollow(player, roundPixels = true)

    MoodAnimations.createAnimations(anims)

    val enemyProjectiles = new ProjectilesGroup(scene = this)
    enemies = new EnemiesGroup(scene = this, gameConfig, enemyProjectiles)
    enemies.add("soldier", Coordinates(config.map.tileSize*10, config.map.tileSize*25))

    physics.add.collider[Player, StaticTilemapLayer](player, floor)
    physics.add.collider[Player, StaticTilemapLayer](player, lowObstacles)
    physics.add.collider[Player, StaticTilemapLayer](player, highObstacles)

    val enemyCollideProjectile: js.Function2[Projectile, Enemy, Unit] = (projectile, enemy) => {
      enemy.killable.takeDamage(projectile.config.damage)
      projectile.impact()
    }
    physics.add.collider[Projectile, Enemy](playerProjectiles, enemies, enemyCollideProjectile)

    keys = MoodKeyboardInput(input.keyboard)
    playerInput = new PlayerInput(keys)

    val hud = scene.get[Hud](Hud.Key)
    hud.startWatching(this)
    scene.moveAbove(config.key, Hud.Key)
    scene.launch(Hud.Key)
  }

  override def update(time: Double, delta: Double): Unit = {
    playerInput.invalidateCaches()
    player.update(playerInput)
    enemies.getChildren().foreach(_.update(player))
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
