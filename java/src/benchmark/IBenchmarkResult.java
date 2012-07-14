package benchmark;

import java.util.List;

/**
 * Collects the data of one benchmark run.
 * 
 * @author seba
 */
public interface IBenchmarkResult {
  /**
   * Returns string representation of this result.
   */
  public String asString();
  
  /**
   * Returns merged result of this and other.
   * May fail if this and other are instances of different subclasses of IBenchmarkResult.
   */
  public IBenchmarkResult merge(List<IBenchmarkResult> other);
}
