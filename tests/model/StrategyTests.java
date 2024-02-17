package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import strategy.AvoidNeighboringCornersStrategy;
import strategy.GoForCornersStrategy;
import strategy.IReversiStrategy;
import strategy.MaximumCaptureStrategy;
import strategy.MinimizeMaxMoveStrategy;
import strategy.TryTwo;
import view.IView;
import view.ReversiView;

/**
 * Tests for strategies using the mockModels.
 */
public class StrategyTests {

  //Checking if strategy 1 checks all possible locations for potential moves
  @Test
  public void testStrategyMaxCapture() {
    StringBuilder log = new StringBuilder();
    MockReversi1 model = new MockReversi1(log, 2);
    IReversiStrategy strategy1 = new MaximumCaptureStrategy();
    List<IPosition> posns = strategy1.choosePositions(model, Cell.BLACK);
    strategy1.chooseBestPosition(posns);
    String checkAllPositions = "Position[q=-2, r=0, s=2]\n" +
            "Position[q=-2, r=1, s=1]\n" +
            "Position[q=-2, r=2, s=0]\n" +
            "Position[q=-1, r=-1, s=2]\n" +
            "Position[q=-1, r=0, s=1]\n" +
            "Position[q=-1, r=1, s=0]\n" +
            "Position[q=-1, r=2, s=-1]\n" +
            "Position[q=0, r=-2, s=2]\n" +
            "Position[q=0, r=-1, s=1]\n" +
            "Position[q=0, r=0, s=0]\n" +
            "Position[q=0, r=1, s=-1]\n" +
            "Position[q=0, r=2, s=-2]\n" +
            "Position[q=1, r=-2, s=1]\n" +
            "Position[q=1, r=-1, s=0]\n" +
            "Position[q=1, r=0, s=-1]\n" +
            "Position[q=1, r=1, s=-2]\n" +
            "Position[q=2, r=-2, s=0]\n" +
            "Position[q=2, r=-1, s=-1]\n" +
            "Position[q=2, r=0, s=-2]\n";
    Assert.assertEquals(checkAllPositions, log.toString());

  }

  //Checking if strategy 3 checks all four corners
  //the last 4 positions are the corners, the positions listed before
  //are checking if there are any legal moves on the board, if there are none,
  //pass the players turn
  @Test
  public void testStrategyCorners() {
    StringBuilder log = new StringBuilder();
    MockReversi1 model = new MockReversi1(log, 3);
    IReversiStrategy strategy1 = new GoForCornersStrategy();
    //strategy1.choosePosition(model, Cell.BLACK);
    List<IPosition> posns = strategy1.choosePositions(model, Cell.BLACK);
    strategy1.chooseBestPosition(posns);
    String checkAllPositions = "Position[q=-3, r=0, s=3]\n" +
            "Position[q=-3, r=1, s=2]\n" +
            "Position[q=-3, r=2, s=1]\n" +
            "Position[q=-3, r=3, s=0]\n" +
            "Position[q=-2, r=-1, s=3]\n" +
            "Position[q=-2, r=0, s=2]\n" +
            "Position[q=-2, r=1, s=1]\n" +
            "Position[q=-2, r=2, s=0]\n" +
            "Position[q=-2, r=3, s=-1]\n" +
            "Position[q=-1, r=-2, s=3]\n" +
            "Position[q=-1, r=-1, s=2]\n" +
            "Position[q=-1, r=0, s=1]\n" +
            "Position[q=-1, r=1, s=0]\n" +
            "Position[q=-1, r=2, s=-1]\n" +
            "Position[q=-1, r=3, s=-2]\n" +
            "Position[q=0, r=-3, s=3]\n" +
            "Position[q=0, r=-2, s=2]\n" +
            "Position[q=0, r=-1, s=1]\n" +
            "Position[q=0, r=0, s=0]\n" +
            "Position[q=0, r=1, s=-1]\n" +
            "Position[q=0, r=2, s=-2]\n" +
            "Position[q=0, r=3, s=-3]\n" +
            "Position[q=1, r=-3, s=2]\n" +
            "Position[q=1, r=-2, s=1]\n" +
            "Position[q=1, r=-1, s=0]\n" +
            "Position[q=1, r=0, s=-1]\n" +
            "Position[q=1, r=1, s=-2]\n" +
            "Position[q=1, r=2, s=-3]\n" +
            "Position[q=2, r=-3, s=1]\n" +
            "Position[q=2, r=-2, s=0]\n" +
            "Position[q=2, r=-1, s=-1]\n" +
            "Position[q=2, r=0, s=-2]\n" +
            "Position[q=2, r=1, s=-3]\n" +
            "Position[q=3, r=-3, s=0]\n" +
            "Position[q=3, r=-2, s=-1]\n" +
            "Position[q=3, r=-1, s=-2]\n" +
            "Position[q=3, r=0, s=-3]\n";
    Assert.assertEquals(checkAllPositions, log.toString());
  }

