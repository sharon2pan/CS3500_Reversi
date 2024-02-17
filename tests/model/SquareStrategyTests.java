package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import strategy.AvoidNeighboringCornersStrategy;
import strategy.GoForCornersStrategy;
import strategy.IReversiStrategy;
import strategy.MaximumCaptureStrategy;
import strategy.TryTwo;
import view.IView;
import view.SquareReversiView;

/**
 * Tests for strategies using the mockModels for a square Reversi.
 */
public class SquareStrategyTests {
  //Checking if strategy 1 checks all possible locations for potential moves
  @Test
  public void testStrategyMaxCapture() {
    StringBuilder log = new StringBuilder();
    MockReversiSquare model = new MockReversiSquare(log, 2);
    IReversiStrategy strategy1 = new MaximumCaptureStrategy();
    List<IPosition> posns = strategy1.choosePositions(model, Cell.BLACK);
    strategy1.chooseBestPosition(posns);
    String checkAllPositions = "Position[q=0, r=0]\n" +
            "Position[q=0, r=1]\n" +
            "Position[q=0, r=2]\n" +
            "Position[q=0, r=3]\n" +
            "Position[q=1, r=0]\n" +
            "Position[q=1, r=1]\n" +
            "Position[q=1, r=2]\n" +
            "Position[q=1, r=3]\n" +
            "Position[q=2, r=0]\n" +
            "Position[q=2, r=1]\n" +
            "Position[q=2, r=2]\n" +
            "Position[q=2, r=3]\n" +
            "Position[q=3, r=0]\n" +
            "Position[q=3, r=1]\n" +
            "Position[q=3, r=2]\n" +
            "Position[q=3, r=3]\n";
    Assert.assertEquals(checkAllPositions, log.toString());
  }

  //checking for all possible locations for potential moves for a larger board
  @Test
  public void testStrategyMaxCapture2() {
    StringBuilder log = new StringBuilder();
    MockReversiSquare model = new MockReversiSquare(log, 4);
    IReversiStrategy strategy1 = new MaximumCaptureStrategy();
    List<IPosition> posns = strategy1.choosePositions(model, Cell.BLACK);
    strategy1.chooseBestPosition(posns);
    String checkAllPositions = "Position[q=0, r=0]\n" +
            "Position[q=0, r=1]\n" +
            "Position[q=0, r=2]\n" +
            "Position[q=0, r=3]\n" +
            "Position[q=0, r=4]\n" +
            "Position[q=0, r=5]\n" +
            "Position[q=0, r=6]\n" +
            "Position[q=0, r=7]\n" +
            "Position[q=1, r=0]\n" +
            "Position[q=1, r=1]\n" +
            "Position[q=1, r=2]\n" +
            "Position[q=1, r=3]\n" +
            "Position[q=1, r=4]\n" +
            "Position[q=1, r=5]\n" +
            "Position[q=1, r=6]\n" +
            "Position[q=1, r=7]\n" +
            "Position[q=2, r=0]\n" +
            "Position[q=2, r=1]\n" +
            "Position[q=2, r=2]\n" +
            "Position[q=2, r=3]\n" +
            "Position[q=2, r=4]\n" +
            "Position[q=2, r=5]\n" +
            "Position[q=2, r=6]\n" +
            "Position[q=2, r=7]\n" +
            "Position[q=3, r=0]\n" +
            "Position[q=3, r=1]\n" +
            "Position[q=3, r=2]\n" +
            "Position[q=3, r=3]\n" +
            "Position[q=3, r=4]\n" +
            "Position[q=3, r=5]\n" +
            "Position[q=3, r=6]\n" +
            "Position[q=3, r=7]\n" +
            "Position[q=4, r=0]\n" +
            "Position[q=4, r=1]\n" +
            "Position[q=4, r=2]\n" +
            "Position[q=4, r=3]\n" +
            "Position[q=4, r=4]\n" +
            "Position[q=4, r=5]\n" +
            "Position[q=4, r=6]\n" +
            "Position[q=4, r=7]\n" +
            "Position[q=5, r=0]\n" +
            "Position[q=5, r=1]\n" +
            "Position[q=5, r=2]\n" +
            "Position[q=5, r=3]\n" +
            "Position[q=5, r=4]\n" +
            "Position[q=5, r=5]\n" +
            "Position[q=5, r=6]\n" +
            "Position[q=5, r=7]\n" +
            "Position[q=6, r=0]\n" +
            "Position[q=6, r=1]\n" +
            "Position[q=6, r=2]\n" +
            "Position[q=6, r=3]\n" +
            "Position[q=6, r=4]\n" +
            "Position[q=6, r=5]\n" +
            "Position[q=6, r=6]\n" +
            "Position[q=6, r=7]\n" +
            "Position[q=7, r=0]\n" +
            "Position[q=7, r=1]\n" +
            "Position[q=7, r=2]\n" +
            "Position[q=7, r=3]\n" +
            "Position[q=7, r=4]\n" +
            "Position[q=7, r=5]\n" +
            "Position[q=7, r=6]\n" +
            "Position[q=7, r=7]\n";
    Assert.assertEquals(checkAllPositions, log.toString());
  }

