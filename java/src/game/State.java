package game;

import game.ai.Solution;
import game.ai.Strategy;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * @author seba
 */
public class State {

  public final StaticConfig staticConfig;

  public Board board;
  public int robotCol;
  public int robotRow;
  public int collectedLambdas;
  
  
  /**
   * Positions of lambdas in board.
   */
  public Set<Integer> lambdaPositions;
  
  public Ending ending;
  
  /**
   * Set of active positions, that is, positions that might require board update.
   */
  public Set<Integer> activePositions;

  /**
   * How much score we got so far.
   */
  public int score;

  /**
   * Whether our scorer thinks we should explore this state.
   */
  public int fitness;

  /**
   * steps taken since start of game.
   */
  public int steps;

  /**
   * not yet applied strategies. gets set when state is created in driver.
   */
  public Set<Strategy> pendingStrategies = new HashSet<Strategy>();

  /**
   * The sequence of commands that leads to this state.
   */
  public Solution solution;

  /*
   * for flooding
   */
  public int waterLevel;
  public int stepsUnderwater;
  public int stepsUntilNextRise;

  public State(StaticConfig sconfig, Board board, Set<Integer> activePositions, int score, int robotCol, int robotRow, Set<Integer> lambdaPositions, int collectedLambdas, int steps, int waterLevel, int stepsUnderwater, int stepsUntilNextRise) {
    this.staticConfig = sconfig;
    this.board = board;
    this.score = score;
    this.ending = Ending.None;
    this.activePositions = new TreeSet<Integer>(activePositions);

    this.robotCol = robotCol;
    this.robotRow = robotRow;

    this.collectedLambdas = collectedLambdas;
    this.lambdaPositions = new TreeSet<Integer>(lambdaPositions);

    this.steps = steps;

    this.waterLevel = waterLevel;
    this.stepsUnderwater = stepsUnderwater;
    this.stepsUntilNextRise = stepsUntilNextRise;
  }

  public State(StaticConfig sconfig, Board board) {
    this(sconfig, board, 0);
  }

  /**
   * Auto-initialize state from initial board.
   */
  public State(StaticConfig sconfig, Board board, int waterLevel) {
    this.staticConfig = sconfig;
    this.board = board;
    this.activePositions = new TreeSet<Integer>();
    this.score = 0;
    this.collectedLambdas = 0;
    this.ending = Ending.None;
    this.steps = 0;
    this.waterLevel = waterLevel;
    this.stepsUnderwater = 0;
    this.stepsUntilNextRise = sconfig.floodingRate;
    this.lambdaPositions = new TreeSet<Integer>();

    int rcol = -1;
    int rrow = -1;

    for (int col = 0; col < board.width; ++col)
      for (int row = 0; row < board.height; ++row)
        switch (board.get(col, row)) {
        case Robot:
          rcol = col;
          rrow = row;
          break;
        case Lambda:
          lambdaPositions.add(col * board.height + row);          
          break;
        default:
          ;
        }

    this.robotCol = rcol;
    this.robotRow = rrow;
  }

  public State makeFinal() {
    /*
     * From now on, you should not change any of the fields stored in this
     * object. We don't enforce that, though.
     */
    return this;
  }

  public static State parse(String s) {
    String[] parts = s.split("\n\n");
    Board board = Board.parse(parts[0]);

    int waterLevel = 0;
    int floodingRate = 0;
    int waterResistance = 10;
    if (parts.length > 1) {
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
    }

    return new State(new StaticConfig(floodingRate, waterResistance), board, waterLevel);
  }

  @Override
  public String toString() {
    String s = board.toString();
    if (waterLevel > 0)
      s += " water=" + waterLevel;
    return s;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((board == null) ? 0 : board.hashCode());
    result = prime * result + collectedLambdas;
    result = prime * result + ((ending == null) ? 0 : ending.hashCode());
    result = prime * result + robotCol;
    result = prime * result + robotRow;
    result = prime * result + score;
    result = prime * result + steps;
    result = prime * result + stepsUnderwater;
    result = prime * result + stepsUntilNextRise;
    result = prime * result + waterLevel;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    State other = (State) obj;
    if (board == null) {
      if (other.board != null)
        return false;
    } else if (!board.equals(other.board))
      return false;
    if (collectedLambdas != other.collectedLambdas)
      return false;
    if (ending != other.ending)
      return false;
    if (robotCol != other.robotCol)
      return false;
    if (robotRow != other.robotRow)
      return false;
    if (score != other.score)
      return false;
    if (steps != other.steps)
      return false;
    if (stepsUnderwater != other.stepsUnderwater)
      return false;
    if (stepsUntilNextRise != other.stepsUntilNextRise)
      return false;
    if (waterLevel != other.waterLevel)
      return false;
    return true;
  }

  public State clone() {
    return new State(staticConfig, board, activePositions, score, robotCol, robotRow, lambdaPositions, collectedLambdas, steps, waterLevel, stepsUnderwater, stepsUntilNextRise);
  }  
}
