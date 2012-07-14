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
    Class<?> mainClass = Class.forName(mainClassName);
    
    Benchmark benchmark = (Benchmark) mainClass.newInstance();
    
    List<IBenchmarkResult> results = new ArrayList<IBenchmarkResult>();
    for (String arg : args)
      results.addAll(benchmark.benchmarkFileTree(new File(arg), ""));
    
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
   * Selects and returns a monitor for driver.
   */
  public IBenchmarkMonitor makeMonitor(final Driver driver) {
    return new SimpleBenchmarkMonitor(driver);
  }
  
  
  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  
  public IBenchmarkResult monitorDriver(StaticConfig sconfig, State state) throws InterruptedException, ExecutionException {
    Driver driver = Driver.create(config(), sconfig, state);
    
    Future<IBenchmarkResult> monitoringResult = executor.submit(makeMonitor(driver));
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
      IBenchmarkResult result = monitorDriver(p.a, p.b);
      String logFile =  "../logs/" + path + "/" + FileCommands.dropExtension(file.getName()) + "-" + name() + "-" + System.currentTimeMillis();
      FileCommands.writeToFile(logFile, result.asString() + "\n");
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
}
