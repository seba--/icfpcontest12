package game;

import java.util.StringTokenizer;

/**
 * @author seba
 */
public class State {
  
  public final StaticConfig staticConfig;
  
  public Board board;
  public int score;

  public int robotCol;
  public int robotRow;
  public int lambdasLeft;
  public int collectedLambdas;
  public int steps;
  public Ending ending;
  
  /*
   * for flooding 
   */
  public int waterLevel;
  public int stepsUnderwater;
  public int stepsUntilNextRise;
  
  
  public State(StaticConfig sconfig, Board board, int score, int robotCol, int robotRow, int lambdasLeft, int collectedLambdas, int steps, int waterLevel, int stepsUnderwater, int stepsUntilNextRise) {
    this.staticConfig = sconfig;
    this.board = board;
    this.score = score;
    ending = Ending.None;
    
    this.robotCol = robotCol;
    this.robotRow = robotRow;
    
    this.collectedLambdas = collectedLambdas;
    this.lambdasLeft = lambdasLeft;
    
    this.steps = steps;
    
    this.waterLevel = waterLevel;
    this.stepsUnderwater = stepsUnderwater;
    this.stepsUntilNextRise = stepsUntilNextRise;
  }
  
  
  public State(StaticConfig sconfig, Board board) {
    this(sconfig, board, 0);
  }
  
  /**
   * Auto-initialize state from intial board.
   */
  public State(StaticConfig sconfig, Board board, int waterLevel) {
    this.staticConfig = sconfig;
    this.board = board;
    this.score = 0;
    this.collectedLambdas = 0;
    this.ending = Ending.None;
    this.steps = 0;
    this.waterLevel = waterLevel;
    this.stepsUnderwater = 0;
    this.stepsUntilNextRise = sconfig.floodingRate;
    
    int rcol = -1;
    int rrow = -1;
    int lambas = 0;
    
    for (int col = 0; col < board.width; ++col)
      for (int row = 0; row < board.height; ++row)
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
  
  
  public static State parse(String s) {
    String[] parts = s.split("\n\n");
    Board board = Board.parse(parts[0]);
    
    int waterLevel = 0;
    int floodingRate = 0;
    int waterResistance = 10;
    StringTokenizer tokenizer = new StringTokenizer(parts[1], "\n");
    while (tokenizer.hasMoreTokens()) {
      String next = tokenizer.nextToken();
      if (next.startsWith("Waterproof"))
        waterResistance = Integer.parseInt(next.substring(11));
      else if (next.startsWith("Water"))
        waterLevel = Integer.parseInt(next.substring(6));
      else if (next.startsWith("Flooding"))
        floodingRate = Integer.parseInt(next.substring(9));
    }
    
    return new State(new StaticConfig(floodingRate, waterResistance), board, waterLevel);
  }
}
