package game.fitness;

import game.Cell;
import game.State;
import game.ai.Fitness;
import game.util.MapUtil;

/**
 * Fitness based on lamda density
 * @author Felix Rieger
 *
 */
public class LambdaDensityFitness implements Fitness {

  @Override
  public int evaluate(State state) {
    int[] integralBoard = MapUtil.computeIntegralBoard(state.board, Cell.Lambda);
    float[] densityMap = MapUtil.getDensityMap(5, state.board.width, integralBoard);
    float res = densityMap[state.robotCol + state.board.width*state.robotRow];
    return (int) (res*1000000);
  }

}
