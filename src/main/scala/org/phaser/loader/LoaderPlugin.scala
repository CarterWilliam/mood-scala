package org.phaser.loader

import org.phaser.loader.LoaderPlugin.{AudioKey, TextureKey, Url}

import scala.scalajs.js

@js.native
trait LoaderPlugin extends js.Object {
  def audio(key: AudioKey, url: Url): LoaderPlugin
  def image(key: TextureKey, url: Url): LoaderPlugin
}

object LoaderPlugin {
  type AudioKey = String
  type TextureKey = String

  type Url = String
}
