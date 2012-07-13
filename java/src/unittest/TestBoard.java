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
    
    Assert.assertEquals(grid[0][0], Cell.Wall);
    Assert.assertEquals(grid[0][1], Cell.Lift);
    Assert.assertEquals(grid[0][2], Cell.Wall);
    Assert.assertEquals(grid[0][3], Cell.Wall);
    Assert.assertEquals(grid[0][4], Cell.Wall);
    Assert.assertEquals(grid[0][5], Cell.Wall);

    Assert.assertEquals(grid[1][0], Cell.Wall);
    Assert.assertEquals(grid[1][1], Cell.Empty);
    Assert.assertEquals(grid[1][2], Cell.Lambda);
    Assert.assertEquals(grid[1][3], Cell.Empty);
    Assert.assertEquals(grid[1][4], Cell.Earth);
    Assert.assertEquals(grid[1][5], Cell.Wall);

    Assert.assertEquals(grid[2][0], Cell.Wall);
    Assert.assertEquals(grid[2][1], Cell.Empty);
    Assert.assertEquals(grid[2][2], Cell.Empty);
    Assert.assertEquals(grid[2][3], Cell.Empty);
    Assert.assertEquals(grid[2][4], Cell.Empty);
    Assert.assertEquals(grid[2][5], Cell.Wall);

    Assert.assertEquals(grid[3][0], Cell.Wall);
    Assert.assertEquals(grid[3][1], Cell.Earth);
    Assert.assertEquals(grid[3][2], Cell.Rock);
    Assert.assertEquals(grid[3][3], Cell.Lambda);
    Assert.assertEquals(grid[3][4], Cell.Rock);
    Assert.assertEquals(grid[3][5], Cell.Wall);

    Assert.assertEquals(grid[4][0], Cell.Wall);
    Assert.assertEquals(grid[4][1], Cell.Lambda);
    Assert.assertEquals(grid[4][2], Cell.Empty);
    Assert.assertEquals(grid[4][3], Cell.Earth);
    Assert.assertEquals(grid[4][4], Cell.Robot);
    Assert.assertEquals(grid[4][5], Cell.Wall);
 
    Assert.assertEquals(grid[5][0], Cell.Wall);
    Assert.assertEquals(grid[5][1], Cell.Lift);
    Assert.assertEquals(grid[5][2], Cell.Wall);
    Assert.assertEquals(grid[5][3], Cell.Wall);
    Assert.assertEquals(grid[5][4], Cell.Wall);
    Assert.assertEquals(grid[5][5], Cell.Wall);
  }
}
