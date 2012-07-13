package game;

/**
 * @author seba
 */
public class State {
  public Board board;
  public int score;

  public int robotCol;
  public int robotRow;
  public int lambdasLeft;
  public int collectedLambdas;
  public int steps;
  public Ending ending;
  
  public State(Board board, int score, int robotCol, int robotRow, int lambdasLeft, int collectedLambdas, int steps) {
    this.board = board;
    this.score = score;
    ending = Ending.None;
    
    this.robotCol = robotCol;
    this.robotRow = robotRow;
    
    this.collectedLambdas = collectedLambdas;
    this.lambdasLeft = lambdasLeft;
    
    this.steps = steps;
  }
  
  /**
   * Auto-initialize state from intial board.
   */
  public State(Board board) {
    this.board = board;
    this.score = 0;
    this.collectedLambdas = 0;
    this.ending = Ending.None;
    this.steps = 0;
    
    int rcol = -1;
    int rrow = -1;
    int lambas = 0;
    
    for (int col = 0; col < board.width; col++)
      for (int row = 0; row < board.height; row++)
        switch (board.grid[col][row]) {
        case Robot:
          rcol = col;
          rrow = row;
          break;
        case Lambda:
          lambas++;
          break;
        default:
          ;
        }
    
    this.robotCol = rcol;
    this.robotRow = rrow;
    this.lambdasLeft = lambas;
  }
  
  public State makeFinal() {
    /*
     * From now on, you should not change any of the fields stored in this object.
     * We don't enforce that, though.
     */
    return this;
  }
}
