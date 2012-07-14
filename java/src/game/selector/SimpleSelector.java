package game.selector;

import game.Command;
import game.State;
import game.ai.Selector;
import game.ai.Strategy;
import game.strategy.ConstantStrategy;
import game.strategy.NextLambdaStrategy;
import game.strategy.NextManhattanLambda;
import game.strategy.NextManhattanLift;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple strategy for selecting a strategy.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */

public class SimpleSelector implements Selector {
  public final Set<Strategy> strategies = new HashSet<Strategy>();
  
  {
    strategies.add(new NextLambdaStrategy());
    // strategies.add(new NextManhattanLift());    
    // strategies.add(new NextManhattanLambda());    
    // strategies.add(new ConstantStrategy(Command.Left));
    // strategies.add(new ConstantStrategy(Command.Right));
    // strategies.add(new ConstantStrategy(Command.Up));
    // strategies.add(new ConstantStrategy(Command.Down));
    // strategies.add(new ConstantStrategy(Command.Wait));
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
      if (result.isUseOnce || !result.wantsToApply(state)) {
        state.pendingStrategies.remove(result);
      }
      
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