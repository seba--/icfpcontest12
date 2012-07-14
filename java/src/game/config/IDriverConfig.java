package game.config;

import game.State;
import game.StaticConfig;
import game.ai.Fitness;
import game.ai.Selector;

/**
 * Configuration for the Driver.
 * 
 * @author seba
 */
public interface IDriverConfig {
  /**
   * Returns the fitness function to be used in the driver.
   */
  public Selector strategySelector(StaticConfig sconfig, State initialState);
  
  /**
   * Returns the strategy selector to be used in the driver.
   */
  public Fitness fitnessFunction(StaticConfig sconfig, State initialState);

  /**
   * Set time in Seconds when SigInt will be send to process
   */
  
  public void timeOutFunction();
  
  /**
   * Return if simulate window should be shown 
   */
  
  public boolean simulateWindow();

}
