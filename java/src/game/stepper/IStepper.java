package game.stepper;

import game.Command;
import game.State;

/**
 * All stepping is performed by mutating the state.
 * 
 * @author seba
 */
public interface IStepper {
  /**
   * Moves the robot. Implementations may deviate from the specifiaction in Section 2.2.
   */
  public void moveRobot(State st, Command cmd);

  /**
   * Updates the board. Implementations may deviate from the specifiaction in Section 2.3. 
   */
  public void updateBoard(State st);

  /**
   * Check ending. Implementations may deviate from the specifiaction in Section 2.4. 
   */
  public void checkEnding(State st);
  
  /**
   * Moves the robot AND updates the board AND checks ending conditions.
   */
  public void executeRound(State st, Command cmd);
}
