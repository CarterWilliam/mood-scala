package mood.sprites.enemies

import mood.config.GameConfig
import mood.sprites.items.ItemsGroup
import mood.sprites.projectiles.ProjectilesGroup
import mood.util.Position.Coordinates
import org.phaser.gameobjects.group.Group
import org.phaser.scenes.Scene

import scala.scalajs.js

class EnemiesGroup(scene: Scene,
                   gameConfig: GameConfig,
                   projectiles: ProjectilesGroup,
                   items: ItemsGroup) extends Group[Enemy](scene) {

  def add(key: String, position: Coordinates): Unit = {
    gameConfig.enemies.get(key) match {
      case Some(enemyConfig) =>
        val enemy = new Enemy(scene, position, enemyConfig, projectiles, items)
        add(enemy, addToScene = true)
      case None =>
        // TODO enemy keys as a case object. see items
        throw js.JavaScriptException(s"enemy config key '$key' not recognised")
    }
  }

  def add(config: Enemy.Config, position: Coordinates): Unit = {
    val enemy = new Enemy(scene, position, config, projectiles, items)
    add(enemy, addToScene = true)
  }
}
