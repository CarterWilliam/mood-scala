package org.phaser.scenes

import scala.scalajs.js

@js.native
trait Systems[SceneData] extends js.Object {
  def settings: SettingsObject[SceneData] = js.native
}
