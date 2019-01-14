package mood.sprites.projectiles

import mood.sprites.projectiles.Projectile.ProjectileConfig
import mood.util.Coordinates
import org.phaser.gameobjects.group.Group
import org.phaser.scenes.Scene

class ProjectilesGroup(scene: Scene) extends Group(scene) {

  def add(origin: Coordinates, direction: Double): Projectile = {
    val config = ProjectileConfig(
      texture = "bullet",
      damage = 10,
      origin = origin,
      direction = direction,
      speed = 600)

    val projectile = new Projectile(scene, this, config)
    this.add(projectile, addToScene = true)
    projectile
  }

}
