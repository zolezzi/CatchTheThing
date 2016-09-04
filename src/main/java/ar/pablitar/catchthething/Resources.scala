package ar.pablitar.catchthething

import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.appearances.Animation

object Resources {
  
  val spriteSize = 200
  val scale = 0.8
  
  def spriteHeight = (200 * scale).toInt
  def spriteWidth = (200 * scale).toInt
  
  
  val spritesheet = Sprite.fromImage("maceta.png").scale(scale)
  
  val macetaAnimation = animationFromSpritesheet(spritesheet, spriteWidth, spriteHeight, 0.04)
  val macetaIdle = spritesheet.crop(0, 0, spriteHeight, spriteWidth).center()

  def animationFromSpritesheet(spritesheet: Sprite, width: Int, height: Int, frameTime: Double) = {
    val frameCountH = (spritesheet.getWidth / width).toInt
    val frameCountV = (spritesheet.getHeight / height).toInt
    
    val sprites = for(i<- 0 to (frameCountH - 1);
        j<- 0 to (frameCountV - 1)) yield {
      spritesheet.crop(width * i, height * j, width, height).center()
    }
    
    new Animation(frameTime, sprites:_*)
  }
}