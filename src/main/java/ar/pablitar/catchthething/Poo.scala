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
import ar.pablitar.vainilla.commons.inspectors.MathInspector
import java.awt.geom.Point2D

class Poo extends SpeedyComponent[CatchTheThingScene]{
  
  
  val diameter = 40
  val radius = diameter.toDouble / 2
  val initialSpeed: Vector2D = (0.0, 300.0)

   var catched = false

  val accelerationMagnitude = 800
  var speedX: Vector2D = initialSpeed
  
  def catcher = this.getScene.catcher
  
  val collisionMargin = 30
  
  reset()

  override def update(state: DeltaState) = {
    this.position += this.speedX * state.getDelta
    super.update(state)
    if (this.isCatchedBy(catcher) && !catched) {
      this.onCaught()
      this.getScene.subScore
      this.catcher.chequearScore
    }else if (catched) {
      this.checkCollisionWithCatcherWalls()
     }else {
        this.checkIfBelowTheScreen()
    }

  }
  
  def sprite = Resources.spritePoo 
  
  this.setAppearance(sprite)

  def randomPosition() = {
    //CatchTheBallGame.width.toDouble / 2
    CatchTheThingGame.randomizer.nextDouble * CatchTheThingGame.width
  }

  def isBelowTheScreen = {
    this.position.x2 >= CatchTheThingGame.height
  }

  def center = {
    this.position + (radius, radius)
  }

  override def position_=(v: Vector2D) = super.position_=(boundWithWalls(v))

  def boundWithWalls(v: Vector2D) = {
    v
  }


  def reset() = {
    this.position = (randomPosition(), -600.0)
    speed = initialSpeed
    this.setDestroyPending(false)
    this
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

  def checkIfBelowTheScreen() = {
    if (this.position.x2 > 650)
      this.destroy()
  }  
  
    def isCatchedBy(catcher: Catcher) = {
    CollisionDetector.INSTANCE
      .collidesCircleAgainstRect(
        this.position.x1 - radius, this.position.x2 - radius, this.radius,
        catcher.topLeft().x1 + collisionMargin, catcher.topLeft().x2 + 10, catcher.width - collisionMargin * 2, this.radius)
  }
    
    def spawn() = {
     new Poo()
    }
}