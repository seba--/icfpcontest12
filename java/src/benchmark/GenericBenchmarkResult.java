package benchmark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author seba
 */
public class GenericBenchmarkResult implements IBenchmarkResult {
  public List<String> headers = new ArrayList<String>();
  public List<Object> data = new ArrayList<Object>();
  
  @Override
  public String asString() {
    StringBuilder sb = new StringBuilder();
    
    for (Iterator<Object> it = data.iterator(); it.hasNext(); ) {
      Object o = it.next();
      sb.append(o);
      if (it.hasNext())
        sb.append("\n");
    }
    
    return sb.toString();
  }
  
  @Override
  public IBenchmarkResult merge(List<IBenchmarkResult> other) {
    throw new UnsupportedOperationException("Cannot merge generic benchmark results.");
  }

  @Override
  public String columnHeadings() {
    // TODO Auto-generated method stub
    return null;
  }
}
