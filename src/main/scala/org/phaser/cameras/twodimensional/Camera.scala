package org.phaser.cameras.twodimensional

import org.phaser.gameobjects.GameObject

import scala.scalajs.js

@js.native
trait Camera extends js.Object {

  def startFollow(
    target: GameObject,
    roundPixels: Boolean = false,
    lerpX: Int = 1,
    lerpY: Int = 1,
    offsetX: Int = 0,
    offsetY: Int = 0): Camera = js.native
}
