package game.strategy;

import game.Command;
import game.State;
import game.ai.Strategy;

import java.util.Arrays;
import java.util.List;

/**
 * This (toy) strategy always issues the same command list.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */

public class ConstantStrategy extends Strategy {
  public final List<Command> commands;
  
  public ConstantStrategy(Command... commands) {
    this(Arrays.asList(commands));
  }
  
  public ConstantStrategy(List<Command> commands) {
    super();
    this.commands = commands;
  }

  @Override
  public List<Command> apply(State state) {
    return commands;
  }
  
  @Override
  public String toString() {
    return "ConstantStrategy(" + commands.toString() + ")";
  }
}
