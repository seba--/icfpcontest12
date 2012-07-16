package game.fitness;

import java.util.Arrays;

import game.State;
import game.ai.Fitness;

/**
 * A scorer that returns the median fitness
 * @author Felix Rieger
 *
 */
public class MedianFitness implements Fitness {

  public Fitness[] fitnesses;
  public int[] scores;
  public MedianFitness(Fitness... fitnesses) {
    this.fitnesses = fitnesses;
  }
  
  @Override
  public int evaluate(State state) {
    scores = new int[fitnesses.length];
    for (int i = 0; i < fitnesses.length; i++) {
      scores[i] = fitnesses[i].evaluate(state);
    }
    
    Arrays.sort(scores);

    return scores[((scores.length&1) != 0)?scores.length/2:(scores.length/2)+1];
  }

}