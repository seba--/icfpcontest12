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
   * Determine whether this strategy can only be used once for a given state
   * or several times, yielding different results each time.
   */
  public static boolean isUseOnce = true;
  
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
   * If this is a strategy that can be applied multiple times
   * this indicates whether this strategy (still) wants to be applied
   * to a given state. Subclasses that set {@item isUseOnce} to false
   * should also override this method. 
   */
  public boolean wantsToApply(State state){
    return false;
  }
}
