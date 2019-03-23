package mood.sprites.enemies

import mood.config.GameConfig
import mood.sprites.projectiles.ProjectilesGroup
import mood.util.Coordinates
import org.phaser.gameobjects.group.Group
import org.phaser.scenes.Scene

import scala.scalajs.js

class EnemiesGroup(scene: Scene,
                   gameConfig: GameConfig,
                   projectiles: ProjectilesGroup) extends Group[Enemy](scene) {

  def add(key: String, position: Coordinates): Unit = {
    gameConfig.enemies.get(key) match {
      case Some(enemyConfig) =>
        val enemy = new Enemy(scene, position, enemyConfig, projectiles)
        add(enemy, addToScene = true)
      case None =>
        throw js.JavaScriptException(s"enemy config key '$key' not recognised")
    }
  }

  def add(config: Enemy.Config, position: Coordinates): Unit = {
    val enemy = new Enemy(scene, position, config, projectiles)
    add(enemy, addToScene = true)
  }
}
