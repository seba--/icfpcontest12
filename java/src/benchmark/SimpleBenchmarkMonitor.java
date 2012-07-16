package benchmark;

import game.ai.Driver;

/**
 * Simple monitor that reports SimpleBenchmarkResult's.
 * 
 * @author Sebastian Erdweg
 *
 */
public class SimpleBenchmarkMonitor implements IBenchmarkMonitor {
  public final Driver driver;
  public final String mapName;
  
  public SimpleBenchmarkMonitor(Driver driver, String mapName) {
    this.driver = driver;
    this.mapName = mapName;
  }

  @Override
  public IBenchmarkResult call() throws Exception {
    MonitorBenchmarkResult result = new MonitorBenchmarkResult();
    
    while (true) {
      IBenchmarkResult r = new SimpleBenchmarkResult(driver, mapName);
      result.add(System.currentTimeMillis(), r);
      
      if (driver.finished)
        return result;

      synchronized (this) {
        this.wait(200);
      }
    }
  }
}