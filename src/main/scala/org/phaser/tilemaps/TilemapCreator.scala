package org.phaser.tilemaps

import scala.scalajs.js
import js.JSConverters._

@js.native
trait TilemapCreator extends js.Object {
  def tilemap(config: TilemapConfig): Tilemap
}

@js.native
trait TilemapConfig extends js.Object {
  def key: String
  def data: js.UndefOr[js.Array[js.Array[Int]]]
  def tileWidth: js.UndefOr[Int]
  def tileHeight: js.UndefOr[Int]
  def width: js.UndefOr[Int]
  def height: js.UndefOr[Int]
  def insertNull: js.UndefOr[Boolean]
}

object TilemapConfig {
  def apply(
    key: String,
    data: Option[Seq[Seq[Int]]] = None,
    tileWidth: Option[Int] = None,
    tileHeight: Option[Int] = None,
    width: Option[Int] = None,
    height: Option[Int] = None,
    insertNull: Option[Boolean] = None,
  ): TilemapConfig = {

    js.Dynamic.literal(
      key = key,
      data = data.map(_.map(_.toJSArray).toJSArray).orUndefined,
      tileWidth = tileWidth.orUndefined,
      tileHeight = tileHeight.orUndefined,
      width = width.orUndefined,
      height = height.orUndefined,
      insertNull = insertNull.orUndefined
    ).asInstanceOf[TilemapConfig]
  }
}
