package benchmark;

import game.State;
import game.StaticConfig;
import game.ai.Fitness;
import game.ai.Selector;
import game.config.IDriverConfig;
import game.config.SimpleSelectorConfig;
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
    return new SimpleSelectorConfig();
  }

}
