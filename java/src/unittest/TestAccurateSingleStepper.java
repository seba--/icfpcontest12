package unittest;

import junit.framework.Assert;
import game.Board;
import game.Command;
import game.Ending;
import game.State;
import game.stepper.AccurateSingleStepper;
import game.stepper.IStepper;

import org.junit.Test;

public class TestAccurateSingleStepper {

  public static Command[] solution1Map1() {
    Command L = Command.Left;
    Command R = Command.Right;
    Command U = Command.Up;
    Command D = Command.Down;

    Command[] cmds = new Command[] {
      D,L,R,D,D,U,U,L,L,L,D,D,L
    };
    
    return cmds;
  }
  
  public static Command[] solution1MapX() {
    Command L = Command.Left;
    Command R = Command.Right;
    Command U = Command.Up;
    Command D = Command.Down;
    
    Command[] cmds = new Command[] {
      L,D,D,D,
      R,R,R,R,
      D,D,L,L,
      L,L,L,D,
      R,R,U,R,
      R,U,U,R,
      R
    };
    
    return cmds;
  }
  
  @Test
  public void testMap1() {
    String map1 = TestBoard.map1();
    Board board = Board.parse(map1);
    State st = new State(board);
    
    IStepper stepper = new AccurateSingleStepper();
    
    for (Command cmd : solution1Map1()) {
      stepper.executeRound(st, cmd);
    }
    
    Assert.assertEquals(Ending.Win, st.ending);
  }

}
