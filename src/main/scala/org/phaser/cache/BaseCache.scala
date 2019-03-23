package org.phaser.cache

import scala.scalajs.js

@js.native
class BaseCache[Object] extends js.Object {
  def get(key: String): Object = js.native
}
