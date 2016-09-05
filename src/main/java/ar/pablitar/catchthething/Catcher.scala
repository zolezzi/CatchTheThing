package ar.pablitar.catchthething

import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.appearances.Rectangle
import java.awt.Color
import com.uqbar.vainilla.DeltaState
import ar.pablitar.vainilla.commons.components.RichGameComponent
import ar.pablitar.vainilla.commons.math.Vector2D
import com.uqbar.vainilla.events.constants.Key
import ar.pablitar.vainilla.commons.components.SpeedyComponent
import ar.pablitar.vainilla.appearances.TimedAppearance
import java.awt.Graphics2D
import scala.collection.mutable.ArrayBuffer
import ar.pablitar.vainilla.commons.math.Semiplane
import ar.pablitar.vainilla.commons.inspectors.MathInspector

class Catcher(val shadow: CatcherShadow) extends SpeedyComponent[CatchTheThingScene] {
  private var _showDebug = false
  override def showDebug = _showDebug
  def showDebug_=(value: Boolean) = _showDebug = value
  
  def sideWalls = List(
    Semiplane(topLeft() + (8, 8), Vector2D(3.1, -1)),
    Semiplane(topRight() + (-8, 8), Vector2D(-3.1, -1))
  )
  
  def bottomWall = Semiplane(this.position + (0, -30), Vector2D(0, -1))
  
  def walls = bottomWall +: sideWalls
  
  val speedMagnitude = 600.0 // px/seg
  this.setZ(-1)
  
  this.setAppearance(
      Resources.macetaIdle
  )
  
  this.position = Vector2D(400, 500) 
  
  shadow.position = this.position
  
  override def update(state :DeltaState) = {
    val speedX:Double = 
      if(state.isKeyBeingHold(Key.RIGHT)) speedMagnitude 
      else if(state.isKeyBeingHold(Key.LEFT)) -speedMagnitude
      else 0.0
    
    this.speed = (speedX, 0.0)
    
    this.setAppearanceAccordingToSpeed(speedX)
    
    if(state.isKeyPressed(Key.D)) {
      this.showDebug = !this.showDebug
    }
    
    super.update(state)
    shadow.position = this.position
  }
  
  override def render(graphics: Graphics2D) = {
    super.render(graphics)
    if(showDebug) {
      graphics.drawRect(this.topLeft().x1.toInt, this.topLeft().x2.toInt + 10, this.width.toInt, 40)
      walls.foreach { w => MathInspector.renderSemiplane(graphics, w) }
    }
  }
  
  override val appearanceCenter = -Vector2D(Resources.macetaIdle.getX, Resources.macetaIdle.getY)

  def caught(ball: Ball) = {
    Resources.macetaAnimation.reset()
    this.setAppearance(TimedAppearance.fromAnimationTo(this, Resources.macetaAnimation, Resources.macetaIdle))
    shadow.onCaught()
  }

  def setAppearanceAccordingToSpeed(sp: Double) = {
//    if(sp > 0) {
//      this.setAppearance(x$1)
//    } TODO
  }
}




