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
    var speedX:Double = 0.0
      
        if(state.isKeyBeingHold(Key.RIGHT)){ 
          speedX = speedMagnitude 
        }
        if(state.isKeyBeingHold(Key.LEFT)) {
          speedX = -speedMagnitude
        }
    
    this.speed = (speedX, 0.0)
   

    
   // this.setAppearanceAccordingToSpeed(speedX)
    
    if(state.isKeyPressed(Key.D)) {
      this.showDebug = !this.showDebug
    }

    
    super.update(state)
    this.fueraDelLimite()
    
    shadow.position = this.position
  }
  
  override def render(graphics: Graphics2D) = {
    super.render(graphics)
    if(showDebug) {
      graphics.drawRect(this.topLeft().x1.toInt + 30, this.topLeft().x2.toInt + 10, this.width.toInt -30 * 2, 20)
      walls.foreach { w => MathInspector.renderSemiplane(graphics, w) }
    }
  }
  
  override val appearanceCenter = -Vector2D(Resources.macetaIdle.getX, Resources.macetaIdle.getY)

  def caught(ball: Ball) = {
    Resources.macetaAnimation.reset()
    this.setAppearance(TimedAppearance.fromAnimationTo(this, Resources.macetaAnimation, Resources.macetaIdle))
    shadow.onCaught()
  }

  
  def fueraDelLimite(){
	  
    if(this.position.x1 >= 900.0 ){
		  this.position = (50.0, 500.0)
	  }else if (this.position.x1 <= 0.0){
		  this.position = (800.0 , 500.0)
	  }
    
  }
  
  def chequearScore = {

    if (this.getScene.getScore < 0) {
      this.death
    }
  }
    def death: Unit = {
      this.getScene.gameOver
      this.getScene.removeComponent(shadow)
      this.getScene.removeComponent(this)
      shadow.destroy()
      this.destroy
    }
    
    
  def setAppearanceAccordingToSpeed(sp: Double) = {
//    if(sp > 0) {
//      this.setAppearance(x$1)
//    } TODO
  }
}




