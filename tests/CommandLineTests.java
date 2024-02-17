import org.junit.Assert;
import org.junit.Test;
import controller.IPlayer;
import model.Cell;
import controller.AiPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Testing command line functionality.
 */

public class CommandLineTests {

  //testing parsing valid arguments
  @Test
  public void testValidArguments() {
    String[] args = {"AI", "MaxCapture", "Human"};
    ArgParser parser = new ArgParser(args);
    parser.parseArguments();
    Assert.assertEquals("AI", parser.getPlayer1());
    Assert.assertEquals("Human", parser.getPlayer2());
    Assert.assertEquals(Arrays.asList("MaxCapture"), parser.getStrategiesPlayer1());
    Assert.assertTrue(parser.getStrategiesPlayer2().isEmpty());
  }

  //testing creating a player
  @Test
  public void testAISingleStrategy() {
    IPlayer aiPlayer = ReversiCreator.createPlayer("AI", Cell.BLACK,
            Collections.singletonList("MaxCapture"));
    Assert.assertTrue(aiPlayer instanceof AiPlayer);
    Assert.assertEquals(Cell.BLACK, aiPlayer.getCell());
    Assert.assertTrue(aiPlayer.isAI());
  }

  //missing second player type and type is not human nor ai
  @Test(expected = IllegalArgumentException.class)
  public void testInValidArguments() {
    String[] args = {"AI", "Hello World"};
    ArgParser parser = new ArgParser(args);
    parser.parseArguments();
  }

  //missing second player type
  @Test(expected = IllegalArgumentException.class)
  public void testInValidArguments2() {
    String[] args = {"AI"};
    ArgParser parser = new ArgParser(args);
    parser.parseArguments();
  }

  //will be deemed as an invalid strategy when using the createPlayer
  @Test
  public void testValidArguments2() {
    String[] args = {"AI", "Hello World", "human"};
    ArgParser parser = new ArgParser(args);
    parser.parseArguments();
  }

  //not a legal strategy
  @Test(expected = IllegalArgumentException.class)
  public void testInValidArgumentCreate() {
    String[] args = {"AI", "Hello World", "Human"};
    ArgParser parser = new ArgParser(args);
    parser.parseArguments();
    ReversiCreator creator = new ReversiCreator();
    IPlayer player1 = creator.createPlayer(parser.getPlayer1(), Cell.BLACK,
            parser.getStrategiesPlayer1());
  }

  //wrong kind of configuration for strategies
  @Test(expected = IllegalArgumentException.class)
  public void testInValidArgumentCreate2() {
    String[] args = {"AI", "Human", "MaxCapture"};
    ArgParser parser = new ArgParser(args);
    parser.parseArguments();
    ReversiCreator creator = new ReversiCreator();
    IPlayer player1 = creator.createPlayer(parser.getPlayer1(), Cell.BLACK,
            parser.getStrategiesPlayer1());
  }

  //need two strategies for trytwo
  @Test(expected = IllegalArgumentException.class)
  public void testInValidArgumentCreate3() {
    String[] args = {"AI", "TryTwo", "Human"};
    ArgParser parser = new ArgParser(args);
    parser.parseArguments();
    ReversiCreator creator = new ReversiCreator();
    IPlayer player1 = creator.createPlayer(parser.getPlayer1(), Cell.BLACK,
            parser.getStrategiesPlayer1());
  }

  //need arguments
  @Test(expected = IllegalArgumentException.class)
  public void testNoArguments() {
    String[] args = {};
    ArgParser parser = new ArgParser(args);
    parser.parseArguments();
    ReversiCreator creator = new ReversiCreator();
    IPlayer player1 = creator.createPlayer(parser.getPlayer1(), Cell.BLACK,
            parser.getStrategiesPlayer1());
  }

  //testing that it works for upper case words
  @Test
  public void testUpperCase() {
    String[] args = {"HUMAN", "HUMAN"};
    ArgParser parser = new ArgParser(args);
    parser.parseArguments();
    Assert.assertEquals("HUMAN", parser.getPlayer1());
    Assert.assertEquals("HUMAN", parser.getPlayer2());
    ReversiCreator creator = new ReversiCreator();
    IPlayer player1 = creator.createPlayer(parser.getPlayer1(), Cell.BLACK,
            parser.getStrategiesPlayer1());
  }

  //testing that the strategies lists work correctly
  @Test
  public void testCorrectStrategiesFOrTwoAis() {
    String[] args = {"Ai", "TryTwo", "MaxCapture", "AvoidNextToCorners", "ai",
            "ChooseCorners"};
    ArgParser parser = new ArgParser(args);
    parser.parseArguments();
    List<String> strat1 = List.of("TryTwo", "MaxCapture", "AvoidNextToCorners");
    Assert.assertEquals(strat1, parser.getStrategiesPlayer1());
    List<String> strat2 = List.of("ChooseCorners");
    Assert.assertEquals(strat2, parser.getStrategiesPlayer2());
    ReversiCreator creator = new ReversiCreator();
    IPlayer player1 = creator.createPlayer(parser.getPlayer1(), Cell.BLACK,
            parser.getStrategiesPlayer1());
  }

  //testing that the strategies list work correctly for human
  @Test
  public void testCorrectStrategiesFOrTwoHumans() {
    String[] args = {"Human", "human"};
    ArgParser parser = new ArgParser(args);
    parser.parseArguments();
    Assert.assertEquals(new ArrayList<>(), parser.getStrategiesPlayer1());
    Assert.assertEquals(new ArrayList<>(), parser.getStrategiesPlayer2());
  }
}
