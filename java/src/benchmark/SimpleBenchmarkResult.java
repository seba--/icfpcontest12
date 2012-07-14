package benchmark;

import game.ai.Driver;

import java.util.List;

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
           driver.bestState != null ? driver.bestState.score : 0, 
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
    public String asString() {
      return String.format("%8d,%8d,%8d,%8d", iterations, bestScore, liveStates, deadStates);
    }
  
    @Override
    public IBenchmarkResult merge(List<IBenchmarkResult> results) {
      int iterationsSum = 0;
      int bestScoreSum = 0;
      int liveStatesSum = 0;
      int deadStatesSum = 0;
      
      for (IBenchmarkResult next : results) {
        if (next instanceof SimpleBenchmarkResult) {
          SimpleBenchmarkResult sbr = (SimpleBenchmarkResult) next;
          iterationsSum += sbr.iterations;
          bestScoreSum += sbr.bestScore;
          liveStatesSum += sbr.liveStates;
          deadStatesSum += sbr.deadStates;
        }
      }
      
      GenericBenchmarkResult result = new GenericBenchmarkResult();
      result.headers.add("iterations-sum");
      result.data.add(iterationsSum);
      result.headers.add("iterations-avg");
      result.data.add((double) iterationsSum / results.size());
      result.headers.add("bestScore-sum");
      result.data.add(bestScoreSum);
      result.headers.add("bestScore-avg");
      result.data.add((double) bestScoreSum / results.size());
      result.headers.add("liveStates-sum");
      result.data.add(liveStatesSum);
      result.headers.add("liveStates-avg");
      result.data.add((double) liveStatesSum / results.size());
      result.headers.add("deadStates-sum");
      result.data.add(deadStatesSum);
      result.headers.add("deadStates-avg");
      result.data.add((double) deadStatesSum / results.size());
      return result;
    }
  }