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
  
  public IBenchmarkResult initialResult() {
    return results.isEmpty() ? null : results.get(0).b;
  }
  
  public IBenchmarkResult finalResult() {
    return results.isEmpty() ? null : results.get(results.size() - 1).b;
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

  @Override
  public IBenchmarkResult merge(List<IBenchmarkResult> others) {
    if (others.isEmpty())
      return new GenericBenchmarkResult();
    
    List<IBenchmarkResult> initials = new ArrayList<IBenchmarkResult>();
    List<IBenchmarkResult> finals = new ArrayList<IBenchmarkResult>();
    
    for (IBenchmarkResult other : others)
      if (other instanceof MonitorBenchmarkResult) {
        MonitorBenchmarkResult mbr = (MonitorBenchmarkResult) other;
        initials.add(mbr.initialResult());
        finals.add(mbr.finalResult());
      }

    IBenchmarkResult initialAggregate = initials.get(0).merge(initials);
    IBenchmarkResult finalAggregate = finals.get(0).merge(finals);
    
    GenericBenchmarkResult result = new GenericBenchmarkResult();
    result.headers.add("initial");
    result.data.add(initialAggregate);
    result.headers.add("final");
    result.data.add(finalAggregate);
    return result;
  }
}
