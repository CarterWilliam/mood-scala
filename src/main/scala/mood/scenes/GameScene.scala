package mood.scenes

import mood.animation.MoodAnimations
import mood.config.{GameConfig, LevelConfig, SceneConfig}
import mood.input.{MoodKeyboardInput, PlayerInput}
import mood.scenes.GameScene.Depth
import mood.sprites.components.Killable
import mood.sprites.enemies.{EnemiesGroup, Enemy}
import mood.sprites.items.{ItemSprite, ItemsGroup}
import mood.sprites.player.Player
import mood.sprites.projectiles.{Projectile, ProjectilesGroup}
import mood.spacial.Position.Coordinates
import org.phaser.gameobjects.particles.ParticleEmitterConfig
import org.phaser.gameobjects.sprite.Sprite
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

    val bloodParticles = add.particles("blood")
    val particleEmitterConfig = ParticleEmitterConfig(
      lifespan = gameConfig.blood.lifespan,
      gravityY = gameConfig.blood.gravity,
      on = false,
      quantity = gameConfig.blood.particles,
      speed = gameConfig.blood.speed,
    )
    bloodParticles.createEmitter(particleEmitterConfig)

    val playerProjectiles = new ProjectilesGroup(scene = this)
    player = new Player(
      scene = this,
      origin = Coordinates(
        x = config.map.tileSize*config.map.playerStartX,
        y = config.map.tileSize*config.map.playerStartY),
      gameConfig = gameConfig,
      projectiles = playerProjectiles)
    cameras.main.startFollow(player, roundPixels = true)

    MoodAnimations.createAnimations(anims, gameConfig)

    val items = new ItemsGroup(scene = this, gameConfig)
    config.map.items.foreach { item =>
      items.add(item.key, item.location)
    }

    val enemyProjectiles = new ProjectilesGroup(scene = this)
    enemies = new EnemiesGroup(scene = this, gameConfig, enemyProjectiles, items)
    enemies.add("soldier", Coordinates(config.map.tileSize*10, config.map.tileSize*25))

    physics.add.collider[Player, StaticTilemapLayer](player, floor)
    physics.add.collider[Player, StaticTilemapLayer](player, lowObstacles)
    physics.add.collider[Player, StaticTilemapLayer](player, highObstacles)

    def projectileCollider[S <: Sprite : Killable]: js.Function2[Projectile, S, Unit] = (projectile, killable) => {
      bloodParticles.emitParticleAt(killable.x, killable.y)
      implicitly[Killable[S]].takeDamage(killable, projectile.config.damage)
      projectile.impact()
    }
    physics.add.collider[Projectile, Player](enemyProjectiles, player, projectileCollider[Player])
    physics.add.collider[Projectile, Enemy](playerProjectiles, enemies, projectileCollider[Enemy])

    val itemCollider: js.Function2[ItemSprite, Player, Unit] = (item, player) => {
      player.pickup(item.config)
      item.destroy()
    }
    physics.add.collider[ItemSprite, Player](items, player, itemCollider)

    keys = MoodKeyboardInput(input.keyboard)
    playerInput = new PlayerInput(keys)

    val hud = scene.get[Hud](Hud.Key)
    hud.startWatching(this)
    scene.moveAbove(config.key, Hud.Key)
    scene.launch(Hud.Key)
  }

  override def update(time: Double, delta: Double): Unit = {
    playerInput.invalidateCaches()
    player.update(playerInput, time)
    enemies.getChildren().foreach(_.update(player, time))
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
