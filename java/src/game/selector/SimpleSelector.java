package game.selector;

import game.State;
import game.StaticConfig;
import game.ai.Selector;
import game.ai.Strategy;
import game.strategy.ClosestManhattanLambda;
import game.strategy.ClosestManhattanLift;
import game.strategy.DownStrategy;
import game.strategy.LeftStrategy;
import game.strategy.RightStrategy;
import game.strategy.SomeLambdaStrategy;
import game.strategy.UpStrategy;
import game.strategy.WaitStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A simple strategy for selecting a strategy.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */

public class SimpleSelector implements Selector {
  public final List<Strategy> strategies = new ArrayList<Strategy>();
  
  public SimpleSelector(StaticConfig sconfig) {
    strategies.add(new SomeLambdaStrategy());
    strategies.add(new ClosestManhattanLift(sconfig));    
    strategies.add(new ClosestManhattanLambda(sconfig));  
//    strategies.add(new DiggingStrategy());
    strategies.add(new LeftStrategy());
    strategies.add(new RightStrategy());
    strategies.add(new UpStrategy());
    strategies.add(new DownStrategy());
    strategies.add(new WaitStrategy());
    
  }
  
  public SimpleSelector(StaticConfig sconfig, Strategy ... strategies) {
    this.strategies.addAll(Arrays.asList(strategies));
  }
  
  /* (non-Javadoc)
   * @see game.ai.Selector#selectStrategy(game.State)
   */
  @Override
  public Strategy selectStrategy(State state) {
    if (state.pendingStrategies.isEmpty()) {
      return null;
    } 
    
    for (Iterator<Strategy> it = state.pendingStrategies.iterator(); it.hasNext();) {
      Strategy strat = it.next();
      if (strat.wantsToApply(state)) {
        if (strat.isUseOnce()) { //single use strategy, remove immediately
          it.remove();
        } 
        return strat;
      }
        
      //here: strat does not want to be applied (anymore)
      it.remove();
    }

    return null;
  }

  /* (non-Javadoc)
   * @see game.ai.Selector#prepareState(game.State)
   */
  @Override
  public void prepareState(State state) {
    state.pendingStrategies = new ArrayList<Strategy>(strategies);
  }

  @Override
  public List<Strategy> getUsedStrategies() {
    return strategies;
  }
}