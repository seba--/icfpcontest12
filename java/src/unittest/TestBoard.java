package unittest;

import game.Board;
import game.Cell;
import game.State;
import game.StaticConfig;
import junit.framework.Assert;

import org.junit.Test;

import util.Pair;

public class TestBoard {

  public static String map1() {
    String map1 = 
        "######\n" + 
        "#. *R#\n" +
        "#  /.#\n" +
        "#/ * #\n" +
        "L  ./#\n" +
        "######";
     map1 = map1.replace("/", "\\");

     return map1;
  }
  
  public static String floodMap1() {
    String map1 = 
        "###########\n" + 
        "#....R....#\n" + 
        "#.*******.#\n" + 
        "#.///////.#\n" + 
        "#.       .#\n" + 
        "#..*///*..#\n" + 
        "#.#*///*#.#\n" + 
        "#########L#\n" + 
        "\n" + 
        "Water 1\n" + 
        "Flooding 8\n" +  
        "Waterproof 5";
     
    map1 = map1.replace("/", "\\");

     return map1;
  }
  
    
  @Test
  public void testMap1() {
    String map1 = map1();
    
    Board board = Board.parse(map1);
    
    Assert.assertEquals(map1, board.toString());
    
    // Cell[] grid = board.grid;
    
    Assert.assertTrue(board.isWall(0 * board.height + 0));
    Assert.assertTrue(board.isLift(0 * board.height + 1));
    Assert.assertTrue(board.isWall(0 * board.height + 2));
    Assert.assertTrue(board.isWall(0 * board.height + 3));
    Assert.assertTrue(board.isWall(0 * board.height + 4));
    Assert.assertTrue(board.isWall(0 * board.height + 5));
                                               
    Assert.assertTrue(board.isWall(1 * board.height + 0));
    Assert.assertTrue(board.isEmpty(1 * board.height + 1));
    Assert.assertTrue(board.isLambda(1 * board.height + 2));
    Assert.assertTrue(board.isEmpty(1 * board.height + 3));
    Assert.assertTrue(board.isEarth(1 * board.height + 4));
    Assert.assertTrue(board.isWall(1 * board.height + 5));
                                               
    Assert.assertTrue(board.isWall(2 * board.height + 0));
    Assert.assertTrue(board.isEmpty(2 * board.height + 1));
    Assert.assertTrue(board.isEmpty(2 * board.height + 2));
    Assert.assertTrue(board.isEmpty(2 * board.height + 3));
    Assert.assertTrue(board.isEmpty(2 * board.height + 4));
    Assert.assertTrue(board.isWall(2 * board.height + 5));
                                               
    Assert.assertTrue(board.isWall(3 * board.height + 0));
    Assert.assertTrue(board.isEarth(3 * board.height + 1));
    Assert.assertTrue(board.isRock(3 * board.height + 2));
    Assert.assertTrue(board.isLambda(3 * board.height + 3));
    Assert.assertTrue(board.isRock(3 * board.height + 4));
    Assert.assertTrue(board.isWall(3 * board.height + 5));
                                               
    Assert.assertTrue(board.isWall(4 * board.height + 0));
    Assert.assertTrue(board.isLambda(4 * board.height + 1));
    Assert.assertTrue(board.isEmpty(4 * board.height + 2));
    Assert.assertTrue(board.isEarth(4 * board.height + 3));
    Assert.assertTrue(board.isRobot(4 * board.height + 4));
    Assert.assertTrue(board.isWall(4 * board.height + 5));
                                               
    Assert.assertTrue(board.isWall(5 * board.height + 0));
    Assert.assertTrue(board.isWall(5 * board.height + 1));
    Assert.assertTrue(board.isWall(5 * board.height + 2));
    Assert.assertTrue(board.isWall(5 * board.height + 3));
    Assert.assertTrue(board.isWall(5 * board.height + 4));
    Assert.assertTrue(board.isWall(5 * board.height + 5));
  }
  
  @Test
  public void testFloodMap1() {
    String floodMap1 = floodMap1();
    
    Pair<StaticConfig, State> p = State.parse(floodMap1);
    
    Assert.assertEquals(1, p.b.waterLevel);
    Assert.assertEquals(8, p.a.floodingRate);
    Assert.assertEquals(5, p.a.waterResistance);
  }  
}
