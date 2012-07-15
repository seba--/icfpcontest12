package game.fitness;

import game.State;
import game.ai.Fitness;

public class BeardNextToLiftFitness implements Fitness {

  @Override
  public int evaluate(State s) {
    int liftRow = s.board.row(s.board.lift);
    int liftCol = s.board.col(s.board.lift);
    
    int smallNeighSearchSize = 1;
    int largeNeighSearchSize = 3;
    
    // bearcCounts[0] : 8-neighborhood; [1] : 24-neighborhood
    int beardCounts[] = new int[]{0,0};
    for (int i = liftRow-largeNeighSearchSize; i < liftRow+largeNeighSearchSize+1; i++) {
      for (int j = liftCol-largeNeighSearchSize; j < liftCol+largeNeighSearchSize+1; j++) {
        if (i>=liftRow-smallNeighSearchSize && i <= liftRow+smallNeighSearchSize &&
            j>=liftCol-smallNeighSearchSize && j >= liftRow+smallNeighSearchSize) {
            
          beardCounts[0]++;
        }
        beardCounts[1]++;
      }

    }
    
    if (beardCounts[0] != 0) { // large penalty for beard near lift
      return 0;
    }
 
    int smallSquare = ((smallNeighSearchSize+1)*2) * ((smallNeighSearchSize+1)*2);
    int largeSquare = ((largeNeighSearchSize+1)*2) * ((largeNeighSearchSize+1)*2);

    int maxBeardsInLargeNeigh = largeSquare - smallSquare - 1;
    
    // smaller penalty for beard inside large neighborhood
    return (int) ((maxBeardsInLargeNeigh - beardCounts[1]) * ((double) 1000000/maxBeardsInLargeNeigh));
    
  }

}
