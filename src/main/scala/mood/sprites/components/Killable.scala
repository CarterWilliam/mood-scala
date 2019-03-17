package mood.sprites.components

import org.phaser.gameobjects.sprite.Sprite

import scala.scalajs.js

class Killable(sprite: Sprite, val maxHealth: Int, onDeath: Sprite => Unit) extends js.Object {

  var health: Int = maxHealth

  def takeDamage(hitPoints: Int): Unit = {
    health -= hitPoints
    if (health < 1) {
      die()
    }
  }

  private def die(): Unit = {
    sprite.body.stop()
    onDeath(sprite)
  }

}