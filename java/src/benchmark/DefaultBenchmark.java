package benchmark;

import game.State;
import game.StaticConfig;
import game.ai.Fitness;
import game.ai.Selector;
import game.config.IDriverConfig;
import game.fitness.AverageFitness;
import game.fitness.ManhattanDirectedFitness;
import game.fitness.ScoreFitness;
import game.fitness.StepCountFitness;
import game.selector.SimpleSelector;

/**
 * The default benchmark.
 * 
 * @author seba
 */
public class DefaultBenchmark extends Benchmark {

  @Override
  public String name() {
    return "default";
  }

  @Override
  public IDriverConfig config() {
    return new IDriverConfig() {
      
      @Override
      public Selector strategySelector(StaticConfig sconfig, State initialState) {
        return new SimpleSelector(sconfig);
      }
      
      @Override
      public Fitness fitnessFunction(StaticConfig sconfig, State initialState) {
        return new AverageFitness(new ScoreFitness(), new StepCountFitness(), new ManhattanDirectedFitness(sconfig));
      }
    };
  }

}
