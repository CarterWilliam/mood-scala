package mood.sprites.items

import mood.config.GameConfig
import mood.util.Coordinates
import org.phaser.gameobjects.group.Group
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.scenes.Scene

class ItemsGroup(scene: Scene, gameConfig: GameConfig) extends Group[ItemSprite](scene) {

  def add(itemKey: ItemKey, position: Coordinates): Unit = {
    val config = gameConfig.items(itemKey)
    add(new ItemSprite(scene, position, config), addToScene = true)
  }
}

class ItemSprite(
  scene: Scene,
  origin: Coordinates,
  val config: ItemConfig
) extends Sprite(scene, origin.x, origin.y, config.image) {
  scene.physics.world.enable(this)
}
