package game;

/**
 * Moves as specified in Section 2.2.
 * 
 * @author seba
 */
public enum Move {
  L, R, U, D, W, A;

  public String toString() {
    return shortName();
  }
  
  public String shortName() {
    switch (this) {
    case L:
      return "L";
    case R:
      return "R";
    case U:
      return "U";
    case D:
      return "D";
    case W:
      return "W";
    case A:
      return "A";
    default:
      throw new IllegalStateException("Unknown move " + this);
    }
  }

  public String longName() {
    switch (this) {
    case L:
      return "Left";
    case R:
      return "Right";
    case U:
      return "Up";
    case D:
      return "Down";
    case W:
      return "Wait";
    case A:
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
      return L;
    case 'R':
      return R;
    case 'U':
      return U;
    case 'D':
      return D;
    case 'W':
      return W;
    case 'A':
      return A;
    default:
      throw new IllegalArgumentException("Cannot parse move " + s);
    }
  }
  
}
