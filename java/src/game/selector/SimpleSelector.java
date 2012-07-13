package game.selector;

import game.Command;
import game.State;
import game.ai.Selector;
import game.ai.Strategy;
import game.strategy.ConstantStrategy;

import java.util.Set;

/**
 * A simple strategy for selecting a strategy.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */

public class SimpleSelector implements Selector {
  public Set<Strategy> strategies;
  
  {
    strategies.add(new ConstantStrategy(Command.Left));
    strategies.add(new ConstantStrategy(Command.Right));
    strategies.add(new ConstantStrategy(Command.Up));
    strategies.add(new ConstantStrategy(Command.Down));
    strategies.add(new ConstantStrategy(Command.Wait));
  }
  
  /* (non-Javadoc)
   * @see game.ai.Selector#selectStrategy(game.State)
   */
  @Override
  public Strategy selectStrategy(State state) {
    if (state.pendingStrategies.isEmpty()) {
      return null;
    } else {
      Strategy result = state.pendingStrategies.iterator().next();
      state.pendingStrategies.remove(result);
      return result;
    }
  }

  /* (non-Javadoc)
   * @see game.ai.Selector#prepareState(game.State)
   */
  @Override
  public void prepareState(State state) {
    state.pendingStrategies.addAll(strategies);
  }
}