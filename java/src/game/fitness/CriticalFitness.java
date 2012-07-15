package game.fitness;

import game.State;
import game.ai.Fitness;

/**
 * Increases the importance of the wrapped Fitness.
 * 
 * @author seba
 */
public class CriticalFitness implements Fitness {

  private final Fitness fitness;
  
  public CriticalFitness(Fitness fitness) {
    this.fitness = fitness;
  }
  
  @Override
  public int evaluate(State state) {
    
    return 0;
  }

}
