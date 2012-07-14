/**
 * 
 */
package benchmark;

import game.ai.Driver;

/**
 * @author seba
 *
 */
public class SimpleBenchmarkMonitor implements IBenchmarkMonitor {
  public final Driver driver;
  
  public SimpleBenchmarkMonitor(Driver driver) {
    this.driver = driver;
  }

  @Override
  public IBenchmarkResult call() throws Exception {
    MonitorBenchmarkResult result = new MonitorBenchmarkResult();
    
    while (true) {
      IBenchmarkResult r = new SimpleBenchmarkResult(driver);
      result.results.add(r);
      
      if (driver.finished)
        return result;

      Thread.currentThread().wait(500);
    }
  }
}