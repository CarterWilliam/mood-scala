package org.phaser.gameobjects.group

import org.phaser.gameobjects.GameObject
import org.phaser.scenes.Scene

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.JSConverters._

@js.native
@js.annotation.JSGlobal("Phaser.GameObjects.Group")
class Group[GO <: GameObject](scene: Scene,
                              children: js.UndefOr[Seq[GO]] = js.undefined,
                              config: js.UndefOr[GroupConfig] = js.undefined) extends js.Object {

  def add(child: GameObject, addToScene: Boolean): Group[GO] = js.native

  def getChildren(): js.Array[GO] = js.native

  def remove(child: GameObject,
             removeFromScene: Boolean = false,
             destroyChild: Boolean = false): Group[GO] = js.native
}

@js.native
trait GroupConfig extends js.Object {
  def active: Boolean
  def maxSize: Int
  def defaultKey: String
  def defaultFrame: js.UndefOr[String | Int]
  def runChildUpdate: Boolean
  def createCallback: js.Function1[GameObject, Unit]
  def removeCallback: js.Function1[GameObject, Unit]
  def createMultipleCallback: js.Function1[js.Array[GameObject], Unit]
}

object GroupConfig {
  def apply(
    active: Boolean = true,
    maxSize: MaxSize = MaxSize.Unlimited,
    defaultKey: Option[String] = None,
    defaultFrame: Option[Int] = None,
    runChildUpdate: Boolean = false,
    createCallback: Option[GameObject => Unit] = None,
    removeCallback: Option[GameObject => Unit] = None,
    createMultipleCallback: Option[Seq[GameObject] => Unit] = None
  ): GroupConfig = {
    js.Dynamic.literal(
      active = active,
      maxSize = maxSize.value,
      defaultKey = defaultKey.orUndefined,
      defaultFrame = defaultFrame.orUndefined,
      runChildUpdate = runChildUpdate,
      createCallback = createCallback.orUndefined,
      removeCallback = removeCallback.orUndefined,
      createMultipleCallback = createMultipleCallback.orUndefined
    ).asInstanceOf[GroupConfig]
  }

  sealed trait MaxSize { def value: Int }
  object MaxSize {
    case object Unlimited extends MaxSize { val value: Int = -1 }
    case class Limited(limit: Int) extends MaxSize { val value: Int = limit }
  }
}
