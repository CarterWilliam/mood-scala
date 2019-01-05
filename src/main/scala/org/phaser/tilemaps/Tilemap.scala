package org.phaser.tilemaps

import scala.scalajs.js

@js.native
trait Tilemap extends js.Object {

  def addTilesetImage(
    tilesetName: String,
    key: js.UndefOr[String] = js.undefined,
    tileWidth: js.UndefOr[Int] = js.undefined,
    tileHeight: js.UndefOr[Int] = js.undefined,
    tileMargin: js.UndefOr[Int] = js.undefined,
    tileSpacing: js.UndefOr[Int] = js.undefined,
    gid: Int = 0): Tileset = js.native

  def createStaticLayer(
    layerID: String,
    tileset: Tileset,
    x: Int,
    y: Int): StaticTilemapLayer = js.native

  def getObjectLayer(name: js.UndefOr[String]): ObjectLayer = js.native
}
