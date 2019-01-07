package mood.sprites

import mood.Assets
import mood.scenes.GameScene
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.scenes.Scene

class Player(scene: Scene, x: Int, y: Int)
  extends Sprite(scene, x, y, Assets.SpriteSheets.Player.key) {

  var health: Int = 100

  scene.physics.world.enable(this)
  scene.add.existing(this)

  body.setSize(30, 40)
  body.setOffset(15, 20)
  body.setCollideWorldBounds()

  setDepth(GameScene.Depth.Sprite)
}

trait PlayerConfig {

}