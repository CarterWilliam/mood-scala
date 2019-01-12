package org.phaser.tilemaps

import org.phaser.gameobjects.GameObject
import org.phaser.gameobjects.components.{Depth, Origin, Transform}

import scala.scalajs.js

@js.native
trait StaticTilemapLayer extends GameObject
  with Depth[StaticTilemapLayer]
  with Origin[StaticTilemapLayer]
  with Transform[StaticTilemapLayer] {

  def setCollisionByProperty(
    properties: js.Object,
    collides: Boolean = true,
    recalculateFaces: Boolean = true): StaticTilemapLayer = js.native

}
