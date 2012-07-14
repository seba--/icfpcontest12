package benchmark;

import game.ai.Driver;
import game.ai.Strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author seba
 */
public class SimpleBenchmarkResult implements IBenchmarkResult {
  
    public final int iterations;
    public final int bestScore;
    public final int liveStates;
    public final int deadStates;
    
    public final Map<Strategy,Integer> strategyApplications;

    public SimpleBenchmarkResult(Driver driver) {
      this(driver.iterations, 
           driver.bestState != null ? driver.bestState.score : 0, 
           driver.liveStates.size(), 
           driver.seenStates.size() - driver.liveStates.size(),
           countUsedStrategies(driver));
    }
    
    public SimpleBenchmarkResult(int iterations, int bestScore, int liveStates, int deadStates, Map<Strategy, Integer> strategyApplications) {
      this.iterations = iterations;
      this.bestScore = bestScore;
      this.liveStates = liveStates;
      this.deadStates = deadStates;
      this.strategyApplications = strategyApplications;
    }
    
    public static Map<Strategy, Integer> countUsedStrategies(Driver driver) {
      Map<Strategy, Integer> tmp = new HashMap<Strategy, Integer>();
      
      for (Strategy s : driver.strategySelector.getUsedStrategies()) {
        tmp.put(s, s.applicationCount);
      }
      
      return tmp;
    }

    @Override
    public String asString() {
      return String.format("%8d,%9d,%8d,%8d", iterations, bestScore, liveStates, deadStates);
    }

    @Override
    public String columnHeadings() {
      return "iter.    ,bestScore,live    ,dead";
    }
  
  }