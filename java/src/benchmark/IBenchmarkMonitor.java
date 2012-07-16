package benchmark;

import java.util.concurrent.Callable;

/**
 * Runnable monitor.
 * 
 * @author Sebastian Erdweg
 */
public interface IBenchmarkMonitor extends Callable<IBenchmarkResult> {

}
