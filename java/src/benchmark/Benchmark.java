package benchmark;

import game.State;
import game.StaticConfig;
import game.ai.Driver;
import game.config.IDriverConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import util.FileCommands;
import util.Pair;

/**
 * A superclass for benchmarks.
 *
 * @author Tillmann Rendel
 * @author Sebastian Erdweg
 */
public abstract class Benchmark {
  static int benchmarkTimeout = 25;
  
  public Benchmark() {
  }
  
  /**
   * A default main method. Can be used for any subclass of Benchmark, too.
   * Calls Benchmark.benchmark().
   * 
   * @throws ClassNotFoundException 
   * @throws IllegalAccessException 
   * @throws InstantiationException 
   * @throws IOException 
   * @throws ExecutionException 
   * @throws InterruptedException 
   */
  public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, InterruptedException, ExecutionException {
    String mainClassName = System.getProperty("sun.java.command").split(" ")[0];
    if (mainClassName.equals("benchmark.Benchmark"))
      throw new IllegalArgumentException("You need to call 'main' on concrete subclasses of benchmark.Benchmark.");
    
    Class<?> mainClass = Class.forName(mainClassName);
    
    Benchmark benchmark = (Benchmark) mainClass.newInstance();
    
    List<IBenchmarkResult> results = new ArrayList<IBenchmarkResult>();
    int argNum = 0;
    for (String arg : args) {
      if (argNum == 0) {
        if (arg.equals("--timeout")) {
          argNum = 1;
          continue;
        } else {
          argNum = 2;
        }
      } else if (argNum == 1) {
        benchmarkTimeout = Integer.parseInt(arg);
        argNum = 2;
        continue;
      }
      results.addAll(benchmark.benchmarkFileTree(new File(arg), ""));
      if (new File(arg).isDirectory()) {
        String aggregateLogFile =  "../logs/" + new File(arg).getName() + "." + benchmark.name() + ".agg." + System.currentTimeMillis() + ".csv";
        String rawLogFile =  "../logs/" + new File(arg).getName() + "." + benchmark.name() + ".raw." + System.currentTimeMillis() + ".csv";
        benchmark.logResults(aggregateLogFile, rawLogFile, results);
      }
    }
    
    benchmark.executor.shutdown();
  }
  
  /**
   * Returns name of this benchmark. Must be unique for each subclass.
   */
  public abstract String name();
  
  /**
   * Returns the driver configuration to be used in this benchmark.
   */
  public abstract IDriverConfig config();

  /**
   * Time per file in seconds.
   */
  public int lifetime() {
    return benchmarkTimeout;
  }
  
  /**
   * Selects and returns a monitor for driver.
   */
  public IBenchmarkMonitor makeMonitor(final Driver driver, String mapName) {
    return new SimpleBenchmarkMonitor(driver, mapName);
  }
  
  
  protected final ExecutorService executor = Executors.newSingleThreadExecutor();
  
  public IBenchmarkResult monitorDriver(StaticConfig sconfig, State state, String mapName) throws InterruptedException, ExecutionException {
    Driver driver = Driver.create(mapName, config(), sconfig, state, lifetime());
    
    Future<IBenchmarkResult> monitoringResult = executor.submit(makeMonitor(driver, mapName));
    driver.run();

    return monitoringResult.get();
  }
  
  /**
   * Performs benchmark on file and directories.
   * @throws IOException 
   * @throws ExecutionException 
   * @throws InterruptedException 
   */
  public List<IBenchmarkResult> benchmarkFileTree(File file, String path) throws IOException, InterruptedException, ExecutionException {
    if (file.isFile()) {
      Pair<StaticConfig, State> p = State.parse(FileCommands.readFileAsString(file.getAbsolutePath()));
      IBenchmarkResult result = monitorDriver(p.a, p.b, FileCommands.dropExtension(file.getName()));
      String logFile =  "../logs/" + path + "/" + FileCommands.dropExtension(file.getName()) + "." + name() + "." + System.currentTimeMillis() + ".csv";
      logResult(logFile, result);
      return Collections.singletonList(result);
    }
    else if (file.isDirectory()) {
      List<IBenchmarkResult> results = new ArrayList<IBenchmarkResult>();
      for (File subFile : file.listFiles())
        results.addAll(benchmarkFileTree(subFile, path.isEmpty() ? file.getName() : path + "/" + file.getName()));
      return results;
    }
    else 
      throw new IllegalArgumentException("Unknown file type " + file.getAbsolutePath());
  }
  
  public void logResult(String logFile, IBenchmarkResult result) throws IOException {
    FileCommands.writeToFile(logFile, result.columnHeadings() + "\n" + result.asString() + "\n");
  }
  
  public void logResults(String aggregateLogFile, String rawLogFile, List<IBenchmarkResult> results) throws IOException {
    if (results.isEmpty())
      return;
    
    StringBuilder sb = new StringBuilder();
    for (IBenchmarkResult r : results) {
      sb.append(r.asString() + "\n");
    }
    
    IBenchmarkResult aggregate = results.get(0).merge(results);
    FileCommands.writeToFile(aggregateLogFile, aggregate.columnHeadings() + "\n" + aggregate.asString() + "\n");
    FileCommands.writeToFile(rawLogFile, results.get(0).columnHeadings() + "\n" +  sb.toString() + "\n");
  }
}
