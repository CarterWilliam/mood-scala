package org.phaser.scenes

import org.phaser.scenes.Scene.SceneKey

import scala.scalajs.js

@js.native
trait ScenePlugin extends js.Object {
  def start(key: SceneKey): ScenePlugin
}
