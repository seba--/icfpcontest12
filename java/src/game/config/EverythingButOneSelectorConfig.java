package game.config;

import game.State;
import game.StaticConfig;
import game.ai.Fitness;
import game.ai.Selector;
import game.fitness.AverageFitness;
import game.fitness.ManhattanDirectedFitness;
import game.fitness.ScoreFitness;
import game.fitness.StepCountFitness;
import game.selector.EverythingButOneSelector;

/**
 * Config for everythingbutoneselector
 * @author Felix Rieger
 *
 */
public class EverythingButOneSelectorConfig implements IDriverConfig {

  int removeStrategy;
  
  public EverythingButOneSelectorConfig(int removeStrategy) {
    this.removeStrategy = removeStrategy;
  }
  
  @Override
  public Selector strategySelector(StaticConfig sconfig, State initialState) {
    return new EverythingButOneSelector(sconfig, removeStrategy);
   }

  @Override
  public Fitness fitnessFunction(StaticConfig sconfig, State initialState) {
    return new AverageFitness(new ScoreFitness(), new StepCountFitness(), new ManhattanDirectedFitness(sconfig, initialState));
  }

  @Override
  public boolean simulateWindow() {
    return false;
  }

}
