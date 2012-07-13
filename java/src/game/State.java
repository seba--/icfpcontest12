package game;

/**
 * @author seba
 */
public class State {
  public Board board;
  public int score;
  
  public State(Board board) {
    this.board = board;
    this.score = 0;
  }
}
