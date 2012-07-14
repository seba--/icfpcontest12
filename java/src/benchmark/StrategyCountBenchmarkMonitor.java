package benchmark;

import game.ai.Driver;
import game.ai.Strategy;

public class StrategyCountBenchmarkMonitor implements IBenchmarkMonitor {
  public final Driver driver;
  public final Strategy[] strategies;
  
  public StrategyCountBenchmarkMonitor(Driver driver, Strategy ... strategies) {
    this.driver = driver;
    this.strategies = strategies;
  }

  @Override
  public IBenchmarkResult call() throws Exception {
    MonitorBenchmarkResult result = new MonitorBenchmarkResult();
    
    while (true) {
      IBenchmarkResult r = new StrategyCountBenchmarkResult(driver);
      result.add(System.currentTimeMillis(), r);
      
      if (driver.finished)
        return result;

      synchronized (this) {
        this.wait(200);
      }
    }
  }
}