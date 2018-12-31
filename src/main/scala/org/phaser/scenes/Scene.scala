package org.phaser.scenes

import org.phaser.gameobjects.GameObjectFactory
import org.phaser.input.InputPlugin
import org.phaser.loader.LoaderPlugin
import org.phaser.scenes.Scene.SceneKey
import org.phaser.sound.BaseSoundManager
import org.phaser.time.Clock

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.Scene")
class Scene(config: SceneConfig) extends js.Object {
  def add: GameObjectFactory = js.native
  def input: InputPlugin = js.native
  def load: LoaderPlugin = js.native
  def scene: ScenePlugin = js.native
  def sound: BaseSoundManager = js.native
  def time: Clock = js.native

  def preload(): Unit = js.native
  def create(): Unit = js.native
  def update(time: Double, delta: Double): Unit = js.native
}

object Scene {
  type SceneKey = String
}

@js.native
trait SceneConfig extends js.Object {
  def key: SceneKey = js.native
  def active: Boolean = js.native
  def visible: Boolean = js.native
}

object SceneConfig {
  def apply(
    key: SceneKey,
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
