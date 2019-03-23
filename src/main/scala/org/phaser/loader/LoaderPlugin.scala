package org.phaser.loader

import org.phaser.events.EventEmitter
import org.phaser.loader.LoaderPlugin.{AssetKey, Url}
import org.phaser.loader.filetypes.ImageFrameConfig

import scala.scalajs.js

@js.native
trait LoaderPlugin extends EventEmitter {
  def audio(key: AssetKey, url: Url): LoaderPlugin = js.native
  def image(key: AssetKey, url: Url): LoaderPlugin = js.native
  def spritesheet(key: AssetKey, url: Url, frameConfig: ImageFrameConfig): LoaderPlugin = js.native
  def text(key: AssetKey, url: Url): LoaderPlugin = js.native
  def tilemapTiledJSON(key: AssetKey, url: Url): LoaderPlugin = js.native
}

object LoaderPlugin {
  type AssetKey = String

  type Url = String
}
