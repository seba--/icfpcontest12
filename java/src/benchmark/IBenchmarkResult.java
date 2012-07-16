package benchmark;

import java.util.List;

/**
 * Collects the data of one benchmark run.
 * 
 * @author Sebastian Erdweg
 */
public interface IBenchmarkResult {
  /**
   * Returns string representation of this result.
   */
  public String asString();
  public String columnHeadings();
  
  /**
   * Returns merged result of this and other.
   * May fail if this and other are instances of different subclasses of IBenchmarkResult.
   */
  public GenericBenchmarkResult merge(List<IBenchmarkResult> other);
}
