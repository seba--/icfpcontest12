package interrupt;

public class ExitHandler extends Thread {
//  private Driver driver;
//  

  private ExitHandler() {
    
  }
  
//  private ExitHandler(Driver d) {
//    this.driver = d;
//  }
  
  public static void register (/* Driver d */) {
    Runtime.getRuntime().addShutdownHook(new ExitHandler(/* d */));
  }
  
  public void run() {
    System.out.println("iterrupted");
    //todo: print out drivers best solution
  }
  
}
