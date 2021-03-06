/**
 * 
 */
package unittest;

import game.Command;
import game.State;
import game.StaticConfig;
import game.ai.Driver;
import game.ai.Fitness;
import game.config.IDriverConfig;
import game.config.SimpleSelectorConfig;
import game.log.Log;
import game.stepper.SingleStepper;

import org.junit.Test;

import util.Pair;

import junit.framework.TestCase;

/**
 * @author Sebastian Erdweg
 *
 */
public class TestFitness extends TestCase {
  @Test
  public void testLambdaCloseness() {
    String map = 
        "L#####\n" +
        "# // #\n" +
        "#R// #\n" +
        "######";
    
    IDriverConfig config = new SimpleSelectorConfig();
    Pair<StaticConfig, State> p = State.parse(map);
    Fitness f = config.fitnessFunction(p.a, p.b);
    SingleStepper stepper = new SingleStepper(p.a);
    
    State st = p.b;
    Log.println(f.evaluate(st));
    Log.println(f.evaluate(stepper.step(st, Command.Up)));
    Log.println(f.evaluate(stepper.step(st, Command.Down)));
    Log.println(f.evaluate(stepper.step(st, Command.Left)));
    Log.println(f.evaluate(stepper.step(st, Command.Right)));
    
    Driver.create("test", config, p.a, p.b).run();
  }
}
