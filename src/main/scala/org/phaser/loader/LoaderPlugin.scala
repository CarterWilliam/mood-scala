package org.phaser.loader

import scala.scalajs.js

@js.native
trait LoaderPlugin extends js.Object {
  def image(key: String, url: String): LoaderPlugin
}
