package mood.sprites.projectiles

import mood.sprites.projectiles.Projectile.ProjectileConfig
import mood.util.{Coordinates, Direction}
import org.phaser.gameobjects.group.Group
import org.phaser.scenes.Scene

class ProjectilesGroup(scene: Scene) extends Group[Projectile](scene) {

  def add(origin: Coordinates, direction: Direction): Projectile = {
    val config = ProjectileConfig(
      texture = "bullet",
      damage = 10,
      origin = origin,
      direction = direction.radians,
      speed = 600)

    val projectile = new Projectile(scene, this, config)
    this.add(projectile, addToScene = true)
    projectile
  }

  def add(x: Double, y: Double, direction: Direction): Projectile = {
    add(Coordinates(x, y), direction)
  }

}
