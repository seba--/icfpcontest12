package game.strategy;

import java.util.Arrays;
import java.util.List;

import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;

public class MakeHoRockFallStrategy extends Strategy {

  @Override
  public List<Command> apply(State s) {
    //   some rules: (internal representation is flipped!)
    //  if possible to push a higher-order rock so it will fall, do it
    //  if possible to clear earth around a higher-order rock, do it
    
    // get 5x3 area around robot. robot is at [2][1]
    
    Cell[][] v = new Cell[5][3];
    for (int i = -2; i < 2+1; i++) {
      for (int j = -1; j < 1+1; j++) {
        v[i+2][j+1] = s.board.get(s.robotCol + i, s.robotRow + j);
      }
    }
        
    // -----------------------
    // idea: push higher-order rock from edge to make it fall
    
    //   R@_    r    _R@
    //   ??_    ->   ?._
    if ((v[3][1] == Cell.HoRock) &&
        (v[4][1] == Cell.Empty) &&
        (v[4][0] == Cell.Empty)) {
      return Arrays.asList(Command.Right);
    }
    
    //   _@R    l    @R_
    //   _??    ->   _??
    if ((v[1][1] == Cell.HoRock) &&
        (v[0][1] == Cell.Empty) &&
        (v[0][0] == Cell.Empty)) {
      return Arrays.asList(Command.Left);
    }
    
    // ---------------------------
    // idea: make higher-order rock slide off lambda
    
    //   @.R    l,r    
    //   \_?    ->   horock will slide off lambda
    // also capture:
    //   @\R    l
    //   \_?    ->
    
    if ((v[0][1] == Cell.HoRock) &&
        ((v[1][1] == Cell.Earth) || (v[1][1] == Cell.Lambda)) &&
        (v[0][0] == Cell.Lambda) &&
        (v[1][0] == Cell.Empty)) {
      return Arrays.asList(Command.Left, Command.Right);  // TODO: is L, R the best here? 
    }
    

//  01234     :2
//  01R34     :1
//  01234     :0

    // --------------------
    // idea: if there is earth beneath a higher-order rock, remove the earth to make it fall 
    
    // R@?
    // ..?     drl
    //         ->
    // or
    // R@?
    // _.?
    // or
    // R@?
    // \.?
    // or
    // R@?
    // \\?
    
    if ((v[3][1] == Cell.HoRock) &&
        ((v[2][0] == Cell.Earth) || (v[2][0] == Cell.Empty) || (v[2][0] == Cell.Lambda)) &&
        (v[3][0] == Cell.Earth) || (v[3][0] == Cell.Lambda)) {
      return Arrays.asList(Command.Down, Command.Right, Command.Left);
    }
    
    // ?@R
    // ?..     dlr
    //         ->
    // or
    // ?@R
    // ?._
    // or
    // ?@R
    // ?.\
    // or
    // ?@R
    // ?\\
    
    if ((v[1][1] == Cell.HoRock) &&
        ((v[2][0] == Cell.Earth) || (v[2][0] == Cell.Empty) || (v[2][0] == Cell.Lambda)) &&
        (v[1][0] == Cell.Earth) || (v[3][0] == Cell.Lambda)) {
      return Arrays.asList(Command.Down, Command.Left, Command.Right);
    }
    
    return null;
    
  }
  
  @Override
  public boolean wantsToApply(State s) {
    // look for a higher-order rock in the vincinity:
    // ?????
    // ??R??
    // ?????
    
    int searchWidth = 2;
    int searchHeight = 1;
    
    for (int i = -searchWidth; i <= searchWidth; i++) {
      for (int j = -searchHeight; j <= searchHeight; j++) {
        if (s.board.get(s.robotCol + i, s.robotRow + j) == Cell.HoRock)
          return true;
      }
    }
    return false;
  }
  

  
  
  @Override
  public String toString() {
    return "makeHoRockFall";
  }

}
