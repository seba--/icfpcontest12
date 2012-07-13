package unittest;

import game.Board;
import game.Command;
import game.Ending;
import game.State;
import game.StaticConfig;
import game.stepper.SingleStepper;
import junit.framework.Assert;

import org.junit.Test;

public class TestSingleStepper {

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
  
  public static Command[] solution1FloodMap1() {
    Command L = Command.Left;
    Command R = Command.Right;
    Command U = Command.Up;
    Command D = Command.Down;

    Command[] cmds = new Command[] {
      L,L,L,L,
      D,D,D,
      R,R,R,
      D,D,R,R,
      U,L,U,
      L,L,L,U,
      R,R,R,R,R,R,R,
      D,D,D,D
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
  
  protected State runStepper(State st, Command[] cmds) {
    SingleStepper stepper = new SingleStepper();
    
    System.out.println(st.board);
    System.out.println();
    for (Command cmd : cmds) {
      st = stepper.step(st, cmd);
      System.out.println(st.board);
      System.out.println();
    }
    
    return st;
  }
  
  @Test
  public void testMap1() {
    System.out.println("--------------------------------------------------------------------------");
    System.out.println("testMap1");
    
    String map1 = TestBoard.map1();
    Board board = Board.parse(map1);
    State st = new State(new StaticConfig(0, 0), board, 0);
    st = runStepper(st, solution1Map1());
    
    Assert.assertEquals(Ending.Win, st.ending);
    Assert.assertEquals(3, st.collectedLambdas);
    Assert.assertEquals(0, st.lambdasLeft);
    Assert.assertEquals(212, st.score);
    Assert.assertEquals(0, st.robotCol);
    Assert.assertEquals(1, st.robotRow);
  }

  @Test
  public void testFloodMap1() {
    System.out.println("--------------------------------------------------------------------------");
    System.out.println("testFloodMap1");
    
    String map1 = TestBoard.floodMap1();
    State st = State.parse(map1);
    runStepper(st, solution1FloodMap1());
  }
}
