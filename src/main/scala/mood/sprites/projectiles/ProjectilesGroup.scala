package mood.sprites.projectiles

import mood.spacial.Direction
import mood.spacial.Position.Coordinates
import org.phaser.gameobjects.group.Group
import org.phaser.scenes.Scene

class ProjectilesGroup(scene: Scene) extends Group[Projectile](scene) {

  def add(origin: Coordinates, direction: Direction): Projectile = {
    val config = Projectile.Config(
      texture = "bullet",
      damage = 10,
      speed = 600)

    val projectile = new Projectile(scene, this, origin, direction, config)
    this.add(projectile, addToScene = true)
    projectile
  }

  def add(x: Double, y: Double, direction: Direction): Projectile = {
    add(Coordinates(x, y), direction)
  }

}
