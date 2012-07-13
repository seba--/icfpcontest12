package game.stepper;

import game.Board;
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
        // push rock to the left
        moveRock(st.board, nextCol, nextRow, nextCol - 1, nextRow);
        moveRobot(st, nextCol, nextRow);
        break;
      }
      if (cmd == Command.Right && st.board.get(nextCol + 1, nextRow) == Cell.Empty) {
        // push rock to the right
        moveRock(st.board, nextCol, nextRow, nextCol + 1, nextRow);
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

  private void moveRock(Board board, int oldCol, int oldRow, int newCol, int newRow) {
    board.grid[oldCol][oldRow] = Cell.Empty;
    board.grid[newCol][newRow] = Cell.Rock;
  }

  @Override
  public void updateBoard(State st) {
    // read from st.board
    // write to b
    
    Board b = st.board.clone();
    
    for (int row = 0; row < b.height; row++) 
      for (int col = 0; col < b.width; col++) {
        if (st.board.grid[col][row] == Cell.Rock) {
          // fall straight down
          if (st.board.get(col, row - 1) == Cell.Empty)
            moveRock(b, col, row, col, row - 1);
          else if (st.board.get(col, row - 1) == Cell.Rock) {
            // there is a rock below
            if (st.board.get(col + 1,  row) == Cell.Empty &&
                st.board.get(col + 1, row - 1) == Cell.Empty)
              // rock slides to the right
              moveRock(b, col, row, col + 1, row - 1);
            else if (st.board.get(col - 1, row) == Cell.Empty &&
                     st.board.get(col - 1, row - 1) == Cell.Empty)
              // rock slides to the left
              moveRock(b, col, row, col - 1, row - 1);
          }
          else if (st.board.get(col, row - 1) == Cell.Lambda &&
                   st.board.get(col + 1, row) == Cell.Empty &&
                   st.board.get(col + 1, row - 1) == Cell.Empty)
            // rock slides to the right off the back of a lambda
            moveRock(b, col, row, col + 1, row - 1);
          
          // skip checking whether we should open lambda lifts
        }
      }
    
    // replace old board with new one
    st.board = b;
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
