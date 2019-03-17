package org.phaser.gameobjects

import org.phaser.events.EventEmitter
import org.phaser.physics.arcade.Body
import org.phaser.scenes.Scene

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.GameObject")
class GameObject extends EventEmitter {

  def body: Body = js.native
  def scene: Scene = js.native

  def destroy(fromScene: Boolean = false): Unit = js.native
}
