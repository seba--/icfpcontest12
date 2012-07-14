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
import java.util.Iterator;
import java.util.List;

/**
 * A selector that selects every strategy but one
 * @author Felix Rieger
 *
 */
public class EverythingButOneSelector implements Selector {
  // XXX Everything here is rather hacky
  public final List<Strategy> strategies = new ArrayList<Strategy>();
  
  public EverythingButOneSelector(StaticConfig sconfig, int removeStrategy) {
    // TODO: Make this better; probably store the available strategies in a central location, so we can just fetch them from there instead of hard-coding them here
    strategies.addAll(buildStrategyList(sconfig));
    strategies.remove(removeStrategy);
  }
    
  public static List<Strategy> buildStrategyList(StaticConfig sconfig) {
    List<Strategy> strategyList = new ArrayList<Strategy>();
    strategyList.add(new SomeLambdaStrategy());
    strategyList.add(new ClosestManhattanLift(sconfig));    
    strategyList.add(new ClosestManhattanLambda(sconfig));  
    strategyList.add(new DiggingStrategy());
    strategyList.add(new WallFollowingStrategy());
    strategyList.add(new ConstantStrategy(Command.Left));
    strategyList.add(new ConstantStrategy(Command.Right));
    strategyList.add(new ConstantStrategy(Command.Up));
    strategyList.add(new ConstantStrategy(Command.Down));
    strategyList.add(new ConstantStrategy(Command.Wait));

    return strategyList;
  }
  
  public static String getStrategyName(int i) {
    // XXX when adding strategies, change this
    String[] strategyNames = {"SomeLambda", "ClosestManhattanLift", "ClosestManhattanLambda", "DiggingStrategy", "WallFollowingStrategy",
                              "ConstantStrategy_L", "ConstantStrategy_R", "ConstantStrategy_U", "ConstantStrategy_D", "ConstantStrategy_W"};
    return strategyNames[i];
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