  //checking for all possible locations for potential moves for a larger board
  @Test
  public void testStrategyMaxCapture2() {
    StringBuilder log = new StringBuilder();
    MockReversi1 model = new MockReversi1(log, 5);
    IReversiStrategy strategy1 = new MaximumCaptureStrategy();
    List<IPosition> posns = strategy1.choosePositions(model, Cell.BLACK);
    strategy1.chooseBestPosition(posns);
    String checkAllPositions = "Position[q=-5, r=0, s=5]\n" +
            "Position[q=-5, r=1, s=4]\n" +
            "Position[q=-5, r=2, s=3]\n" +
            "Position[q=-5, r=3, s=2]\n" +
            "Position[q=-5, r=4, s=1]\n" +
            "Position[q=-5, r=5, s=0]\n" +
            "Position[q=-4, r=-1, s=5]\n" +
            "Position[q=-4, r=0, s=4]\n" +
            "Position[q=-4, r=1, s=3]\n" +
            "Position[q=-4, r=2, s=2]\n" +
            "Position[q=-4, r=3, s=1]\n" +
            "Position[q=-4, r=4, s=0]\n" +
            "Position[q=-4, r=5, s=-1]\n" +
            "Position[q=-3, r=-2, s=5]\n" +
            "Position[q=-3, r=-1, s=4]\n" +
            "Position[q=-3, r=0, s=3]\n" +
            "Position[q=-3, r=1, s=2]\n" +
            "Position[q=-3, r=2, s=1]\n" +
            "Position[q=-3, r=3, s=0]\n" +
            "Position[q=-3, r=4, s=-1]\n" +
            "Position[q=-3, r=5, s=-2]\n" +
            "Position[q=-2, r=-3, s=5]\n" +
            "Position[q=-2, r=-2, s=4]\n" +
            "Position[q=-2, r=-1, s=3]\n" +
            "Position[q=-2, r=0, s=2]\n" +
            "Position[q=-2, r=1, s=1]\n" +
            "Position[q=-2, r=2, s=0]\n" +
            "Position[q=-2, r=3, s=-1]\n" +
            "Position[q=-2, r=4, s=-2]\n" +
            "Position[q=-2, r=5, s=-3]\n" +
            "Position[q=-1, r=-4, s=5]\n" +
            "Position[q=-1, r=-3, s=4]\n" +
            "Position[q=-1, r=-2, s=3]\n" +
            "Position[q=-1, r=-1, s=2]\n" +
            "Position[q=-1, r=0, s=1]\n" +
            "Position[q=-1, r=1, s=0]\n" +
            "Position[q=-1, r=2, s=-1]\n" +
            "Position[q=-1, r=3, s=-2]\n" +
            "Position[q=-1, r=4, s=-3]\n" +
            "Position[q=-1, r=5, s=-4]\n" +
            "Position[q=0, r=-5, s=5]\n" +
            "Position[q=0, r=-4, s=4]\n" +
            "Position[q=0, r=-3, s=3]\n" +
            "Position[q=0, r=-2, s=2]\n" +
            "Position[q=0, r=-1, s=1]\n" +
            "Position[q=0, r=0, s=0]\n" +
            "Position[q=0, r=1, s=-1]\n" +
            "Position[q=0, r=2, s=-2]\n" +
            "Position[q=0, r=3, s=-3]\n" +
            "Position[q=0, r=4, s=-4]\n" +
            "Position[q=0, r=5, s=-5]\n" +
            "Position[q=1, r=-5, s=4]\n" +
            "Position[q=1, r=-4, s=3]\n" +
            "Position[q=1, r=-3, s=2]\n" +
            "Position[q=1, r=-2, s=1]\n" +
            "Position[q=1, r=-1, s=0]\n" +
            "Position[q=1, r=0, s=-1]\n" +
            "Position[q=1, r=1, s=-2]\n" +
            "Position[q=1, r=2, s=-3]\n" +
            "Position[q=1, r=3, s=-4]\n" +
            "Position[q=1, r=4, s=-5]\n" +
            "Position[q=2, r=-5, s=3]\n" +
            "Position[q=2, r=-4, s=2]\n" +
            "Position[q=2, r=-3, s=1]\n" +
            "Position[q=2, r=-2, s=0]\n" +
            "Position[q=2, r=-1, s=-1]\n" +
            "Position[q=2, r=0, s=-2]\n" +
            "Position[q=2, r=1, s=-3]\n" +
            "Position[q=2, r=2, s=-4]\n" +
            "Position[q=2, r=3, s=-5]\n" +
            "Position[q=3, r=-5, s=2]\n" +
            "Position[q=3, r=-4, s=1]\n" +
            "Position[q=3, r=-3, s=0]\n" +
            "Position[q=3, r=-2, s=-1]\n" +
            "Position[q=3, r=-1, s=-2]\n" +
            "Position[q=3, r=0, s=-3]\n" +
            "Position[q=3, r=1, s=-4]\n" +
            "Position[q=3, r=2, s=-5]\n" +
            "Position[q=4, r=-5, s=1]\n" +
            "Position[q=4, r=-4, s=0]\n" +
            "Position[q=4, r=-3, s=-1]\n" +
            "Position[q=4, r=-2, s=-2]\n" +
            "Position[q=4, r=-1, s=-3]\n" +
            "Position[q=4, r=0, s=-4]\n" +
            "Position[q=4, r=1, s=-5]\n" +
            "Position[q=5, r=-5, s=0]\n" +
            "Position[q=5, r=-4, s=-1]\n" +
            "Position[q=5, r=-3, s=-2]\n" +
            "Position[q=5, r=-2, s=-3]\n" +
            "Position[q=5, r=-1, s=-4]\n" +
            "Position[q=5, r=0, s=-5]\n";
    Assert.assertEquals(checkAllPositions, log.toString());

  }

