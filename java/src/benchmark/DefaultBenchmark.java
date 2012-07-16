package benchmark;

import game.config.IDriverConfig;
import game.config.SimpleSelectorConfig;

/**
 * The default benchmark.
 * 
 * @author Sebastian Erdweg
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
