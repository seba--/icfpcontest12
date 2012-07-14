package game;

/**
 * Cells as defined in Section 2.1.
 * 
 * @author seba
 *
 */
public enum Cell {
  Wall, Rock, Lambda, Earth, FallingRock, Robot, Lift, Empty, RobotAndLift;
    
  public String toString() {
    return shortName();
  }
  
  public String shortName() {
    switch (this) {
    case Robot:
      return "R";
    case Wall:
      return "#";
    case Rock:
      return "*";
    case FallingRock:
      return "!";
    case Lambda:
      return "\\";
    case Lift:
      return "L";
    case Earth:
      return ".";
    case Empty:
      return " ";
    case RobotAndLift:
      return "r";
    default:
      throw new IllegalStateException();
    }
  }

  public String longName() {
    switch (this) {
    case Robot:
      return "Robot";
    case Wall:
      return "Wall";
    case Rock:
      return "Rock";
    case FallingRock:
      return "FallingRock";
    case Lambda:
      return "Lambda";
    case Lift:
      return "Lift";
    case Earth:
      return "Earth";
    case Empty:
      return "Empty";
    case RobotAndLift:
      return "RobotAndLift";
    default:
      throw new IllegalStateException();
    }
  }
  
  public static Cell parse(char c) {
    switch (c) {
    case 'R':
      return Robot;
    case '#':
      return Wall;
    case '*':
      return Rock;
    case '\\':
      return Lambda;
    case 'L':
      return Lift;
    case 'O':
      throw new IllegalArgumentException("Ascii input should not contain open lift.");
    case '.':
      return Earth;
    case ' ':
      return Empty;
    case 'r':
      return RobotAndLift;
    default:
      throw new IllegalStateException("Unkown cell " + (int)c);
    }
  }
}
