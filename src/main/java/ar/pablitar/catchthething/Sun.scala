package ar.pablitar.catchthething

import com.uqbar.vainilla.GameComponent
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import ar.pablitar.vainilla.commons.math.Vector2D
import com.uqbar.vainilla.DeltaState

class Sun(position: Vector2D) extends GameComponent[CatchTheThingScene]{
  var currentRotation = 0.0
  val rotationSpeed = Math.PI / 60
  
  this.setZ(-9)
  
  override def update(state: DeltaState) = {
    currentRotation += rotationSpeed * state.getDelta
  }
  
  override def render(g: Graphics2D) = {
    val beforeTransform = g.getTransform
    val newTransform = AffineTransform.getTranslateInstance(position.x1, position.x2)
    newTransform.rotate(currentRotation)
    g.setTransform(newTransform)
    Resources.sun.renderAt(0, 0, g)
    g.setTransform(beforeTransform)
  }
}