  //testing if the strategy chooses the position with the maximum amount of captures.
  @Test
  public void testMultipleFlipsMovingOver() {
    IReversiModel model = new ReversiModel(4);
    model.startGame();
    model.executeMove(new Position(1, 1, -2));
    model.executeMove(new Position(-1, -1, 2));
    Assert.assertEquals(2, model.countCaptures(new Position(-1, -2, 3), Cell.BLACK));
    MaximumCaptureStrategy str = new MaximumCaptureStrategy();
    List<IPosition> posns = str.choosePositions(model, Cell.BLACK);
    Assert.assertEquals(Optional.of(new Position(-1, -2, 3)), str.chooseBestPosition(posns));
  }

  //testing the MaxCapturesStrategy correctly handles a tie
  @Test
  public void testTieGoesUpperLeft() {
    IReversiModel model = new ReversiModel(4);
    model.startGame();
    MaximumCaptureStrategy str = new MaximumCaptureStrategy();
    List<IPosition> posns = str.choosePositions(model, Cell.BLACK);
    //goes to upper leftmost
    Assert.assertEquals(Optional.of(new Position(1, -2, 1)),
            str.chooseBestPosition(posns));

  }

  //testing go for corners strategy with one legal open corner
  @Test
  public void testGoForOnlyLegalCorner() {
    IReversiModel model = new ReversiModel(3);
    model.startGame();
    model.executeMove(new Position(2, -1, -1));
    model.executeMove(new Position(3, -2, -1));
    model.executeMove(new Position(3, -1, -2));
    model.executeMove(new Position(-1, 2, -1));
    IView view = new ReversiView(model);
    view.renderTextualView();
    GoForCornersStrategy str = new GoForCornersStrategy();
    List<IPosition> posns = str.choosePositions(model, Cell.BLACK);

    Assert.assertEquals(Optional.of(new Position(3, -3, 0)),
            str.chooseBestPosition(posns));

  }

