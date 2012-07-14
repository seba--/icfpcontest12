package game;

/**
 * @author seba
 */
public class StaticConfig {
  
  public final int[] liftPositions;
  
  /*
   * for flooding
   */
  public final int floodingRate;
  public final int waterResistance;
  
  public StaticConfig(int[] liftPositions) {
    this(liftPositions, 0, 10);
  }
  
  public StaticConfig(int[] liftPositions, int floodingRate, int waterResistance) {
    this.liftPositions = liftPositions;
    this.floodingRate = floodingRate;
    this.waterResistance = waterResistance;
  }
}
