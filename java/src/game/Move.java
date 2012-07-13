package game;

/**
 * Moves as specified in Section 2.2.
 * 
 * @author seba
 */
public enum Move {
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
      throw new IllegalStateException("Unknown move " + this);
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
      throw new IllegalStateException("Unknown move " + this);
    }
  }
  
  public static Move parse(String s) {
    if (s == null || s.isEmpty())
      return null;
    
    switch (s.charAt(0)) {
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
      throw new IllegalArgumentException("Cannot parse move " + s);
    }
  }
  
}
