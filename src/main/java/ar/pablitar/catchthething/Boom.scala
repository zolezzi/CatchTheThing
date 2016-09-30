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

class Boom extends SpeedyComponent[CatchTheThingScene]{
  
  
  val diameter = 20
  val radius = diameter.toDouble / 2
  val initialSpeed: Vector2D = (0.0, 300.0)
  
  var colisiono = false
   
  val accelerationMagnitude = 900
  var speedX: Vector2D = initialSpeed
  
  def catcher = this.getScene.catcher
  
  val collisionMargin = 20
  
  reset()

  override def update(state: DeltaState) = {
    this.position += this.speedX * state.getDelta
    super.update(state)
    if (this.isCollidedBy(catcher) && !colisiono) {
      this.collision()
      this.catcher.death
      this.crearExplosionPorCollision
    }else if (colisiono) {
      this.checkCollisionWithCatcher
     }else {
        this.checkIfBelowTheScreen()
    }
      this.crearExplosion
  }
  
  def crearExplosionPorCollision {
    val e = new Explosion()
    e.position = Vector2D(catcher.position.x1, 400)
    this.getScene.addComponent(e) 
    this.destroy()
  }
  
  
  def crearExplosion {
    if(this.position.x2 >= 500){
      val e = new Explosion()
      e.position = Vector2D(this.position.x1, 550)
      this.getScene.addComponent(e)     
    }
  }
  
  def sprite = Resources.spriteBoom
  
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
  
  def collision() = {
    this.setZ(-2)
    this.colisiono = true
  }
    
    def checkCollisionWithCatcherWalls() = {
    catcher.sideWalls.foreach {
      this.checkCollisionWithWallAndRebound(_)
    }

    if (catcher.bottomWall.circuloPasoDetras(this.position, this.radius)) {
      this.destroy()
    }
  }

  def checkCollisionWithCatcher = {
    if (catcher.bottomWall.circuloPasoDetras(this.position, this.radius)) {
      this.destroy()
    }
    if (this.position.x2 >= 500){
      this.getScene.addComponent(new Explosion)
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
    
    def isCollidedBy(catcher : Catcher) ={
      CollisionDetector.INSTANCE.collidesCircleAgainstRect(this.position.x1 - radius, this.position.x2 - radius, this.radius,
        catcher.topLeft().x1 + collisionMargin, catcher.topLeft().x2, catcher.width, this.radius)
    }
    
    def spawn() = {
     new Boom()
    }
}