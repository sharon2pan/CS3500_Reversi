package controller;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import model.Cell;
import model.IReversiModel;
import model.Position;
import model.ReversiModel;
import strategy.MaximumCaptureStrategy;

/**
 * Tests for ai and human players.
 */
public class PlayerTests {

  //testing the getCell method
  @Test
  public void testCellColorRetrieval() {
    AiPlayer aiPlayer = new AiPlayer(Cell.WHITE, new MaximumCaptureStrategy());
    Assert.assertEquals(Cell.WHITE, aiPlayer.getCell());
  }

  //testing the isAI method
  @Test
  public void testIsAI() {
    AiPlayer aiPlayer = new AiPlayer(Cell.BLACK, new MaximumCaptureStrategy());
    Assert.assertTrue(aiPlayer.isAI());
  }

  //testing ai game play for a game that has not started
  @Test
  public void testPlay() {
    IReversiModel model = new ReversiModel(4);
    AiPlayer aiplayer = new AiPlayer(Cell.BLACK, new MaximumCaptureStrategy());
    Assert.assertEquals(Optional.empty(), aiplayer.play(model));
  }

  //testing ai game play for an actual game
  @Test
  public void testPlay2() {
    IReversiModel model = new ReversiModel(4);
    AiPlayer aiplayer = new AiPlayer(Cell.WHITE, new MaximumCaptureStrategy());
    IPlayer human = new HumanPlayer(Cell.BLACK);
    model.startGame();
    model.executeMove(new Position(2, -1, -1));
    Assert.assertEquals(Optional.of(new Position(3, -2, -1)), aiplayer.play(model));
  }

  //testing the is human an ai
  @Test
  public void testHumanIsAi() {
    IPlayer human = new HumanPlayer(Cell.BLACK);
    Assert.assertFalse(human.isAI());

  }

  //testing the getcell color for the human player
  @Test
  public void testCellColorRetrievalHuman() {
    HumanPlayer humanPlayer = new HumanPlayer(Cell.BLACK);
    Assert.assertEquals(Cell.BLACK, humanPlayer.getCell());
  }

  //human does not have a strategy so play just returns empty
  @Test
  public void testHumanPlay() {
    HumanPlayer humanPlayer = new HumanPlayer(Cell.BLACK);
    IReversiModel model = new ReversiModel(4);
    model.startGame();
    Assert.assertEquals(Optional.empty(), humanPlayer.play(model));

  }




}
