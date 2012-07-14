package benchmark;

import game.config.BigStepSelectorConfig;
import game.config.IDriverConfig;

/**
 * The default benchmark.
 * 
 * @author seba
 */
public class BigStepBenchmark extends Benchmark {

  @Override
  public String name() {
    return "default";
  }

  @Override
  public IDriverConfig config() {
    return new BigStepSelectorConfig();
  }

}
