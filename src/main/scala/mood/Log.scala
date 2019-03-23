package mood

import scala.scalajs.js.Dynamic.global

object Log {
  def log(message: String): Unit = global.console.log(message)
  def error(message: String): Unit = global.console.error(message)
}
