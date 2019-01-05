package org.phaser.physics.arcade

import scala.scalajs.js

@js.native
trait World extends js.Object {
  def setBounds(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    checkLeft: js.UndefOr[Boolean] = js.undefined,
    checkRight: js.UndefOr[Boolean] = js.undefined,
    checkUp: js.UndefOr[Boolean] = js.undefined,
    checkDown: js.UndefOr[Boolean] = js.undefined): World = js.native
}
