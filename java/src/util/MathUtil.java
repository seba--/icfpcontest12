package util;

import java.util.Random;

/**
 * @author Sebastian Erdweg
 */
public class MathUtil {
  /*
   * Random generator.
   */
  public final static Random RANDOM = new Random(110101010);

  public static int distance(int col1, int row1, int col2, int row2) {
    return Math.abs(col1 - col2) + Math.abs(row1 - row2);
  }
  
  public static int distanceToPos(int col1, int row1, int pos, int height) {
    return Math.abs(col1 - (pos / height)) + Math.abs(row1 - (pos % height));
  }
}
