package org.phaser.loader

import org.phaser.events.EventEmitter
import org.phaser.loader.LoaderPlugin.{AudioKey, TextureKey, Url}
import org.phaser.loader.filetypes.ImageFrameConfig

import scala.scalajs.js

@js.native
trait LoaderPlugin extends EventEmitter {
  def audio(key: AudioKey, url: Url): LoaderPlugin = js.native
  def image(key: TextureKey, url: Url): LoaderPlugin = js.native
  def spritesheet(key: TextureKey, url: Url, frameConfig: ImageFrameConfig): LoaderPlugin = js.native
}

object LoaderPlugin {
  type AudioKey = String
  type TextureKey = String

  type Url = String
}
