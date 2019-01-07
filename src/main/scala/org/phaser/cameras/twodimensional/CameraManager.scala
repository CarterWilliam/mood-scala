package org.phaser.cameras.twodimensional

import scala.scalajs.js

@js.native
trait CameraManager extends js.Object {

  def main: Camera

  def add(
    x: Int = 0,
    y: Int = 0,
    width: js.UndefOr[Int] = js.undefined,
    height: js.UndefOr[Int] = js.undefined,
    makeMain: Boolean = false,
    name: js.UndefOr[String] = js.undefined)
}
