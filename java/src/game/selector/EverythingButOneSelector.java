package game.selector;

import game.Command;
import game.State;
import game.StaticConfig;
import game.ai.Selector;
import game.ai.Strategy;
import game.strategy.ConstantStrategy;
import game.strategy.DiggingStrategy;
import game.strategy.SomeLambdaStrategy;
import game.strategy.ClosestManhattanLambda;
import game.strategy.ClosestManhattanLift;
import game.strategy.WallFollowingStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A selector that selects every strategy but one
 * @author Felix Rieger
 *
 */
public class EverythingButOneSelector implements Selector {
  public final List<Strategy> strategies = new ArrayList<Strategy>();
  
  public EverythingButOneSelector(StaticConfig sconfig, int removeStrategy) {
    // TODO: Make this better; probably store the available strategies in a central location, so we can just fetch them from there instead of hard-coding them here
    strategies.add(new SomeLambdaStrategy());
    strategies.add(new ClosestManhattanLift(sconfig));    
    strategies.add(new ClosestManhattanLambda(sconfig));  
    strategies.add(new DiggingStrategy());
    strategies.add(new WallFollowingStrategy());
    strategies.add(new ConstantStrategy(Command.Left));
    strategies.add(new ConstantStrategy(Command.Right));
    strategies.add(new ConstantStrategy(Command.Up));
    strategies.add(new ConstantStrategy(Command.Down));
    strategies.add(new ConstantStrategy(Command.Wait));
    
    strategies.remove(removeStrategy);
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