package unittest;

import junit.framework.Assert;
import game.Board;
import game.Cell;
import game.State;

import org.junit.Test;

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
    
    Cell[][] grid = board.grid;
    
    Assert.assertEquals(Cell.Wall,   grid[0][0]);
    Assert.assertEquals(Cell.Lift,   grid[0][1]);
    Assert.assertEquals(Cell.Wall,   grid[0][2]);
    Assert.assertEquals(Cell.Wall,   grid[0][3]);
    Assert.assertEquals(Cell.Wall,   grid[0][4]);
    Assert.assertEquals(Cell.Wall,   grid[0][5]);
                                               
    Assert.assertEquals(Cell.Wall,   grid[1][0]);
    Assert.assertEquals(Cell.Empty,  grid[1][1]);
    Assert.assertEquals(Cell.Lambda, grid[1][2]);
    Assert.assertEquals(Cell.Empty,  grid[1][3]);
    Assert.assertEquals(Cell.Earth,  grid[1][4]);
    Assert.assertEquals(Cell.Wall,   grid[1][5]);
                                               
    Assert.assertEquals(Cell.Wall,   grid[2][0]);
    Assert.assertEquals(Cell.Empty,  grid[2][1]);
    Assert.assertEquals(Cell.Empty,  grid[2][2]);
    Assert.assertEquals(Cell.Empty,  grid[2][3]);
    Assert.assertEquals(Cell.Empty,  grid[2][4]);
    Assert.assertEquals(Cell.Wall,   grid[2][5]);
                                               
    Assert.assertEquals(Cell.Wall,   grid[3][0]);
    Assert.assertEquals(Cell.Earth,  grid[3][1]);
    Assert.assertEquals(Cell.Rock,   grid[3][2]);
    Assert.assertEquals(Cell.Lambda, grid[3][3]);
    Assert.assertEquals(Cell.Rock,   grid[3][4]);
    Assert.assertEquals(Cell.Wall,   grid[3][5]);
                                               
    Assert.assertEquals(Cell.Wall,   grid[4][0]);
    Assert.assertEquals(Cell.Lambda, grid[4][1]);
    Assert.assertEquals(Cell.Empty,  grid[4][2]);
    Assert.assertEquals(Cell.Earth,  grid[4][3]);
    Assert.assertEquals(Cell.Robot,  grid[4][4]);
    Assert.assertEquals(Cell.Wall,   grid[4][5]);
                                               
    Assert.assertEquals(Cell.Wall,   grid[5][0]);
    Assert.assertEquals(Cell.Wall,   grid[5][1]);
    Assert.assertEquals(Cell.Wall,   grid[5][2]);
    Assert.assertEquals(Cell.Wall,   grid[5][3]);
    Assert.assertEquals(Cell.Wall,   grid[5][4]);
    Assert.assertEquals(Cell.Wall,   grid[5][5]);
  }
  
  @Test
  public void testFloodMap1() {
    String floodMap1 = floodMap1();
    
    State st = State.parse(floodMap1);
    
    Assert.assertEquals(1, st.waterLevel);
    Assert.assertEquals(8, st.staticConfig.floodingRate);
    Assert.assertEquals(5, st.staticConfig.waterResistance);
  }  
}
