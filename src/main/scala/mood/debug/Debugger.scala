package mood.debug

import org.phaser.scenes.Scene

object Debugger {
  def pleaseWork(scene: Scene)(block: => Unit): Unit = {
    try { block } catch {
      case error: Throwable =>
        println("ERROR!")
        println(error.getMessage)
        scene.game.destroy(removeCanvas = true, noReturn = true)
        throw error
    }
  }
}