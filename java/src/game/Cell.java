package game;

/**
 * Cells as defined in Section 2.1.
 * 
 * @author seba
 *
 */
public enum Cell {
  Wall, Rock, Lambda, Earth, FallingRock, Trampoline, Target, Beard, Razor, Robot, Lift, Empty, RobotAndLift;
    
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
      return "|";
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
    case Trampoline:
      return "T";
    case Target:
      return "t";
    case Beard:
      return "W";
    case Razor:
      return "!";
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
    case Trampoline:
      return "Trampoline";
    case Target:
      return "Target";
    case Beard:
      return "Beard";
    case Razor:
      return "Razor";
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
    case 'A':
      return Trampoline;
    case 'B':
      return Trampoline;
    case 'C':
      return Trampoline;
    case 'D':
      return Trampoline;
    case 'E':
      return Trampoline;
    case 'F':
      return Trampoline;
    case 'G':
      return Trampoline;
    case 'H':
      return Trampoline;
    case 'I':
      return Trampoline;
    case '1':
      return Target;
    case '2':
      return Target;
    case '3':
      return Target;
    case '4':
      return Target;
    case '5':
      return Target;
    case '6':
      return Target;
    case '7':
      return Target;
    case '8':
      return Target;
    case '9':
      return Target;
    case 'W':
      return Beard;
    case '!':
      return Razor;
    default:
      throw new IllegalStateException("Unkown cell " + (int)c);
    }
  }
}
