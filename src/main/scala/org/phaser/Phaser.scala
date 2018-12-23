package org.phaser

import scala.scalajs.js
import org.scalajs.dom.raw._
import scala.scalajs.js.|

object Phaser {
  sealed trait RenderType { def value: Int }
  object RenderType {
    case object AUTO extends RenderType { val value = 0 }
    case object CANVAS extends RenderType { val value = 1 }
    case object WEBGL extends RenderType { val value = 2 }
  }
}
