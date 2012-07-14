package game.selector;

import game.Command;
import game.State;
import game.ai.Selector;
import game.ai.Strategy;
import game.strategy.ConstantStrategy;
import game.strategy.NextLambdaStrategy;
import game.strategy.NextManhattanLambda;
import game.strategy.NextManhattanLift;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A simple strategy for selecting a strategy.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */

public class SimpleSelector implements Selector {
  public final List<Strategy> strategies = new ArrayList<Strategy>();
  
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
    } 
    
    for (Strategy strat : state.pendingStrategies) {
      
      if (strat.wantsToApply(state)) {
        if (strat.isUseOnce()) { //single use strategy, remove immediately
          state.pendingStrategies.remove(strat);
        } 
        return strat;
      }
        
      //here: strat does not want to be applied (anymore)
      state.pendingStrategies.remove(strat);
    }

    return null;
  }

  /* (non-Javadoc)
   * @see game.ai.Selector#prepareState(game.State)
   */
  @Override
  public void prepareState(State state) {
    state.pendingStrategies.addAll(strategies);
  }

  @Override
  public List<Strategy> getUsedStrategies() {
    return strategies;
  }
}