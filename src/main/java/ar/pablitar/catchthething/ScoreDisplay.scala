package ar.pablitar.catchthething

import com.uqbar.vainilla.appearances.Label
import java.awt.Font
import java.awt.Color
import com.uqbar.vainilla.GameComponent

class ScoreDisplay(scene: CatchTheThingScene) extends GameComponent[CatchTheThingScene] {
  var score = 0
  
  this.setScene(scene)

  this.updateLabel

  //this.setX(this.getGame.getDisplayWidth - this.getWidth - 40)
  //this.setY(10)

  def updateLabel = {
    this.setAppearance(new Label(new Font(Font.SANS_SERIF, Font.BOLD, 32), Color.BLACK, "Score: " + score))
  }
  
  def getScore = {
     score
  }

  def sumScore = {
    score += 10
    this.updateLabel
  }
  
  def subScore = {
    score -= 10
    this.updateLabel
  }
}