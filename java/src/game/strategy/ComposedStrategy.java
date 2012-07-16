package game.strategy;

import java.util.LinkedList;
import java.util.List;

import game.Command;
import game.State;
import game.ai.Strategy;

/**
 * @author seba
 *
 */
public class ComposedStrategy extends Strategy {

  private final Strategy[] strats;
  
  public ComposedStrategy(Strategy... strats) {
    assert strats.length > 0;
    this.strats = strats;
  }
  
  /* (non-Javadoc)
   * @see game.ai.Strategy#apply(game.State)
   */
  @Override
  public List<Command> apply(State state) {
    List<Command> commands = new LinkedList<Command>();
    
    for (Strategy s : strats) {
      List<Command> scommands = s.apply(state);
      if (scommands == null)
        break;
      commands.addAll(scommands);
    }
    
    return commands.isEmpty() ? null : commands;
  }
  
  @Override
  public boolean wantsToApply(State state) {
    return strats[0].wantsToApply(state);
  }

}
