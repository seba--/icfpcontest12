package game.strategy;

import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;
import game.strategy.tom.Helpers;
import game.strategy.tom.WayCoordinateSimple;

import java.util.ArrayList;
import java.util.List;

import util.Pair;

public class MakeRockFallStrategy extends Strategy {

  private final boolean horock;
  
  public MakeRockFallStrategy(boolean horock) {
    this.horock = horock;
  }
  
  @Override
  public List<Command> apply(State s) {
    Cell goal = horock ? Cell.HoRock : Cell.Rock;
    
    Pair<List<Command>, WayCoordinateSimple> res = Helpers.moveToFallableCell(s, goal, 1);
    if (res == null)
      return null;
    ArrayList<Command> cmds = new ArrayList<Command>(res.a);
    cmds.remove(cmds.size() - 1); // do not actually go on top of the
                                  // higher-order rock, just go to the field
                                  // next to it.
                                  // there are better strategies for dealing
                                  // with higher-order rocks

    int rcol = res.b == null ? s.robotCol : res.b.col;
    int rrow = res.b == null ? s.robotRow : res.b.row;
    
    //   some rules: (internal representation is flipped!)
    //  if possible to push a higher-order rock so it will fall, do it
    //  if possible to clear earth around a higher-order rock, do it
    
    // get 5x3 area around robot. robot is at [2][1]
    
    Cell[][] v = new Cell[5][3];
    for (int i = -2; i < 2+1; i++) {
      for (int j = -1; j < 1+1; j++) {
        v[i+2][j+1] = s.board.get(rcol + i, rrow + j);
      }
    }
        
    // -----------------------
    // idea: push higher-order rock from edge to make it fall
    
    //   R@_    r    _R@
    //   ??_    ->   ?._
    if ((v[3][1] == goal) &&
        (v[4][1] == Cell.Empty) &&
        (v[4][0] == Cell.Empty)) {
      cmds.add(Command.Right);
      return cmds;
    }
    
    //   _@R    l    @R_
    //   _??    ->   _??
    if ((v[1][1] == goal) &&
        (v[0][1] == Cell.Empty) &&
        (v[0][0] == Cell.Empty)) {
      cmds.add(Command.Left);
      return cmds;
    }
    
    // ---------------------------
    // idea: make higher-order rock slide off lambda
    
    //   @.R    l,r    
    //   \_?    ->   horock will slide off lambda
    // also capture:
    //   @\R    l
    //   \_?    ->
    
    if ((v[0][1] == goal) &&
        ((v[1][1] == Cell.Earth) || (v[1][1] == Cell.Lambda)) &&
        (v[0][0] == Cell.Lambda) &&
        (v[1][0] == Cell.Empty)) {
      cmds.add(Command.Left);
      cmds.add(Command.Right);  // TODO: is L, R the best here?
      return cmds;
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
    
    if ((v[3][1] == goal) &&
        ((v[2][0] == Cell.Earth) || (v[2][0] == Cell.Empty) || (v[2][0] == Cell.Lambda)) &&
        (v[3][0] == Cell.Earth) || (v[3][0] == Cell.Lambda)) {
      cmds.add(Command.Down);
      cmds.add(Command.Right);
      cmds.add(Command.Left);
      return cmds;
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
    
    if ((v[1][1] == goal) &&
        ((v[2][0] == Cell.Earth) || (v[2][0] == Cell.Empty) || (v[2][0] == Cell.Lambda)) &&
        (v[1][0] == Cell.Earth) || (v[3][0] == Cell.Lambda)) {
      cmds.add(Command.Down);
      cmds.add(Command.Left);
      cmds.add(Command.Right);
      return cmds;
    }
    
    
    
//  01234     :2
//  01R34     :1
//  01234     :0

    // idea: excavate earth below rock
    
    // R
    // @    R@    @R
    // . or  . or .
    
    List<Command> tempCmds = null;
    if ((v[2][0] == goal)) { // robot above rock
      tempCmds = Helpers.moveToSimple(s, rcol, rrow-1); // try to move below rock
    }
    if ((tempCmds == null) &&
        (v[3][1] == goal)) { // robot left of rock
      tempCmds = Helpers.moveToSimple(s, rcol+1, rrow-1);
    }
    if ((tempCmds == null) &&
        (v[1][1] == goal)) {  // robot right of rock
      tempCmds = Helpers.moveToSimple(s, rcol-1, rrow-1);
    }
    
    if (tempCmds != null) {
      cmds.addAll(tempCmds);
      return cmds;
    }
    return null;
    
  }
  
  @Override
  public boolean wantsToApply(State s) {
    Cell goal = horock ? Cell.HoRock : Cell.Rock;
    
    return !s.board.bitsets[goal.ordinal()].isEmpty();
    
//    // look for a higher-order rock in the vincinity:
//    // ?????
//    // ??R??
//    // ?????
//    
//    int searchWidth = 2;
//    int searchHeight = 1;
//    
//    for (int i = -searchWidth; i <= searchWidth; i++) {
//      for (int j = -searchHeight; j <= searchHeight; j++) {
//        if (s.board.get(rcol + i, rrow + j) == goal)
//          return true;
//      }
//    }
//    return false;
  }
  

  
  
  @Override
  public String toString() {
    return "makeHoRockFall";
  }

}
