package org.phaser.gameobjects

import org.phaser.events.EventEmitter

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.GameObject")
class GameObject extends EventEmitter {
  def destroy(fromScene: Boolean = false): Unit = js.native
}
