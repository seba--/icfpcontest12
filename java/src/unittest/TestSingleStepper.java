package unittest;

import game.Board;
import game.Cell;
import game.Command;
import game.Ending;
import game.State;
import game.StaticConfig;
import game.log.Log;
import game.stepper.SingleStepper;
import junit.framework.Assert;

import org.junit.Test;

import util.Pair;

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
  
  protected State runStepper(StaticConfig sconfig, State st, Command... cmds) {
    SingleStepper stepper = new SingleStepper(sconfig);
    
    Log.println(st.board);
    Log.println();
    for (Command cmd : cmds) {
      st = stepper.step(st, cmd);
      Log.println(st);
      Log.println();
      if (st.ending != Ending.None)
        break;
    }
    
    return st;
  }
  
  @Test
  public void testMap1() {
    Log.println("--------------------------------------------------------------------------");
    Log.println("testMap1");
    
    String map1 = TestBoard.map1();
    Pair<StaticConfig, State> p = State.parse(map1);
    State st = p.b;
    st = runStepper(p.a, st, solution1Map1());
    
    Assert.assertEquals(Ending.Win, st.ending);
    Assert.assertEquals(3, st.collectedLambdas);
    Assert.assertEquals(0, st.lambdaPositions.size());
    Assert.assertEquals(212, st.score);
    Assert.assertEquals(0, st.robotCol);
    Assert.assertEquals(1, st.robotRow);
  }

  @Test
  public void testMap1variant() {
    Log.println("--------------------------------------------------------------------------");
    Log.println("testMap1variant");
    
    String map1 = TestBoard.map1();
    Pair<StaticConfig, State> p = State.parse(map1);
    State st = p.b;
    st = runStepper(p.a, st, solution2Map1());
    
    Assert.assertEquals(3, st.robotCol);
    Assert.assertEquals(4, st.robotRow);
    Assert.assertEquals(Cell.Robot, st.board.get(3, 4));
    Assert.assertEquals(Cell.FallingRock, st.board.get(2, 3));
  }

  @Test
  public void testFloodMap1() {
    Log.println("--------------------------------------------------------------------------");
    Log.println("testFloodMap1");
    
    String map1 = TestBoard.floodMap1();
    Pair<StaticConfig, State> p = State.parse(map1);
    State st = p.b;
    st = runStepper(p.a, st, drowning1FloodMap1());
    
    Assert.assertEquals(Ending.LoseWater, st.ending);
  }

  @Test
  public void pushLiftAround() {
    Pair<StaticConfig, State> p = State.parse("######\n# LR\\#\n######");
    State st = p.b;
    Board before = st.board;
    st = runStepper(p.a, st, L);
    Board after = st.board;
    Assert.assertEquals(before, after);
  }
}
