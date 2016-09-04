package ar.pablitar.catchthething

import ar.pablitar.vainilla.commons.components.RichGameComponent
import ar.pablitar.vainilla.commons.math.Vector2D
import ar.pablitar.vainilla.appearances.TimedAppearance

class CatcherShadow extends RichGameComponent[CatchTheThingScene] {
  this.setZ(-3)
  override val appearanceCenter = -Vector2D(Resources.macetaIdle.getX, Resources.macetaIdle.getY)
  
  this.setAppearance(Resources.macetaIdleShadow)

  def onCaught() = {
    //TODO: Esta lógica se repite con el catcher. En caso de hacer actualizaciones en el catcher, 
    //va a haber que hacerlo en la sombra. Eso puede ser difícil de mantener
    Resources.macetaShadowAnimation.reset()
    this.setAppearance(TimedAppearance.fromAnimationTo(this, Resources.macetaShadowAnimation, Resources.macetaIdleShadow))
  }
}