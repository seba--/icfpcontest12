package game.strategy.tom;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import game.Cell;
import game.Command;
import game.State;


public class Helpers {
  
  //TODO: test this
  public static List<Command> moveToSimple(State s, int destCol, int destRow) {
    
    WayCoordinate[][] m = new WayCoordinate[s.board.width][s.board.height];
    PriorityQueue<WayCoordinate> p = new PriorityQueue<WayCoordinate>();
    WayCoordinate wc = new WayCoordinate(s.robotCol, s.robotRow, destCol, destRow, 0, null);
     
    p.add(wc);
    
    while (wc != null && (wc.col != destCol || wc.row != destRow)) {
      
      if (m[wc.col][wc.row] != null && m[wc.col][wc.row].steps <= wc.steps) continue; //already better way
      
      Cell c = s.board.get(wc.col, wc.row);
      if (c == Cell.Earth || c == Cell.Empty || c == Cell.Lambda || c == Cell.Robot) { //may move there
          m[wc.row][wc.col] = wc;
          
          //generate follow up moves
          p.add(new WayCoordinate(wc.col-1, wc.row, destCol, destRow, wc.steps+1, Command.Left));
          p.add(new WayCoordinate(wc.col+1, wc.row, destCol, destRow, wc.steps+1, Command.Right));
          p.add(new WayCoordinate(wc.col, wc.row+1, destCol, destRow, wc.steps+1, Command.Up));
          if (s.board.get(wc.col, wc.row+1) != Cell.Rock)
            p.add(new WayCoordinate(wc.col, wc.row-1, destCol, destRow, wc.steps+1, Command.Down));
          
      }
      
      wc = p.poll();
    }
     //here: wc is destination or null 
    if (wc == null) return null;
    
    //now build commands that led here
    Command[] cmds= new Command[wc.steps];
    for (int i = wc.steps - 1; i >= 0; i--) {
      cmds[i] = wc.steppedHereWith;
      switch (wc.steppedHereWith) {
      case Left: 
        wc = m[wc.col+1][wc.row];
        break;
      case Right:
        wc = m[wc.col-1][wc.row];
        break;
      case Up:
        wc = m[wc.col][wc.row-1];
        break;
      case Down: 
        wc = m[wc.col][wc.row+1];
      }
    }
    
    return Arrays.asList(cmds);    
  }
}
