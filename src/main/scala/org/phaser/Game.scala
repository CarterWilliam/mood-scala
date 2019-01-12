package org.phaser

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.Game")
class Game(config: GameConfig) extends js.Object {
  def destroy(removeCanvas: Boolean, noReturn: js.UndefOr[Boolean] = js.undefined): Unit = js.native
}
