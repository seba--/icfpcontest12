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
public interface Strategy {
  /**
   * Returns a list of commands to be applied to this state.
   * 
   * <p>
   * Callers must not change this list!
   * 
   * @param state
   * @return
   */
  public List<Command> apply(State state);
}
