package benchmark;

import game.State;
import game.StaticConfig;
import game.ai.Driver;
import game.config.EverythingButOneSelectorConfig;
import game.config.IDriverConfig;
import game.selector.EverythingButOneSelector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * This benchmark will test strategies:
 * Use all strategies and remove one for each configuration
 * @author Felix Rieger
 *
 */
public class SimpleStrategyComparisonBenchmark extends Benchmark {

  public int removeStrategy;
  public String removeStrategyName = "";
  
  @Override
  public String name() {
    return "simpleStrategyComparison.strategyConfig_" + removeStrategy + "_" + removeStrategyName;
  }

  @Override
  public IDriverConfig config() {
    return new EverythingButOneSelectorConfig(removeStrategy);
  }
  
  public SimpleStrategyComparisonBenchmark(int removeStrategy, String removeStrategyName) {
    super();
    this.removeStrategy = removeStrategy;
    this.removeStrategyName = removeStrategyName;
  }
  
  public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, InterruptedException, ExecutionException {
    
    List<SimpleStrategyComparisonBenchmark> benchmarkList = new ArrayList<SimpleStrategyComparisonBenchmark>();
    for (int i = 0; i < 10; i++) {
      benchmarkList.add(new SimpleStrategyComparisonBenchmark(i, EverythingButOneSelector.getStrategyName(i)));
    }
   
    //List<List<IBenchmarkResult>> resultList = new ArrayList<List<IBenchmarkResult>>();  // list of benchmark results
    
    for (SimpleStrategyComparisonBenchmark benchmark : benchmarkList) {
      List<IBenchmarkResult> results = new ArrayList<IBenchmarkResult>();
      for (String arg : args) {
        results.addAll(benchmark.benchmarkFileTree(new File(arg), ""));
        if (new File(arg).isDirectory()) {
          String logFile =  "../logs/" + new File(arg).getName() + "." + benchmark.name() + "." + System.currentTimeMillis() + ".csv";
          benchmark.logResults(logFile, results);
        }
       // resultList.add(results);
      }
      
      benchmark.executor.shutdown();
    }
  }
  
  
  public int lifetime() {
    return 25;
  }
  
  /**
   * Selects and returns a monitor for driver.
   */
  public IBenchmarkMonitor makeMonitor(final Driver driver, String mapName) {
    return new SimpleBenchmarkMonitor(driver, mapName);
  }

  public IBenchmarkResult monitorDriver(StaticConfig sconfig, State state, String mapName) throws InterruptedException, ExecutionException {
    Driver driver = Driver.create(config(), sconfig, state, lifetime());
    
    Future<IBenchmarkResult> monitoringResult = executor.submit(makeMonitor(driver, mapName));
    driver.run();

    return monitoringResult.get();
  }


}
