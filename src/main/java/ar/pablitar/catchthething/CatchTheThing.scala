package ar.pablitar.catchthething

import com.uqbar.vainilla.Game
import java.awt.Dimension

class CatchTheThing extends Game {
  def getDisplaySize(): Dimension = {
    new Dimension(800, 600)
  }

  def getTitle(): String = {
    "Atrapa la Cosa"
  }

  def initializeResources(): Unit = {
   
  }

  def setUpScenes(): Unit = {
    this.setCurrentScene(new CatchTheThingScene())
  }
  
}





