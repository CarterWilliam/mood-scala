package org.phaser.gameobjects.particles

import scala.scalajs.js
import scala.scalajs.js.|

@js.native
class ParticleEmitter extends js.Object {

  // members
  def lifespan: EmitterOp = js.native
  def quantity: EmitterOp = js.native

  // methods
  def emitParticleAt(x: Double, y: Double): Particle = js.native

}

@js.native
trait ParticleEmitterConfig extends js.Object {
  def active: js.UndefOr[Boolean]
  def alpha: js.UndefOr[Double]
  def angle: js.UndefOr[Double]
  def blendMode: js.UndefOr[Int]
  def callbackScope: js.UndefOr[Any]
  def collideBottom: js.UndefOr[Boolean]
  def collideLeft: js.UndefOr[Boolean]
  def collideRight: js.UndefOr[Boolean]
  def collideTop: js.UndefOr[Boolean]
  def frames: js.UndefOr[String | Int]
  def gravityX: js.UndefOr[Int]
  def gravityY: js.UndefOr[Int]
  def lifespan: js.UndefOr[Int]
  def on: js.UndefOr[Boolean]
  def quantity: js.UndefOr[Int]
  def scale: js.UndefOr[Double]
  def speed: js.UndefOr[Int]
  def speedX: js.UndefOr[Int]
  def speedY: js.UndefOr[Int]
}

object ParticleEmitterConfig {

  def apply(
    active: js.UndefOr[Boolean] = js.undefined,
    alpha: js.UndefOr[Double] = js.undefined,
    angle: js.UndefOr[Double] = js.undefined,
    frames: js.UndefOr[String] = js.undefined,
    gravityX: js.UndefOr[Int] = js.undefined,
    gravityY: js.UndefOr[Int] = js.undefined,
    lifespan: js.UndefOr[Int] = js.undefined,
    on: js.UndefOr[Boolean] = js.undefined,
    quantity: js.UndefOr[Int] = js.undefined,
    speed: js.UndefOr[Int] = js.undefined
  ): ParticleEmitterConfig = {
    val config = js.Dynamic.literal()

    active.foreach(config.active = _)
    alpha.foreach(config.alpha = _)
    gravityX.foreach(config.gravityX = _)
    gravityY.foreach(config.gravityY = _)
    lifespan.foreach(config.lifespan = _)
    on.foreach(config.on = _)
    quantity.foreach(config.quantity = _)
    speed.foreach(config.speed = _)

    config.asInstanceOf[ParticleEmitterConfig]
  }

  /**
    * @typedef {object} ParticleEmitterConfig
    *
    * @property {boolean} [active] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#active}.
    * @property {integer} [blendMode] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#blendMode}.
    * @property {*} [callbackScope] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#deathCallbackScope} and {@link Phaser.GameObjects.Particles.ParticleEmitter#emitCallbackScope}.
    * @property {boolean} [collideBottom] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#collideBottom}.
    * @property {boolean} [collideLeft] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#collideLeft}.
    * @property {boolean} [collideRight] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#collideRight}.
    * @property {boolean} [collideTop] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#collideTop}.
    * @property {boolean} [deathCallback] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#deathCallback}.
    * @property {*} [deathCallbackScope] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#deathCallbackScope}.
    * @property {function} [emitCallback] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#emitCallback}.
    * @property {*} [emitCallbackScope] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#emitCallbackScope}.
    * @property {Phaser.GameObjects.GameObject} [follow] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#follow}.
    * @property {number} [frequency] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#frequency}.
    * @property {number} [gravityX] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#gravityX}.
    * @property {number} [gravityY] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#gravityY}.
    * @property {integer} [maxParticles] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#maxParticles}.
    * @property {string} [name] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#name}.
    * @property {boolean} [on] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#on}.
    * @property {boolean} [particleBringToTop] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#particleBringToTop}.
    * @property {Phaser.GameObjects.Particles.Particle} [particleClass] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#particleClass}.
    * @property {boolean} [radial] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#radial}.
    * @property {number} [timeScale] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#timeScale}.
    * @property {boolean} [trackVisible] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#trackVisible}.
    * @property {boolean} [visible] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#visible}.
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [accelerationX] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#accelerationX} (emit only).
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [accelerationY] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#accelerationY} (emit only).
    * @property {number|number[]|EmitterOpOnUpdateCallback|object} [alpha] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#alpha}.
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [angle] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#angle} (emit only)
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [bounce] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#bounce} (emit only).
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [delay] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#delay} (emit only).
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [lifespan] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#lifespan} (emit only).
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [maxVelocityX] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#maxVelocityX} (emit only).
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [maxVelocityY] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#maxVelocityY} (emit only).
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [moveToX] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#moveToX} (emit only).
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [moveToY] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#moveToY} (emit only).
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [quantity] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#quantity} (emit only).
    * @property {number|number[]|EmitterOpOnUpdateCallback|object} [rotate] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#rotate}.
    * @property {number|number[]|EmitterOpOnUpdateCallback|object} [scale] - As {@link Phaser.GameObjects.Particles.ParticleEmitter#setScale}.
    * @property {number|number[]|EmitterOpOnUpdateCallback|object} [scaleX] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#scaleX}.
    * @property {number|number[]|EmitterOpOnUpdateCallback|object} [scaleY] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#scaleY}.
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [speed] - As {@link Phaser.GameObjects.Particles.ParticleEmitter#setSpeed} (emit only).
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [speedX] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#speedX} (emit only).
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [speedY] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#speedY} (emit only).
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [tint] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#tint}.
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [x] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#x} (emit only).
    * @property {number|number[]|EmitterOpOnEmitCallback|object} [y] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#y} (emit only).
    * @property {object} [emitZone] - As {@link Phaser.GameObjects.Particles.ParticleEmitter#setEmitZone}.
    * @property {ParticleEmitterBounds|ParticleEmitterBoundsAlt} [bounds] - As {@link Phaser.GameObjects.Particles.ParticleEmitter#setBounds}.
    * @property {object} [followOffset] - Assigns to {@link Phaser.GameObjects.Particles.ParticleEmitter#followOffset}.
    * @property {number} [followOffset.x] - x-coordinate of the offset.
    * @property {number} [followOffset.y] - y-coordinate of the offset.
    * @property {number|number[]|string|string[]|Phaser.Textures.Frame|Phaser.Textures.Frame[]|ParticleEmitterFrameConfig} [frames] - Sets {@link Phaser.GameObjects.Particles.ParticleEmitter#frames}.
    */

}