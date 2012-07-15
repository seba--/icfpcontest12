/**
 * 
 */
package game.config;

import game.State;
import game.StaticConfig;
import game.ai.Fitness;
import game.ai.Selector;
import game.fitness.AverageFitness;
import game.fitness.ManhattanDirectedFitness;
import game.fitness.ScoreFitness;
import game.fitness.StepCountFitness;
import game.selector.BigStepSelector;

/**
 * @author seba
 *
 */
public class BigStepSelectorConfig implements IDriverConfig {
  @Override
  public Selector strategySelector(StaticConfig sconfig, State initialState) {
    return new BigStepSelector(sconfig);
  }
  
  @Override
  public Fitness fitnessFunction(StaticConfig sconfig, State initialState) {
    return new AverageFitness(new ScoreFitness(), new StepCountFitness(), new ManhattanDirectedFitness(sconfig, initialState));
  }
}
