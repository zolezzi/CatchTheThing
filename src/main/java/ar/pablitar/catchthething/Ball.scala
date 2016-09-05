package ar.pablitar.catchthething

import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.appearances.Circle
import java.awt.Color
import ar.pablitar.vainilla.commons.components.SpeedyComponent
import ar.pablitar.vainilla.commons.math.Vector2D
import scala.util.Random
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.colissions.CollisionDetector
import java.awt.Graphics2D
import ar.pablitar.vainilla.commons.math.Semiplane
import ar.pablitar.vainilla.commons.math.PhysicsUtils
import java.awt.geom.AffineTransform

class Ball extends SpeedyComponent[CatchTheThingScene] {
  val diameter = 20
  def radius = diameter / 2

  var catched = false

  this.speed = (Random.nextInt(800), 0)

  override val acceleration = Some(Vector2D(0, 1000))

  def catcher = this.getScene.catcher

  override def update(state: DeltaState) = {
    super.update(state)
    if (this.isCatchedBy(catcher) && !catched) {
      this.onCaught()
      catcher.caught(this)
    } else if (catched) {
      this.checkCollisionWithCatcherWalls()
    }
  }

  override def render(g: Graphics2D) {
    val beforeTransform = g.getTransform
    val newTransform = AffineTransform.getTranslateInstance(this.getX, this.getY)
    newTransform.rotate(this.speed.angle - Math.PI / 2)
    g.setTransform(newTransform)
    Resources.spriteSemilla.renderAt(0, 0, g)
    g.setTransform(beforeTransform)
  }

  val collisionMargin = 20

  def isCatchedBy(catcher: Catcher) = {
    CollisionDetector.INSTANCE
      .collidesCircleAgainstRect(
        this.topLeft().x1, this.topLeft().x2, this.radius,
        catcher.topLeft().x1 + collisionMargin, catcher.topLeft().x2 + 10, catcher.width - collisionMargin * 2, this.height)
  }

  def onCaught() = {
    this.setZ(-2)
    this.catched = true
  }

  def checkCollisionWithCatcherWalls() = {
    catcher.sideWalls.foreach {
      this.checkCollisionWithWallAndRebound(_)
    }

    if (catcher.bottomWall.circuloPasoDetras(this.position, this.radius)) {
      this.destroy()
    }
  }

  def checkCollisionWithWallAndRebound(wall: Semiplane) = {
    if (wall.circuloPasoDetras(this.position, this.radius)) {
      this.speed = PhysicsUtils.rebound(this.speed, wall.normal, 0.3)
    }
  }
}