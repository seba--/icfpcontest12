package benchmark;

import game.config.BigStepSelectorConfig;
import game.config.IDriverConfig;

/**
 * The default benchmark.
 * 
 * @author Sebastian Erdweg
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
