package mood.sprites.projectiles

import mood.spacial.Direction
import mood.spacial.Position.Coordinates
import org.phaser.gameobjects.group.Group
import org.phaser.scenes.Scene

class ProjectilesGroup(scene: Scene) extends Group[Projectile](scene) {

  def add(config: Projectile.Config, origin: Coordinates, direction: Direction): Projectile = {
    val projectile = new Projectile(scene, this, origin, direction, config)
    add(projectile, addToScene = true)
    projectile
  }

}