  //Checking if strategy 3 checks all four corners
  //the last 4 positions are the corners, the positions listed before
  //are checking if there are any legal moves on the board, if there are none,
  //pass the players turn
  @Test
  public void testStrategyCorners() {
    StringBuilder log = new StringBuilder();
    MockReversiSquare model = new MockReversiSquare(log, 3);
    IReversiStrategy strategy1 = new GoForCornersStrategy();
    List<IPosition> posns = strategy1.choosePositions(model, Cell.BLACK);
    strategy1.chooseBestPosition(posns);
    String checkAllPositions = "Position[q=0, r=0]\n" +
            "Position[q=0, r=1]\n" +
            "Position[q=0, r=2]\n" +
            "Position[q=0, r=3]\n" +
            "Position[q=0, r=4]\n" +
            "Position[q=0, r=5]\n" +
            "Position[q=1, r=0]\n" +
            "Position[q=1, r=1]\n" +
            "Position[q=1, r=2]\n" +
            "Position[q=1, r=3]\n" +
            "Position[q=1, r=4]\n" +
            "Position[q=1, r=5]\n" +
            "Position[q=2, r=0]\n" +
            "Position[q=2, r=1]\n" +
            "Position[q=2, r=2]\n" +
            "Position[q=2, r=3]\n" +
            "Position[q=2, r=4]\n" +
            "Position[q=2, r=5]\n" +
            "Position[q=3, r=0]\n" +
            "Position[q=3, r=1]\n" +
            "Position[q=3, r=2]\n" +
            "Position[q=3, r=3]\n" +
            "Position[q=3, r=4]\n" +
            "Position[q=3, r=5]\n" +
            "Position[q=4, r=0]\n" +
            "Position[q=4, r=1]\n" +
            "Position[q=4, r=2]\n" +
            "Position[q=4, r=3]\n" +
            "Position[q=4, r=4]\n" +
            "Position[q=4, r=5]\n" +
            "Position[q=5, r=0]\n" +
            "Position[q=5, r=1]\n" +
            "Position[q=5, r=2]\n" +
            "Position[q=5, r=3]\n" +
            "Position[q=5, r=4]\n" +
            "Position[q=5, r=5]\n";
    Assert.assertEquals(checkAllPositions, log.toString());
  }

  //change here ///////

