package benchmark;

import game.ai.Driver;

/**
 * 
 * @author seba
 */
public class SimpleBenchmarkResult implements IBenchmarkResult {
  
    public final int iterations;
    public final int bestScore;
    public final int liveStates;
    public final int deadStates;

    public SimpleBenchmarkResult(Driver driver) {
      this(driver.iterations, 
           driver.bestState.score, 
           driver.liveStates.size(), 
           driver.seenStates.size() - driver.liveStates.size());
    }
    
    public SimpleBenchmarkResult(int iterations, int bestScore, int liveStates, int deadStates) {
      this.iterations = iterations;
      this.bestScore = bestScore;
      this.liveStates = liveStates;
      this.deadStates = deadStates;
    }

    @Override
    public String toFile(String file) {
      // TODO Auto-generated method stub
      return null;
    }
  
  }