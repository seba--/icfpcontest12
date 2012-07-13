package game;

/**
 * @author seba
 */
public class StaticConfig {
  
  /*
   * for flooding
   */
  public final int floodingRate;
  public final int waterResistance;
  
  public StaticConfig() {
    this(0, 10);
  }
  
  public StaticConfig(int floodingRate, int waterResistance) {
    this.floodingRate = floodingRate;
    this.waterResistance = waterResistance;
  }
}
