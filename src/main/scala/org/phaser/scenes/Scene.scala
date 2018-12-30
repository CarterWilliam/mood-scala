package org.phaser.scenes

import org.phaser.gameobjects.GameObjectFactory
import org.phaser.loader.LoaderPlugin
import org.phaser.scenes.Scene.SceneId

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.Scene")
class Scene(config: SceneConfig) extends js.Object {
  def add: GameObjectFactory = js.native
  def load: LoaderPlugin = js.native
  def scene: ScenePlugin = js.native

  def preload(): Unit = js.native
  def create(): Unit = js.native
  def update(time: Double, delta: Double): Unit = js.native
}

object Scene {
  type SceneId = String
}

@js.native
trait SceneConfig extends js.Object {
  def key: SceneId = js.native
  def active: Boolean = js.native
  def visible: Boolean = js.native
}

object SceneConfig {
  def apply(
    key: SceneId,
    active: Boolean = false,
    visible: Boolean = true
  ): SceneConfig = {
    js.Dynamic.literal(
      key = key,
      active = active,
      visible = visible
    ).asInstanceOf[SceneConfig]
  }
}
