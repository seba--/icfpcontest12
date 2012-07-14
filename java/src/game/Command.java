package game;

/**
 * Commands as specified in Section 2.2.
 * 
 * @author seba
 */
public enum Command {
  Left, Right, Up, Down, Wait, Abort;

  public String toString() {
    return shortName();
  }
  
  public String shortName() {
    switch (this) {
    case Left:
      return "L";
    case Right:
      return "R";
    case Up:
      return "U";
    case Down:
      return "D";
    case Wait:
      return "W";
    case Abort:
      return "A";
    default:
      throw new IllegalStateException("Unknown command " + this);
    }
  }

  public String longName() {
    switch (this) {
    case Left:
      return "Left";
    case Right:
      return "Right";
    case Up:
      return "Up";
    case Down:
      return "Down";
    case Wait:
      return "Wait";
    case Abort:
      return "Abort";
    default:
      throw new IllegalStateException("Unknown command " + this);
    }
  }
  
  public static Command parse(char c) {
    switch (c) {
    case 'L':
      return Left;
    case 'R':
      return Right;
    case 'U':
      return Up;
    case 'D':
      return Down;
    case 'W':
      return Wait;
    case 'A':
      return Abort;
    default:
      throw new IllegalArgumentException("Cannot parse command " + c);
    }
  }
  
  public static Command[] ARRAY = new Command[]{};
}