  //testing that avoid corners strategy looks through all possible positions
  //only legal moves being the ones adjacent to the corners, meaning that there
  //are no moves for this strategy
  @Test
  public void testAvoidCorners() {
    StringBuilder log = new StringBuilder();
    MockReversiAvoidCornersSquare model = new MockReversiAvoidCornersSquare(log, 3);
    IReversiStrategy strategy1 = new AvoidNeighboringCornersStrategy();
    List<IPosition> posns = strategy1.choosePositions(model, Cell.BLACK);
    strategy1.chooseBestPosition(posns);
    String checkAllPositions = "Position[q=0, r=0] legal\n" +
            "Position[q=0, r=1] legal\n" +
            "Position[q=0, r=2] not legal\n" +
            "Position[q=0, r=3] not legal\n" +
            "Position[q=0, r=4] legal\n" +
            "Position[q=0, r=5] legal\n" +
            "Position[q=1, r=0] legal\n" +
            "Position[q=1, r=1] legal\n" +
            "Position[q=1, r=2] not legal\n" +
            "Position[q=1, r=3] not legal\n" +
            "Position[q=1, r=4] legal\n" +
            "Position[q=1, r=5] legal\n" +
            "Position[q=2, r=0] not legal\n" +
            "Position[q=2, r=1] not legal\n" +
            "Position[q=2, r=2] not legal\n" +
            "Position[q=2, r=3] not legal\n" +
            "Position[q=2, r=4] not legal\n" +
            "Position[q=2, r=5] not legal\n" +
            "Position[q=3, r=0] not legal\n" +
            "Position[q=3, r=1] not legal\n" +
            "Position[q=3, r=2] not legal\n" +
            "Position[q=3, r=3] not legal\n" +
            "Position[q=3, r=4] not legal\n" +
            "Position[q=3, r=5] not legal\n" +
            "Position[q=4, r=0] legal\n" +
            "Position[q=4, r=1] legal\n" +
            "Position[q=4, r=2] not legal\n" +
            "Position[q=4, r=3] not legal\n" +
            "Position[q=4, r=4] legal\n" +
            "Position[q=4, r=5] legal\n" +
            "Position[q=5, r=0] legal\n" +
            "Position[q=5, r=1] legal\n" +
            "Position[q=5, r=2] not legal\n" +
            "Position[q=5, r=3] not legal\n" +
            "Position[q=5, r=4] legal\n" +
            "Position[q=5, r=5] legal\n";
    Assert.assertEquals(checkAllPositions, log.toString());
  }

  //testing if the strategy chooses the position with the maximum amount of captures.
  @Test
  public void testMultipleFlipsMovingOver() {
    IReversiModel model = new SquareReversiModel(4);
    model.startGame();
    model.executeMove(new SquarePosition(5,3));
    model.executeMove(new SquarePosition(3,2));
    model.executeMove(new SquarePosition(2,1));
    Assert.assertEquals(1, model.countCaptures(new SquarePosition(3,1), Cell.WHITE));
    Assert.assertEquals(1, model.countCaptures(new SquarePosition(5,4), Cell.WHITE));
    Assert.assertEquals(2, model.countCaptures(new SquarePosition(6,3), Cell.WHITE));
    MaximumCaptureStrategy str = new MaximumCaptureStrategy();
    List<IPosition> posns = str.choosePositions(model, Cell.WHITE);
    Assert.assertEquals(Optional.of(new SquarePosition(6,3)), str.chooseBestPosition(posns));
  }

  //testing the MaxCapturesStrategy correctly handles a tie
  @Test
  public void testTieGoesUpperLeft() {
    IReversiModel model = new SquareReversiModel(4);
    model.startGame();
    MaximumCaptureStrategy str = new MaximumCaptureStrategy();
    List<IPosition> posns = str.choosePositions(model, Cell.BLACK);
    //goes to upper leftmost
    Assert.assertEquals(Optional.of(new SquarePosition(4,2)), str.chooseBestPosition(posns));
  }

  //testing go for corners strategy with one legal open corner
  @Test
  public void testGoForOnlyLegalCorner() {
    IReversiModel model = new SquareReversiModel(2);
    model.startGame();
    model.executeMove(new SquarePosition(3,1)); //b
    model.executeMove(new SquarePosition(3,0)); //w
    model.executeMove(new SquarePosition(0,2)); //b
    IView view = new SquareReversiView(model);
    view.renderTextualView();
    GoForCornersStrategy str = new GoForCornersStrategy();
    List<IPosition> posns = str.choosePositions(model, Cell.WHITE);

    Assert.assertEquals(Optional.of(new SquarePosition(0,3)), str.chooseBestPosition(posns));
  }

