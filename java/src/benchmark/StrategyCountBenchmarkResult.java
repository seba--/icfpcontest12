package benchmark;

import game.ai.Driver;
import game.ai.Strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Felix Rieger
 * Number of executions per strategy
 *
 */
public class StrategyCountBenchmarkResult implements IBenchmarkResult {
  public final Map<Strategy,Integer> strategyApplications;
  public final List<Strategy> usedStrategies;
  
  public StrategyCountBenchmarkResult(Driver driver) {
    this(countUsedStrategies(driver.strategySelector.getUsedStrategies()), driver.strategySelector.getUsedStrategies());
  }
  
  public StrategyCountBenchmarkResult(Map<Strategy, Integer> strategyApplications, List<Strategy> usedStrategies) {
    this.strategyApplications = strategyApplications;
    this.usedStrategies = usedStrategies;
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
    StringBuilder sb = new StringBuilder();
    for (Strategy s : usedStrategies) {
      sb.append(String.format("%20d,", strategyApplications.get(s)));
    }
    return sb.toString();
  }

  @Override
  public String columnHeadings() {
    StringBuilder sb = new StringBuilder();
    for (Strategy s : usedStrategies) {
      sb.append((s + "                    ").substring(0, 20)  + ",");
    }
    return sb.toString();
  }

  @Override
  public GenericBenchmarkResult merge(List<IBenchmarkResult> other) {
    // TODO Auto-generated method stub
    return null;
  }
  
  
}
