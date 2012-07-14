package unittest;

import static org.junit.Assert.assertEquals;
import game.Board;

import org.junit.Before;
import org.junit.Test;

public class TestPositionCalculation {

  private Board board;

  @Before
  public void before() {
    board = Board.parse(TestBoard.map1());
  }

  @Test
  public void test() {
    int position = board.position(2, 3);
    assertEquals(2, board.col(position));
    assertEquals(3, board.row(position));

    assertEquals(board.position(1, 3), board.left(position));
    assertEquals(board.position(3, 3), board.right(position));
    assertEquals(board.position(2, 2), board.down(position));
    assertEquals(board.position(2, 4), board.up(position));
  }
}
