package game.log;

public class Log {
  private static final boolean LOGGIN = true;
  
  public static void println() {
    if (LOGGIN)
      System.out.println();
  }
  
  public static void println(Object o) {
    if (LOGGIN)
      System.out.println(o);
  }
  
  public static void append(CharSequence o) {
    if (LOGGIN)
      System.out.append(o);
  }
  
  public static void flush() {
    if (LOGGIN)
      System.out.flush();
  }
  
  public static void printf(String format, Object... args) {
    if (LOGGIN)
      System.out.printf(format, args);
  }
  
  
}
