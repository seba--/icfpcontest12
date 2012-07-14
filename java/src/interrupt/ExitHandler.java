package interrupt;

import game.ai.Driver;

import java.util.HashMap;

public class ExitHandler extends Thread {
  private Driver driver;

  private static HashMap<Driver, ExitHandler> instances = new HashMap<Driver, ExitHandler>();

  private ExitHandler(Driver d) {
    this.driver = d;
  }
  
  private static ExitHandler create(Driver d) {
    ExitHandler h = instances.get(d);
    if (h == null) {
      h = new ExitHandler(d);
      instances.put(d, h);
    }
    return h;
  }
  
  public static void register (Driver d) {
    Runtime.getRuntime().addShutdownHook(ExitHandler.create(d));
  }

  public static void unregister(Driver d) {
    Runtime.getRuntime().removeShutdownHook(ExitHandler.create(d));
  }

  public void run() {
    System.out.println("interrupted");
    //TODO: print out result
    driver.printSolution();
  }
}
