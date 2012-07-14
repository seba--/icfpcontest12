package unittest;

import game.Board;
import game.Cell;
import game.Command;
import game.Ending;
import game.State;
import game.StaticConfig;
import game.stepper.SingleStepper;
import junit.framework.Assert;

import org.junit.Test;

public class TestSingleStepper {

  static Command L = Command.Left;
  static Command R = Command.Right;
  static Command U = Command.Up;
  static Command D = Command.Down;

  public static Command[] solution1Map1() {
    Command[] cmds = new Command[] {
      L,R,D,D,D,U,U,L,L,L,D,D,L
    };
    
    return cmds;
  }
  
  public static Command[] solution2Map1() {
    Command[] cmds = new Command[] {
      L
    };
    
    return cmds;
  }
  
  
  public static Command[] drowning1FloodMap1() {
    Command[] cmds = new Command[] {
      L,L,L,L,
      D,D,D,
      R,R,R,
      D,D,R,R,
      U,U,
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
      System.out.println(st);
      System.out.println();
      if (st.ending != Ending.None)
        break;
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
  public void testMap1variant() {
    System.out.println("--------------------------------------------------------------------------");
    System.out.println("testMap1variant");
    
    String map1 = TestBoard.map1();
    Board board = Board.parse(map1);
    State st = new State(new StaticConfig(0, 0), board, 0);
    st = runStepper(st, solution2Map1());
    
    Assert.assertEquals(3, st.robotCol);
    Assert.assertEquals(4, st.robotRow);
    Assert.assertEquals(Cell.Robot, st.board.get(3, 4));
    Assert.assertEquals(Cell.FallingRock, st.board.get(2, 3));
  }

  @Test
  public void testFloodMap1() {
    System.out.println("--------------------------------------------------------------------------");
    System.out.println("testFloodMap1");
    
    String map1 = TestBoard.floodMap1();
    State st = State.parse(map1);
    st = runStepper(st, drowning1FloodMap1());
    
    Assert.assertEquals(Ending.LoseWater, st.ending);
  }
}
