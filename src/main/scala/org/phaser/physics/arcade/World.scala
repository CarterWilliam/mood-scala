package org.phaser.physics.arcade

import org.phaser.gameobjects.GameObject

import scala.scalajs.js

@js.native
trait World extends js.Object {

  def enable(
    `object`: GameObject,
    bodyType: js.UndefOr[Int] = js.undefined): World = js.native

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
