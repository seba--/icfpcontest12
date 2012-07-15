package benchmark;

import game.ai.Driver;
import game.ai.Strategy;
import game.fitness.LiftReachable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author seba
 */
public class SimpleBenchmarkResult implements IBenchmarkResult {
  
    public final int iterations;
    public final int bestScore;
    public final int liveStates;
    public final int deadStates;
    public final Map<Strategy, Integer> appliedStrategies;
    public final int liftReachable;
    public final int remainingLambdas;
    public final String mapName;
    
    public SimpleBenchmarkResult(Driver driver, String mapName) {
      this(driver.iterations, 
           driver.bestState != null ? driver.bestState.score : 0, 
           driver.liveStates.size(), 
           driver.seenStates.size() - driver.liveStates.size(),
           countUsedStrategies(driver.strategySelector.getUsedStrategies()),
           driver.bestState != null ? 
                             (LiftReachable.liftReachable(driver.bestState.board, driver.sconfig.liftx, driver.sconfig.lifty) > 0 ? 1 : 0) : 
                             (LiftReachable.liftReachable(driver.initialState.board, driver.sconfig.liftx, driver.sconfig.lifty) > 0 ? 1 : 0),
           driver.bestState != null ? driver.initialState.lambdaPositions.size() - driver.bestState.collectedLambdas : driver.initialState.lambdaPositions.size(),
           mapName);
      
    }
    
    public SimpleBenchmarkResult(int iterations, int bestScore, int liveStates, int deadStates, Map<Strategy, Integer> appliedStrategies, int liftReachable, int remainingLambdas, String mapName) {
      this.iterations = iterations;
      this.bestScore = bestScore;
      this.liveStates = liveStates;
      this.deadStates = deadStates;
      this.appliedStrategies = appliedStrategies;
      this.liftReachable = liftReachable;
      this.remainingLambdas = remainingLambdas;
      this.mapName = mapName;
    }
    
    public static Map<Strategy, Integer> countUsedStrategies(List<Strategy> usedStrategies) {
      Map<Strategy, Integer> tmp = new HashMap<Strategy, Integer>();
      
      for (Strategy s : usedStrategies) {
        tmp.put(s, s.applicationCount);
      }
      
      return tmp;
    }


    @Override
    public String asString() {
      return String.format("%8d,%9d,%8d,%8d,%10d,%13d,%s", iterations, bestScore, liveStates, deadStates, remainingLambdas, liftReachable, mapName);
    }

    @Override
    public String columnHeadings() {
      return "iter.    ,bestScore,live    ,dead    ,lambdaLeft,liftReachable,mapName";
    }
  
    @Override
    public GenericBenchmarkResult merge(List<IBenchmarkResult> results) {
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
      
      int remainingLambdasMax = 0;
      String remainingLambdasMaxMap = "";
      
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
      result.headers.add("liftReachable-avg");
      result.data.add((double) liftReachable / results.size());
      result.headers.add("remainingLamdas-avg");
      result.data.add((double) remainingLambdas / results.size());
      result.headers.add("remainingLambdas-max");
      for (IBenchmarkResult r : results) {
        SimpleBenchmarkResult res = (SimpleBenchmarkResult) r;
        if (res.remainingLambdas > remainingLambdasMax) {
          remainingLambdasMax = res.remainingLambdas;
          remainingLambdasMaxMap = res.mapName;
        }
      }
      result.data.add(remainingLambdasMax);
      result.headers.add("remainingLambdasMax-map");
      result.data.add(remainingLambdasMaxMap);
      Iterator<Entry<Strategy, Integer>> strategiesIterator = appliedStrategies.entrySet().iterator();
      while(strategiesIterator.hasNext()) {
        Entry<Strategy, Integer> e = strategiesIterator.next();
        result.headers.add(e.getKey().toString());
        result.data.add((double) e.getValue() / results.size());
      }
      return result;
    }
  }