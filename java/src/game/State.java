package game;

/**
 * @author seba
 */
public class State {
  public Board board;
  public int score;
  
  public int robotCol;
  public int robotRow;
  
  public boolean isAborted;
  public int collectedLambdas;
  
  public State(Board board) {
    this.board = board;
    this.score = 0;
    findRobot();
    isAborted = false;
    collectedLambdas = 0;
  }
  
  public void findRobot() {
    for (int col = 0; col < board.width; col++)
      for (int row = 0; row < board.height; row++)
        if (board.grid[col][row] == Cell.Robot) {
          robotCol = col;
          robotRow = row;
          return;
        }
  }
}
