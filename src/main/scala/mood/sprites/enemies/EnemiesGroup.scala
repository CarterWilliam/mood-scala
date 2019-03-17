package mood.sprites.enemies

import mood.sprites.projectiles.ProjectilesGroup
import mood.util.Coordinates
import org.phaser.gameobjects.group.Group
import org.phaser.scenes.Scene

class EnemiesGroup(scene: Scene, projectiles: ProjectilesGroup) extends Group[Enemy](scene) {

  def add(config: Enemy.Config, position: Coordinates): Unit = {
    val enemy = new Enemy(scene, position, config, projectiles)
    add(enemy, addToScene = true)
  }
}
