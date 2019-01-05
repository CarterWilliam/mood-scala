package org.phaser.tilemaps

import scala.scalajs.js

@js.native
trait StaticTilemapLayer extends js.Object {

  def setCollisionByProperty(
    properties: js.Object,
    collides: Boolean = true,
    recalculateFaces: Boolean = true): StaticTilemapLayer = js.native

}