  //testing avoiding the adjacent corners when the only moves are to the adjacent corners
  @Test
  public void testAvoidCornersHard() {
    IReversiModel model = new SquareReversiModel(2);
    model.startGame();
    IView view = new SquareReversiView(model);
    view.renderTextualView();
    AvoidNeighboringCornersStrategy str = new AvoidNeighboringCornersStrategy();
    List<IPosition> posns = str.choosePositions(model, Cell.BLACK);
    Assert.assertEquals(Optional.empty(), str.chooseBestPosition(posns));
  }

  //goes to the upper leftmost legal move that is not an adjacent corner
  @Test
  public void testAvoidCornersEasier() {
    IReversiModel model = new SquareReversiModel(3);
    model.startGame();
    IView view = new SquareReversiView(model);
    view.renderTextualView();
    AvoidNeighboringCornersStrategy str = new AvoidNeighboringCornersStrategy();
    IReversiStrategy str2 = new MaximumCaptureStrategy();
    IReversiStrategy strategy = new TryTwo(str, str2);
    List<IPosition> posns = strategy.choosePositions(model, Cell.BLACK);
    Assert.assertEquals(Optional.of(new SquarePosition(3,1)),
            strategy.chooseBestPosition(posns));
  }

  //there are no legal moves for the avoid corners strategy
  //so it uses the maximum capture strategy.
  @Test
  public void testCantAvoidNeighboringCornersButCanMaxCapture() {
    IReversiModel model = new SquareReversiModel(2);
    model.startGame();
    IView view = new SquareReversiView(model);
    view.renderTextualView();
    AvoidNeighboringCornersStrategy str = new AvoidNeighboringCornersStrategy();
    IReversiStrategy str2 = new MaximumCaptureStrategy();
    IReversiStrategy strategy = new TryTwo(str, str2);
    List<IPosition> posns = strategy.choosePositions(model, Cell.BLACK);
    Assert.assertEquals(Optional.of(new SquarePosition(2,0)),
            strategy.chooseBestPosition(posns));

  }

  //this mock lies about the amount of captures a position has the strategy follows it
  @Test
  public void testMaxStrategyLyingMock() {
    StringBuilder log = new StringBuilder();
    IReversiModel model2 = new SquareReversiModel(4);
    model2.startGame();
    MockReversiLieCapturesSquare model = new MockReversiLieCapturesSquare(model2, log);
    IReversiStrategy strategy1 = new MaximumCaptureStrategy();
    List<IPosition> posns = strategy1.choosePositions(model, Cell.BLACK);
    Assert.assertEquals(Optional.of(new SquarePosition(5,3)),
            strategy1.chooseBestPosition(posns));
    String expected = "0\n" +
            "0\n" +
            "0\n" +
            "5\n";
    Assert.assertEquals(expected, log.toString());
  }

  //testing go for corner when there are no corners available
  @Test
  public void testGoForCornersWhenNoCornersAvailable() {
    IReversiModel model = new SquareReversiModel(2);
    model.startGame();
    IReversiStrategy str = new GoForCornersStrategy();
    List<IPosition> posns = str.choosePositions(model, Cell.BLACK);

    Assert.assertEquals(Optional.empty(), str.chooseBestPosition(posns));
  }

  //testing go for corner when there are no corners available
  //so goes to the maximum capture
  @Test
  public void testGoForCornersWhenNoCornersAvailableSoGoesToMax() {
    IReversiModel model = new SquareReversiModel(4);
    IReversiStrategy str = new GoForCornersStrategy();
    IReversiStrategy str2 = new MaximumCaptureStrategy();
    IReversiStrategy tt = new TryTwo(str, str2);
    model.startGame();
    List<IPosition> posns = tt.choosePositions(model, Cell.BLACK);
    //goes to upper leftmost position for ties
    Assert.assertEquals(Optional.of(new SquarePosition(4,2)), tt.chooseBestPosition(posns));
  }

