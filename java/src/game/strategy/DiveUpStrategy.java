package game.strategy;

import java.util.List;

import game.Command;
import game.State;
import game.StaticConfig;
import game.ai.Strategy;
import game.strategy.tom.Helpers;

/**
 * Tries to dive up and get out of the water
 * @author Felix Rieger
 *
 */
public class DiveUpStrategy extends Strategy {

  final int waterResistance;
  final int floodingRate;
  
  public DiveUpStrategy(StaticConfig sc) {
    waterResistance = sc.waterResistance;
    floodingRate = sc.floodingRate;
  }
  
  @Override
  public List<Command> apply(State s) {
    //int remainingSteps = waterResistance - s.stepsUnderwater;
    //int robotDepth = s.waterLevel - s.robotRow;
    
    //int searchWidth = remainingSteps - robotDepth;  // we can look this many columns to the side of the robot's position; needs at least robotDepth steps to dive up
    
    return Helpers.moveToRow(s, s.waterLevel+1);
  }
  
  @Override
  public String toString() {
    return "DiveUpStrategy";
  }
  
  @Override
  public boolean wantsToApply(State s) {  
    if (s.waterLevel == 0 || s.waterLevel - s.robotRow <= 0)
      return false;
    
    if ((s.waterLevel - s.robotRow) > (waterResistance - s.stepsUnderwater)) // robot is too deep and will drown anyway, so do not bother to search
      return false;
    
    // only apply if there is water and robot is underwater
    return true;
  }

}
