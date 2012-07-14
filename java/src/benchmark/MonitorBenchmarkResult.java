package benchmark;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection of other benchmark results.
 * 
 * @author seba
 */
public class MonitorBenchmarkResult implements IBenchmarkResult {
  public final List<IBenchmarkResult> results;
  
  public MonitorBenchmarkResult() {
    this.results = new ArrayList<IBenchmarkResult>();
  }

  @Override
  public String toFile(String file) {
    // TODO Auto-generated method stub
    return null;
  }
}
