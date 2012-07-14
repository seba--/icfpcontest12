package benchmark;

/**
 * Collects the data of one benchmark run.
 * 
 * @author seba
 */
public interface IBenchmarkResult {
  public String asString();
  public String columnHeadings();
}
