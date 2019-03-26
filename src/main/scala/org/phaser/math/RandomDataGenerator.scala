package org.phaser.math

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.Math.RandomDataGenerator")
class RandomDataGenerator extends js.Object {
  def integer(): Int = js.native
  def frac(): Double = js.native
  def real(): Double = js.native
  def integerInRange(min: Int, max: Int): Int = js.native
  def between(min: Int, max: Int): Int = js.native
  def realInRange(min: Double, max: Double): Double = js.native
  def normal(): Double = js.native
  def uuid(): String = js.native
  def pick[T](array: js.Array[T]): T = js.native
  def sign(): Int = js.native
  def weightedPick[T](array: js.Array[T]): T = js.native
  def timestamp(min: Long, max: Long): Long = js.native
  def angle(): Double = js.native
  def rotation(): Double = js.native
  def state(state: String): String = js.native
  def shuffle[T](array: js.Array[T]): js.Array[T] = js.native
}

@js.native
@js.annotation.JSGlobal("Phaser.Math.RND")
object RND extends RandomDataGenerator
