package game.fitness;

import game.State;
import game.StaticConfig;
import game.ai.Fitness;

import java.util.Set;

import util.MathUtil;

/**
 * Directed towards next lambda (if any), otherwise next lift.
 * Use the original lambda positions (from the intial state). If the
 * lambdas are gone, scoring will take care of that.
 * 
 * @author Sebastian Erdweg
 *
 */
public class ClosestLambdaFitness implements Fitness {

  private final StaticConfig sconfig;
  
  int[] nextLambda;
  
  public ClosestLambdaFitness(StaticConfig sconfig, State state) {
    this.sconfig = sconfig;
    this.nextLambda = state.getNextLambda();
  }
  
  @Override
  public int evaluate(State state) {
    int maxDistance = state.board.height + state.board.width;
    int minDistance;
    if (state.lambdaPositions.isEmpty())
      return 1000000;
    else {
      int bestLambda = nextLambda[state.board.position(state.robotCol, state.robotRow)];
      minDistance = MathUtil.distanceToPos(state.robotCol, state.robotRow, bestLambda, state.board.height);
    }
    
    int r = (int) ((1 - (double) minDistance / maxDistance) * 1000000);
    return r;
  }

  public static int minDistance(int col, int row, int[] positions) {
    int minDistance = Integer.MAX_VALUE;
    
    for (int pos : positions) {
      int c = pos / 2;
      int r = pos % 2;
      int distance = MathUtil.distance(col, row, c, r);
      minDistance = Math.min(minDistance, distance);
    }

    return minDistance;
  }

  
  public static int minDistance(int col, int row, Set<Integer> positions) {
    int minDistance = Integer.MAX_VALUE;
    
    for (int pos : positions) {
      int c = pos / 2;
      int r = pos % 2;
      int distance = MathUtil.distance(col, row, c, r);
      minDistance = Math.min(minDistance, distance);
    }

    return minDistance;
  }

}
