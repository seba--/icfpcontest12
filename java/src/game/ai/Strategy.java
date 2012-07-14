package game.ai;

import game.Command;
import game.State;

import java.util.List;

/**
 * A strategy for computing a couple of commands.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */
public abstract class Strategy {
  
  /**
   * How often this strategy was applied.
   */
  public int applicationCount = 0;


  /**
   * Returns a list of commands to be applied to this state.
   * 
   * <p>
   * Callers must not change this list!
   * 
   * @param state
   * @return
   */
  public abstract List<Command> apply(State state);
  
  /**
   * Determine whether this strategy can only be used once for a given state
   * or several times, yielding different results each time.
   */
  public boolean isUseOnce() {
    return true;
  }
  
  /**
   * A strategy may deny to be applied.
   */
  public boolean wantsToApply(State state){
    return true;
  }
}
