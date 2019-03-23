package org.phaser.scenes

import org.phaser.Game
import org.phaser.animations.AnimationManager
import org.phaser.cache.CacheManager
import org.phaser.cameras.twodimensional.CameraManager
import org.phaser.events.EventEmitter
import org.phaser.gameobjects.{GameObjectCreator, GameObjectFactory}
import org.phaser.input.InputPlugin
import org.phaser.loader.LoaderPlugin
import org.phaser.physics.arcade.ArcadePhysics
import org.phaser.scenes.Scene.{SceneData, SceneKey}
import org.phaser.sound.BaseSoundManager
import org.phaser.time.Clock

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.Scene")
class Scene(config: SceneConfig) extends js.Object {
  type Key <: SceneKey
  type Data <: SceneData

  def add: GameObjectFactory = js.native
  def anims: AnimationManager = js.native
  def cache: CacheManager = js.native
  def cameras: CameraManager = js.native
  def events: EventEmitter = js.native
  def game: Game = js.native
  def input: InputPlugin = js.native
  def load: LoaderPlugin = js.native
  def make: GameObjectCreator = js.native
  def physics: ArcadePhysics = js.native
  def scene: ScenePlugin = js.native
  def sound: BaseSoundManager = js.native
  def sys: Systems[Data] = js.native
  def time: Clock = js.native

  def preload(): Unit = js.native
  def create(): Unit = js.native
  def update(time: Double, delta: Double): Unit = js.native
}

object Scene {
  trait SceneKey { def value: String }
  implicit def keyAsString(id: SceneKey): String = id.value
  implicit def keyAsStringOrUndefined(id: SceneKey): js.UndefOr[String] = id.value

  trait SceneData extends js.Object
  trait NoData extends SceneData
}

@js.native
trait SceneConfig extends js.Object {
  def key: SceneKey = js.native
  def active: Boolean = js.native
  def visible: Boolean = js.native
}

object SceneConfig {
  def apply(
    key: String,
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