  //testing that avoid corners strategy looks through all possible positions
  //only legal moves being the ones adjacent to the corners, meaning that there
  //are no moves for this strategy
  @Test
  public void testAvoidCorners() {
    StringBuilder log = new StringBuilder();
    MockReversiAvoidCorners model = new MockReversiAvoidCorners(log, 3);
    IReversiStrategy strategy1 = new AvoidNeighboringCornersStrategy();
    //strategy1.choosePosition(model, Cell.BLACK);
    List<IPosition> posns = strategy1.choosePositions(model, Cell.BLACK);
    strategy1.chooseBestPosition(posns);
    String checkAllPositions = "Position[q=-3, r=0, s=3] not legal\n" +
            "Position[q=-3, r=1, s=2] not legal\n" +
            "Position[q=-3, r=2, s=1] legal\n" +
            "Position[q=-3, r=3, s=0] not legal\n" +
            "Position[q=-2, r=-1, s=3] not legal\n" +
            "Position[q=-2, r=0, s=2] not legal\n" +
            "Position[q=-2, r=1, s=1] not legal\n" +
            "Position[q=-2, r=2, s=0] legal\n" +
            "Position[q=-2, r=3, s=-1] legal\n" +
            "Position[q=-1, r=-2, s=3] legal\n" +
            "Position[q=-1, r=-1, s=2] not legal\n" +
            "Position[q=-1, r=0, s=1] not legal\n" +
            "Position[q=-1, r=1, s=0] not legal\n" +
            "Position[q=-1, r=2, s=-1] not legal\n" +
            "Position[q=-1, r=3, s=-2] legal\n" +
            "Position[q=0, r=-3, s=3] not legal\n" +
            "Position[q=0, r=-2, s=2] legal\n" +
            "Position[q=0, r=-1, s=1] not legal\n" +
            "Position[q=0, r=0, s=0] not legal\n" +
            "Position[q=0, r=1, s=-1] not legal\n" +
            "Position[q=0, r=2, s=-2] legal\n" +
            "Position[q=0, r=3, s=-3] not legal\n" +
            "Position[q=1, r=-3, s=2] legal\n" +
            "Position[q=1, r=-2, s=1] not legal\n" +
            "Position[q=1, r=-1, s=0] not legal\n" +
            "Position[q=1, r=0, s=-1] not legal\n" +
            "Position[q=1, r=1, s=-2] not legal\n" +
            "Position[q=1, r=2, s=-3] legal\n" +
            "Position[q=2, r=-3, s=1] legal\n" +
            "Position[q=2, r=-2, s=0] legal\n" +
            "Position[q=2, r=-1, s=-1] not legal\n" +
            "Position[q=2, r=0, s=-2] not legal\n" +
            "Position[q=2, r=1, s=-3] not legal\n" +
            "Position[q=3, r=-3, s=0] not legal\n" +
            "Position[q=3, r=-2, s=-1] legal\n" +
            "Position[q=3, r=-1, s=-2] not legal\n" +
            "Position[q=3, r=0, s=-3] not legal\n";
    Assert.assertEquals(checkAllPositions, log.toString());
    // Assert.assertEquals(Optional.empty(), strategy1.choosePosition(model, Cell.BLACK));

  }