  //testing maximum capture strategy when the game is over
  //should return an empty list
  @Test
  public void testMaximumCaptureStrategyEndGame() {
    IReversiModel model = new SquareReversiModel(2);
    IReversiStrategy str = new MaximumCaptureStrategy();
    model.startGame();
    Assert.assertTrue(model.isALegalMove(new SquarePosition(3, 1), Cell.BLACK));
    model.executeMove(new SquarePosition(3,1)); //b
    model.executeMove(new SquarePosition(3,0)); //w
    model.executeMove(new SquarePosition(0,2)); //b
    model.executeMove(new SquarePosition(0,3)); //w
    model.executeMove(new SquarePosition(1,3)); //b
    model.executeMove(new SquarePosition(2,3)); //w
    model.executeMove(new SquarePosition(3,2)); //b
    model.executeMove(new SquarePosition(3,3)); //w
    model.executeMove(new SquarePosition(2,0)); //b
    model.executeMove(new SquarePosition(0,0)); //w
    model.executeMove(new SquarePosition(1,0)); //b
    model.executeMove(new SquarePosition(0,1)); //w
    List<IPosition> posns = str.choosePositions(model, Cell.WHITE);
    Assert.assertEquals(0, posns.size());

    Assert.assertEquals(Optional.empty(), str.chooseBestPosition(posns));
  }

  //testing maximum capture strategy when the game is one move away from being over
  //and there is only one valid position
  @Test
  public void testMaximumCaptureStrategyAlmostEndGame() {
    IReversiModel model = new SquareReversiModel(2);
    IReversiStrategy str = new MaximumCaptureStrategy();
    model.startGame();
    model.executeMove(new SquarePosition(3,1)); //b
    model.executeMove(new SquarePosition(3,0)); //w
    model.executeMove(new SquarePosition(0,2)); //b
    model.executeMove(new SquarePosition(0,3)); //w
    model.executeMove(new SquarePosition(1,3)); //b
    model.executeMove(new SquarePosition(2,3)); //w
    model.executeMove(new SquarePosition(3,2)); //b
    model.executeMove(new SquarePosition(3,3)); //w
    model.executeMove(new SquarePosition(2,0)); //b
    model.executeMove(new SquarePosition(0,0)); //w
    model.executeMove(new SquarePosition(1,0)); //b
    List<IPosition> posns = str.choosePositions(model, Cell.WHITE);
    Assert.assertEquals(1, posns.size());

    Assert.assertEquals(Optional.of(new SquarePosition(0,1)),
            str.chooseBestPosition(posns));
  }

  //testing maximum capture strategy when the game is more than halfway through the game
  @Test
  public void testMaximumCaptureStrategyAlmostEndGame2() {
    IReversiModel model = new SquareReversiModel(2);
    SquareReversiView view = new SquareReversiView(model);
    IReversiStrategy str = new MaximumCaptureStrategy();
    model.startGame();
    Assert.assertTrue(model.isALegalMove(new SquarePosition(3, 1), Cell.BLACK));
    model.executeMove(new SquarePosition(3,1)); //b
    view.renderTextualView();
    model.executeMove(new SquarePosition(3,0)); //w
    view.renderTextualView();
    model.executeMove(new SquarePosition(0,2)); //b
    view.renderTextualView();
    model.executeMove(new SquarePosition(0,3)); //w
    view.renderTextualView();
    model.executeMove(new SquarePosition(1,3)); //b
    view.renderTextualView();
    Assert.assertTrue(model.isALegalMove(new SquarePosition(3, 2), Cell.WHITE));
    List<IPosition> posns = str.choosePositions(model, Cell.WHITE);
    System.out.println(posns);
    Assert.assertEquals(2, posns.size());

    Assert.assertEquals(Optional.of(new SquarePosition(0,1)),
            str.chooseBestPosition(posns));
  }

  /*
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
    Assert.assertEquals(Optional.of(new Position(-1, 2, -1)), str.chooseBestPosition(posns));
  }
   */
}
