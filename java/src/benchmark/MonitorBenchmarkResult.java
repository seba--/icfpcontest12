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
  public String columnHeadings() {
    StringBuilder sb = new StringBuilder();

    sb.append("time         ,");
    sb.append(results.get(0).b.columnHeadings());
    return sb.toString();
  }
  
  public GenericBenchmarkResult merge(List<IBenchmarkResult> others) {
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

    GenericBenchmarkResult initialAggregate = initials.get(0).merge(initials);
    GenericBenchmarkResult finalAggregate = finals.get(0).merge(finals);
    
    GenericBenchmarkResult result = new GenericBenchmarkResult();
    for (int i = 0; i < initialAggregate.headers.size(); i++) {
      result.headers.add("initial" + "-" + initialAggregate.headers.get(i));
      result.data.add(initialAggregate.data.get(i));
    }
    for (int i = 0; i < finalAggregate.headers.size(); i++) {
      result.headers.add("final" + "-" + finalAggregate.headers.get(i));
      result.data.add(finalAggregate.data.get(i));
      
    }
    return result;
  }
}
