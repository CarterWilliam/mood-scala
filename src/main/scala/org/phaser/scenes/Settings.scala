package org.phaser.scenes

import scala.scalajs.js

@js.native
trait SettingsConfig extends js.Object

@js.native
trait SettingsObject[SceneData] extends js.Object {
  def status: Int = js.native
  def key: String = js.native
  def active: Boolean = js.native
  def visible: Boolean = js.native
  def isBooted: Boolean = js.native
  def isTransition: Boolean = js.native
  def data: SceneData = js.native
}
