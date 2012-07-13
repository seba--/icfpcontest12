package game;

/**
 * @author seba
 */
public class State {
  public Board board;
  public int score;

  /*
   * data initialized from board
   */
  public int robotCol;
  public int robotRow;
  public int lambdasLeft;
  
  public Ending ending;
  public int collectedLambdas;
  
  public State(Board board) {
    this.board = board;
    this.score = 0;
    ending = Ending.None;
    collectedLambdas = 0;
    
    initStateFromBoard();
  }
  
  private void initStateFromBoard() {
    lambdasLeft = 0;
    
    for (int col = 0; col < board.width; ++col)
      for (int row = 0; row < board.height; ++row)
        switch (board.grid[col][row]) {
        case Robot:
          robotCol = col;
          robotRow = row;
          break;
        case Lambda:
          ++lambdasLeft;
          break;
        default:
          ;
        }
  }
}
