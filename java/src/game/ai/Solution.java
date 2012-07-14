package game.ai;

import game.Command;

/**
 * A sequence of commands to solve a board. Common prefixes (due to shared
 * states) are shared. Each part of the solution keeps a reference to the
 * strategy that created it.
 * 
 * @author Tillmann Rendel
 */
public class Solution {
  /**
   * The (possibly shared) prefix of this solution.
   * 
   * <p>
   * This field is {@code null} if there is no prefix.
   */
  public final Solution prefix;

  /**
   * The commands after the prefix.
   */
  public final Command[] commands;

  /**
   * The strategy that created the {@code commands}.
   */
  public final Strategy strategy;

  /**
   * Extend a solution with a sequence of commands.
   */
  public Solution(Solution prefix, Command[] commands, Strategy strategy) {
    super();
    this.prefix = prefix;
    this.commands = commands;
    this.strategy = strategy;
  }

  /**
   * Return all commands in this solution.
   */
  public Command[] allCommands() {
    // Measure space to reserve
    int index = 0;
    for (Solution solution = this; solution != null; solution = solution.prefix) {
      index += solution.commands.length;
    }

    // Collect commands
    Command[] result = new Command[index];
    for (Solution solution = this; solution != null; solution = solution.prefix) {
      int sublength = solution.commands.length;
      index -= sublength;
      System.arraycopy(solution.commands, 0, result, index, sublength);
    }

    return result;
  }

}
