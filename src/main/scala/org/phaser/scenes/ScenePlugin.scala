package org.phaser.scenes

import org.phaser.scenes.Scene.SceneId

import scala.scalajs.js

@js.native
trait ScenePlugin extends js.Object {
  def start(key: SceneId): ScenePlugin
}