  //testing avoiding the adjacent corners when the only moves are to the adjacent corners
  @Test
  public void testAvoidCornersHard() {
    IReversiModel model = new ReversiModel(2);
    model.startGame();
    IView view = new ReversiView(model);
    view.renderTextualView();
    AvoidNeighboringCornersStrategy str = new AvoidNeighboringCornersStrategy();
    List<IPosition> posns = str.choosePositions(model, Cell.BLACK);
    Assert.assertEquals(Optional.empty(), str.chooseBestPosition(posns));

  }

  //goes to the upper leftmost legal move that is not an adjacent corner
  @Test
  public void testAvoidCornersEasier() {
    IReversiModel model = new ReversiModel(3);
    model.startGame();
    IView view = new ReversiView(model);
    view.renderTextualView();
    AvoidNeighboringCornersStrategy str = new AvoidNeighboringCornersStrategy();
    IReversiStrategy str2 = new MaximumCaptureStrategy();
    IReversiStrategy strategy = new TryTwo(str, str2);
    List<IPosition> posns = strategy.choosePositions(model, Cell.BLACK);
    Assert.assertEquals(Optional.of(new Position(1, -2, 1)), strategy.chooseBestPosition(posns));

  }

  //there are no legal moves for the avoid corners strategy
  //so it uses the maximum capture strategy.
  @Test
  public void testCantAvoidNeighboringCornersButCanMaxCapture() {
    IReversiModel model = new ReversiModel(2);
    model.startGame();
    IView view = new ReversiView(model);
    view.renderTextualView();
    AvoidNeighboringCornersStrategy str = new AvoidNeighboringCornersStrategy();
    IReversiStrategy str2 = new MaximumCaptureStrategy();
    IReversiStrategy strategy = new TryTwo(str, str2);
    List<IPosition> posns = strategy.choosePositions(model, Cell.BLACK);
    Assert.assertEquals(Optional.of(new Position(1, -2, 1)), strategy.chooseBestPosition(posns));

  }

  //this mock lies about the amount of captures a position has the strategy follows it
  @Test
  public void testMaxStrategyLyingMock() {
    StringBuilder log = new StringBuilder();
    IReversiModel model2 = new ReversiModel(4);
    model2.startGame();
    MockReversiLieCaptures model = new MockReversiLieCaptures(model2, log);
    IReversiStrategy strategy1 = new MaximumCaptureStrategy();
    List<IPosition> posns = strategy1.choosePositions(model, Cell.BLACK);
    Assert.assertEquals(Optional.of(new Position(2, -1, -1)),
            strategy1.chooseBestPosition(posns));
    String expected = "0\n" +
            "0\n" +
            "0\n" +
            "0\n" +
            "0\n" +
            "5\n";
    Assert.assertEquals(expected, log.toString());
  }

  //testing go for corner when there are no corners available
  @Test
  public void testGoForCornersWhenNoCornersAvailable() {
    IReversiModel model = new ReversiModel(2);
    model.startGame();
    IReversiStrategy str = new GoForCornersStrategy();
    List<IPosition> posns = str.choosePositions(model, Cell.BLACK);

    Assert.assertEquals(Optional.empty(), str.chooseBestPosition(posns));
  }

