package benchmark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Pair;

/**
 * Collection of other benchmark results.
 * 
 * @author seba
 */
public class MonitorBenchmarkResult implements IBenchmarkResult {
  public final List<Pair<Long, IBenchmarkResult>> results;
  
  public MonitorBenchmarkResult() {
    this.results = new ArrayList<Pair<Long, IBenchmarkResult>>();
  }
  
  public void add(Long time, IBenchmarkResult result) {
    results.add(Pair.create(time, result));
  }

  @Override
  public String asString() {
    StringBuilder sb = new StringBuilder();
    
    for (Iterator<Pair<Long, IBenchmarkResult>> it = results.iterator(); it.hasNext(); ) {
      Pair<Long, IBenchmarkResult> p = it.next();
      sb.append(String.format("%13d, %s", p.a, p.b.asString()));
      if (it.hasNext())
        sb.append("\n");
    }
    
    return sb.toString();
  }
}
