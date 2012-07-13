package unittest;

import junit.framework.Assert;
import game.Board;
import game.Cell;

import org.junit.Test;

public class TestBoard {

  @Test
  public void testMap1() {
    String map1 = 
       "######\n" + 
       "#. *R#\n" +
       "#  /.#\n" +
       "#/ * #\n" +
       "L  ./#\n" +
       "######";

    Cell[][] grid = Board.parse(map1).grid;
    
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
}
