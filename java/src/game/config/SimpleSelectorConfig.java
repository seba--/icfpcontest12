package game.config;

import game.State;
import game.StaticConfig;
import game.ai.Fitness;
import game.ai.Selector;
import game.fitness.AverageFitness;
import game.fitness.BeardFitness;
import game.fitness.BeardNextToLiftFitness;
import game.fitness.HoRockRemainingFitness;
import game.fitness.LiftReachable;
import game.fitness.ClosestLambdaFitness;
import game.fitness.RazorsAvailableFitness;
import game.fitness.ScoreFitness;
import game.fitness.StepCountFitness;
import game.selector.SimpleSelector;

/**
 * @author Sebastian Erdweg
 *
 */
public class SimpleSelectorConfig implements IDriverConfig {
  @Override
  public Selector strategySelector(StaticConfig sconfig, State initialState) {
    return new SimpleSelector(sconfig);
  }
  
  @Override
  public Fitness fitnessFunction(StaticConfig sconfig, State initialState) {
    return new AverageFitness(
        new LiftReachable(sconfig),
//        new AliveFitness(),
        new ScoreFitness(), 
        new StepCountFitness(sconfig), 
        new ClosestLambdaFitness(sconfig, initialState),
        new BeardNextToLiftFitness(),
        new BeardFitness(),
        new RazorsAvailableFitness(),
        new HoRockRemainingFitness(initialState)
    );
  }
}
