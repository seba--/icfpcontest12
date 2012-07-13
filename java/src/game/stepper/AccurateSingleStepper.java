package game.stepper;

import game.Cell;
import game.Command;
import game.State;

/**
 * @author seba
 */
public class AccurateSingleStepper implements IStepper {

  @Override
  public void moveRobot(State st, Command cmd) {
    int nextCol;
    int nextRow;
    
    switch (cmd) {
    case Left:
      nextCol = st.robotCol - 1;
      nextRow = st.robotRow;
      break;
    case Right:
      nextCol = st.robotCol + 1;
      nextRow = st.robotRow;
      break;
    case Up:
      nextCol = st.robotCol;
      nextRow = st.robotRow + 1;
      break;
    case Down:
      nextCol = st.robotCol;
      nextRow = st.robotRow - 1;
      break;
    case Wait:
      return;
    case Abort:
      st.isAborted = true;
      return;
    default:
      throw new IllegalArgumentException("Unknown command " + cmd);
    }
    
    Cell next = st.board.get(nextCol, nextRow); 
    switch (next) {
    case Lambda:
      st.collectedLambdas++;
    case Empty:
    case Earth:
    case Lift:
      moveRobot(st, nextCol, nextRow);
      break;
    
    case Rock:
      if (cmd == Command.Left && st.board.get(nextCol - 1, nextRow) == Cell.Empty) {
        moveRock(st, nextCol, nextRow, nextCol - 1, nextRow);
        moveRobot(st, nextCol, nextRow);
        break;
      }
      if (cmd == Command.Right && st.board.get(nextCol + 1, nextRow) == Cell.Empty) {
        moveRock(st, nextCol, nextRow, nextCol + 1, nextRow);
        moveRobot(st, nextCol, nextRow);
        break;
      }

    default:
      // the move was invalid => execute Wait
      break;
    }
  }

  private void moveRobot(State st, int nextCol, int nextRow) {
    if (st.board.grid[nextCol][nextRow] == Cell.Lift)
      st.board.grid[nextCol][nextRow] = Cell.RobotAndLift;
    else
      st.board.grid[nextCol][nextRow] = Cell.Robot;
    
    if (st.board.grid[st.robotCol][st.robotRow] == Cell.RobotAndLift)
      st.board.grid[st.robotCol][st.robotRow] = Cell.Lift;
    else
      st.board.grid[st.robotCol][st.robotRow] = Cell.Empty;
    
    st.robotCol = nextCol;
    st.robotRow = nextRow;
  }

  private void moveRock(State st, int oldCol, int oldRow, int newCol, int newRow) {
    st.board.grid[oldCol][oldRow] = Cell.Empty;
    st.board.grid[newCol][newRow] = Cell.Rock;
  }

  @Override
  public void updateBoard(State st) {
    // TODO Auto-generated method stub
  }

  @Override
  public void checkEnding(State st) {
    // TODO Auto-generated method stub

  }

  @Override
  public void executeRound(State st, Command cmd) {
    moveRobot(st, cmd);
    updateBoard(st);
    checkEnding(st);
  }

}