  //testing go for corner when there are no corners available
  //so goes to the maximum capture
  @Test
  public void testGoForCornersWhenNoCornersAvailableSoGoesToMax() {
    IReversiModel model = new ReversiModel(2);
    IReversiStrategy str = new GoForCornersStrategy();
    IReversiStrategy str2 = new MaximumCaptureStrategy();
    IReversiStrategy tt = new TryTwo(str, str2);
    model.startGame();
    List<IPosition> posns = tt.choosePositions(model, Cell.BLACK);
    //goes to upper leftmost position for ties
    Assert.assertEquals(Optional.of(new Position(1, -2, 1)),
            tt.chooseBestPosition(posns));
  }


  //testing maximum capture strategy when the game is over
  //should return an empty list
  @Test
  public void testMaximumCaptureStrategyEndGame() {
    IReversiModel model = new ReversiModel(2);
    IReversiStrategy str = new MaximumCaptureStrategy();
    model.startGame();
    model.executeMove(new Position(2, -1, -1)); //b
    model.executeMove(new Position(-2, 1, 1)); //w
    model.passTurn(); //b
    model.executeMove(new Position(1, -2, 1)); //w
    model.executeMove(new Position(-1, -1, 2)); //b
    model.executeMove(new Position(1, 1, -2)); //w
    model.executeMove(new Position(-1, 2, -1)); //b
    List<IPosition> posns = str.choosePositions(model, Cell.WHITE);
    Assert.assertEquals(0, posns.size());

    Assert.assertEquals(Optional.empty(), str.chooseBestPosition(posns));
  }

  //testing maximum capture strategy when the game is one move away from being over
  //and there is only one valid position
  @Test
  public void testMaximumCaptureStrategyAlmostEndGame() {
    IReversiModel model = new ReversiModel(2);
    IReversiStrategy str = new MaximumCaptureStrategy();
    model.startGame();
    model.executeMove(new Position(2, -1, -1)); //b
    model.executeMove(new Position(-2, 1, 1)); //w
    model.passTurn(); //b
    model.executeMove(new Position(1, -2, 1)); //w
    model.executeMove(new Position(-1, -1, 2)); //b
    model.executeMove(new Position(1, 1, -2)); //w
    List<IPosition> posns = str.choosePositions(model, Cell.BLACK);
    Assert.assertEquals(1, posns.size());

    Assert.assertEquals(Optional.of(new Position(-1,2,-1)),
            str.chooseBestPosition(posns));
  }

  //testing maximum capture strategy when the game is more than halfway through the game
  @Test
  public void testMaximumCaptureStrategyAlmostEndGame2() {
    IReversiModel model = new ReversiModel(2);
    IReversiStrategy str = new MaximumCaptureStrategy();
    model.startGame();
    model.executeMove(new Position(2, -1, -1)); //b
    model.executeMove(new Position(-2, 1, 1)); //w
    model.passTurn(); //b
    model.executeMove(new Position(1, -2, 1)); //w
    model.executeMove(new Position(-1, -1, 2)); //b
    List<IPosition> posns = str.choosePositions(model, Cell.WHITE);
    Assert.assertEquals(1, posns.size());

    Assert.assertEquals(Optional.of(new Position(1,1,-2)),
            str.chooseBestPosition(posns));
  }


  //scenario that there are two legal moves to play: one gives the opponent 3 moves: two avoiding
  //adjacent corners and one max capture of 2.  The other position give 1 avoiding adjacent corner
  // move and the other no advantage. It chooses position two.
  @Test
  public void testMinimizeMaxMoveStrategy2() {
    IReversiModel model = new ReversiModel(3);
    IReversiStrategy str = new MinimizeMaxMoveStrategy();
    model.startGame();
    model.executeMove(new Position(2, -1, -1));
    model.executeMove(new Position(-2, 1, 1));
    List<IPosition> posns = str.choosePositions(model, Cell.BLACK);
    Assert.assertEquals(Optional.of(new Position(-1, 2, -1)),
            str.chooseBestPosition(posns));

  }
}
