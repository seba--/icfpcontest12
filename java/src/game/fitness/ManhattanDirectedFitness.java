package game.fitness;

import game.State;
import game.StaticConfig;
import game.ai.Fitness;

import java.util.Set;

import util.MathUtil;

/**
 * Directed towards next lambda (if any), otherwise next lift.
 * 
 * @author seba
 *
 */
public class ManhattanDirectedFitness implements Fitness {

  private final StaticConfig sconfig;
  
  public ManhattanDirectedFitness(StaticConfig sconfig) {
    this.sconfig = sconfig;
  }
  
  @Override
  public int evaluate(State state) {
    int maxDistance = state.board.height + state.board.width;
    int minDistance;
    if (state.lambdaPositions.isEmpty())
      minDistance = MathUtil.distance(state.robotCol, state.robotRow, sconfig.liftx, sconfig.lifty);
//    else if (state.previousState != null && state.previousState.board.get(state.robotCol, state.robotRow) == Cell.Lambda) {
//      // if we just ate a lambda, that's great!
//      return 1000000;
//    }
    else {
      int lambda = state.nextLambda(state.robotCol, state.robotRow);
      minDistance = MathUtil.distanceToPos(state.robotCol, state.robotRow, lambda, state.board.height);
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
