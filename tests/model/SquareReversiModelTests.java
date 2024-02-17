package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import view.IView;
import view.SquareReversiView;


/**
 * Tests for the SquareReversiModel.
 */
public class SquareReversiModelTests {
  private SquareReversiModel model;

  @Before
  public void setUp() {
    model = new SquareReversiModel(4);
  }

  @Test
  public void testInitializeCells() {
    IReversiModel model = new SquareReversiModel(2);
    model.startGame();
    Assert.assertEquals(2, model.getScore(Cell.BLACK));
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new SquarePosition(1,1)).get());
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new SquarePosition(2,2)).get());

    Assert.assertEquals(2, model.getScore(Cell.WHITE));
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new SquarePosition(2,1)).get());
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new SquarePosition(1,2)).get());
  }

  //valid execute move, 1 moves
  @Test
  public void testValidExecuteMoveMovingOverWhite() {
    model.startGame();
    Assert.assertFalse(model.getHexAt(new SquarePosition(5,3)).isPresent());
    Assert.assertTrue(model.isALegalMove(new SquarePosition(5,3), Cell.BLACK));
    Assert.assertEquals(1, model.countCaptures(new SquarePosition(5,3), Cell.BLACK));
    model.executeMove(new SquarePosition(5,3));
    Assert.assertTrue(model.getHexAt(new SquarePosition(5,3)).isPresent());
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new SquarePosition(5,3)).get());

    //seeing if the opposite cell was flipped to other color
    Assert.assertTrue(model.getHexAt(new SquarePosition(4,3)).isPresent());
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new SquarePosition(4,3)).get());
  }

  //valid execute move, 2 moves in a row
  //getScore should update accordingly
  @Test
  public void testExecuteMoveMovingOverBlack() {
    SquareReversiView view = new SquareReversiView(model);

    model.startGame();
    Assert.assertEquals(2, model.getScore(Cell.BLACK));
    Assert.assertEquals(2, model.getScore(Cell.WHITE));

    model.executeMove(new SquarePosition(5,3)); //black
    view.renderTextualView();
    Assert.assertEquals(4, model.getScore(Cell.BLACK));
    Assert.assertEquals(1, model.getScore(Cell.WHITE));

    model.executeMove(new SquarePosition(5,4)); //white
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new SquarePosition(4,4)).get());
    Assert.assertEquals(3, model.getScore(Cell.BLACK));
    Assert.assertEquals(3, model.getScore(Cell.WHITE));

    //cells turned over from the previous move should still be black because White moving
    //to (5,4) should not have flipped any of them
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new SquarePosition(4,3)).get());

    Assert.assertFalse(model.isGameOver());
  }

  //testing flipping multiple cells
  @Test
  public void testMultipleFlipsMovingOver() {
    SquareReversiView view = new SquareReversiView(model);

    model.startGame();
    model.executeMove(new SquarePosition(5,3)); //black
    model.executeMove(new SquarePosition(5,4)); //white
    view.renderTextualView();

    Assert.assertEquals(3, model.getScore(Cell.WHITE));
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new SquarePosition(3, 4)).get());
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new SquarePosition(4, 4)).get());

    Assert.assertTrue(model.isALegalMove(new SquarePosition(5,5), Cell.BLACK));
    Assert.assertEquals(2, model.countCaptures(new SquarePosition(5,5), Cell.BLACK));

    model.executeMove(new SquarePosition(5,5));
    view.renderTextualView();

    Assert.assertEquals(Cell.BLACK, model.getHexAt(new SquarePosition(5,5)).get());

    //checks if the others flipped
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new SquarePosition(5,4)).get());
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new SquarePosition(4,4)).get());
  }

  //testing for game over if both players pass their turn
  @Test
  public void testPassTurnTwiceGameOver() {
    model.startGame();
    model.passTurn();
    Assert.assertEquals(Cell.WHITE, model.getCurrentTurn());
    model.passTurn();
    Assert.assertEquals(Cell.BLACK, model.getCurrentTurn());
    Assert.assertTrue(model.isGameOver());
    Assert.assertEquals(Optional.empty(), model.getWinner());
  }

  @Test
  //testing for equality with creating a board in SquareReversiModel + in the SquareBoard class
  public void testGetBoardAndGetBoardCellsEquality() {
    SquareReversiModel smallBoardModel = new SquareReversiModel(2);
    SquareBoard smallBoard = new SquareBoard(2);
    Assert.assertEquals(smallBoard.getBoardCells(), smallBoardModel.getBoard());
    Assert.assertEquals(4, smallBoard.getBoardCells().length);
    Assert.assertEquals(4, smallBoardModel.getBoard().length);
    Assert.assertEquals(smallBoard.getBoardCells().length, smallBoardModel.getBoard().length);
  }

  @Test
  //testing isGameOver is false when there are still legal moves left on the board
  public void testGameOverFalseSinceStillMovesLeft() {
    model.startGame();
    model.executeMove(new SquarePosition(5,3));
    model.executeMove(new SquarePosition(5,4));
    Assert.assertFalse(model.isGameOver());
  }

  @Test
  //test when game is only played halfway and isGameOver should be false
  //since still legal moves left
  public void testPlayingGameMidwayWithNoPassTurns() {
    model.startGame();
    IView view = new SquareReversiView(model);
    view.renderTextualView();
    model.executeMove(new SquarePosition(5,3)); //b
    view.renderTextualView();
    model.executeMove(new SquarePosition(5,4)); //w
    view.renderTextualView();
    model.executeMove(new SquarePosition(5,5)); //b
    view.renderTextualView();
    model.executeMove(new SquarePosition(3,2)); //w
    view.renderTextualView();
    model.executeMove(new SquarePosition(2,4)); //b
    view.renderTextualView();
    model.executeMove(new SquarePosition(6,5)); //w
    view.renderTextualView();
    Assert.assertTrue(model.hasLegalMove(Cell.BLACK));
    Assert.assertTrue(model.hasLegalMove(Cell.WHITE));
    Assert.assertFalse(model.isGameOver());
  }

  @Test
  //test with no passes and White wins
  public void testPlayingGameWithNoPassTurnsUntilNoMovesLeftWhiteWins() {
    IReversiModel model2 = new SquareReversiModel(2);
    model2.startGame();
    IView view = new SquareReversiView(model2);
    Assert.assertEquals(2, model2.getScore(Cell.BLACK));
    Assert.assertEquals(2, model2.getScore(Cell.WHITE));
    view.renderTextualView();
    model2.executeMove(new SquarePosition(3,1)); //b
    view.renderTextualView();
    model2.executeMove(new SquarePosition(3,0)); //w
    view.renderTextualView();
    model2.executeMove(new SquarePosition(0,2)); //b
    view.renderTextualView();
    model2.executeMove(new SquarePosition(0,3)); //w
    view.renderTextualView();
    model2.executeMove(new SquarePosition(1,3)); //b
    view.renderTextualView();
    model2.executeMove(new SquarePosition(2,3)); //w
    view.renderTextualView();
    model2.executeMove(new SquarePosition(3,2)); //b
    view.renderTextualView();
    model2.executeMove(new SquarePosition(3,3)); //w
    view.renderTextualView();
    model2.executeMove(new SquarePosition(2,0)); //b
    view.renderTextualView();
    model2.executeMove(new SquarePosition(0,0)); //w
    view.renderTextualView();
    model2.executeMove(new SquarePosition(1,0)); //b
    view.renderTextualView();
    Assert.assertTrue(model2.isALegalMove(new SquarePosition(0,1), Cell.WHITE));
    Assert.assertEquals(4,
            model2.countCaptures(new SquarePosition(0,1), Cell.WHITE));
    model2.executeMove(new SquarePosition(0,1)); //w
    view.renderTextualView();
    Assert.assertTrue(model2.isGameOver());
    Assert.assertEquals(2, model2.getScore(Cell.BLACK));
    Assert.assertEquals(14, model2.getScore(Cell.WHITE));
    Assert.assertEquals(Cell.WHITE, model2.getWinner().get());
  }

  @Test
  //test where same score ends up in a tie
  public void testPlayingGameUntilStalemate() {
    IReversiModel model2 = new SquareReversiModel(2);
    model2.startGame();
    IView view = new SquareReversiView(model2);
    Assert.assertEquals(2, model2.getScore(Cell.BLACK));
    Assert.assertEquals(2, model2.getScore(Cell.WHITE));
    view.renderTextualView();
    model2.executeMove(new SquarePosition(3,1)); //b
    view.renderTextualView();
    model2.executeMove(new SquarePosition(3,2)); //w
    view.renderTextualView();
    model2.passTurn(); //b
    view.renderTextualView();
    model2.executeMove(new SquarePosition(3,0)); //w
    view.renderTextualView();
    model2.executeMove(new SquarePosition(3,3)); //b
    view.renderTextualView();
    model2.executeMove(new SquarePosition(0,1)); //w
    view.renderTextualView();
    model2.executeMove(new SquarePosition(0,0)); //b
    view.renderTextualView();
    model2.executeMove(new SquarePosition(1,0)); //w
    view.renderTextualView();
    model2.executeMove(new SquarePosition(2,0)); //b
    view.renderTextualView();
    Assert.assertFalse(model.hasLegalMove(Cell.WHITE));
    model2.passTurn(); //w
    view.renderTextualView();
    Assert.assertEquals(3,
            model2.countCaptures(new SquarePosition(0,2), Cell.BLACK));
    model2.executeMove(new SquarePosition(0,2)); //b
    view.renderTextualView();
    model2.executeMove(new SquarePosition(0,3)); //w
    view.renderTextualView();
    model2.executeMove(new SquarePosition(1,3)); //b
    view.renderTextualView();
    model2.executeMove(new SquarePosition(2,3)); //b
    view.renderTextualView();
    Assert.assertTrue(model2.isGameOver());
    Assert.assertEquals(8, model2.getScore(Cell.BLACK));
    Assert.assertEquals(8, model2.getScore(Cell.WHITE));
    Assert.assertFalse(model2.getWinner().isPresent());
  }

  @Test
  //test game is over when both players pass their turn consecutively
  public void testPlayingGameWithTwoPassTurnsSoGameOver() {
    IReversiModel model2 = new SquareReversiModel(4);
    IView view = new SquareReversiView(model2);
    model2.startGame();
    view.renderTextualView();
    model2.executeMove(new SquarePosition(5,3)); //b
    view.renderTextualView();
    model2.passTurn(); //w
    view.renderTextualView();
    model2.passTurn(); //b --> if both pass, game ends!!!!!!!!!!!
    view.renderTextualView();
    Assert.assertTrue(model2.hasLegalMove(Cell.BLACK));
    Assert.assertTrue(model2.isGameOver());
    Assert.assertEquals(4, model2.getScore(Cell.BLACK));
    Assert.assertEquals(1, model2.getScore(Cell.WHITE));
    Assert.assertEquals(Cell.BLACK, model2.getWinner().get());
  }

  @Test
  //test when Black only passes its turn and White only plays and wins
  //and game is not over after each pass because only Black passes (White never passes)
  public void testPlayingGameWithOnlyBlackPassesAndWhiteWins() {
    IReversiModel model2 = new SquareReversiModel(2);
    IView view = new SquareReversiView(model2);
    model2.startGame();
    view.renderTextualView();
    Assert.assertEquals(Cell.BLACK, model2.getCurrentTurn());
    model2.passTurn(); //b
    view.renderTextualView();
    Assert.assertEquals(Cell.WHITE, model2.getCurrentTurn());
    model2.executeMove(new SquarePosition(0,1)); //w
    view.renderTextualView();
    Assert.assertEquals(Cell.BLACK, model2.getCurrentTurn());
    model2.passTurn(); //b
    view.renderTextualView();
    Assert.assertEquals(Cell.WHITE, model2.getCurrentTurn());
    model2.executeMove(new SquarePosition(3,2)); //w
    view.renderTextualView();
    Assert.assertFalse(model.hasLegalMove(Cell.BLACK));
    Assert.assertTrue(model2.isGameOver());
    Assert.assertEquals(0, model2.getScore(Cell.BLACK));
    Assert.assertEquals(6, model2.getScore(Cell.WHITE));
    Assert.assertEquals(Cell.WHITE, model2.getWinner().get());
  }

  ///////////////// Exceptions /////////////////

  //creating a SquareReversiModel with zero size
  @Test(expected = IllegalArgumentException.class)
  public void testReversiModelWithSizeZero() {
    IReversiModel badModel = new SquareReversiModel(0);
    badModel.startGame();
  }

  //creating a SquareReversiModel with negative size
  @Test(expected = IllegalArgumentException.class)
  public void testReversiModelWithSizeNegative() {
    IReversiModel badModel = new SquareReversiModel(-1);
    badModel.startGame();
  }

  //cannot execute a move outside board
  @Test (expected = IllegalArgumentException.class)
  public void testExecuteMoveOutsideBoard() {
    model.startGame();
    model.executeMove(new SquarePosition(80, 80));
  }

  //cannot execute a move to a position that won't flip any cells
  @Test (expected = IllegalArgumentException.class)
  public void testExecuteInvalidMove() {
    model.startGame();
    model.executeMove(new SquarePosition(0, 0));
  }

  //cannot execute a move on a filled cell
  @Test (expected = IllegalArgumentException.class)
  public void testExecuteMoveOnFilledCell() {
    model.startGame();
    model.executeMove(new SquarePosition(5,3));
    Assert.assertTrue(model.getHexAt(new SquarePosition(5,3)).isPresent());
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new SquarePosition(5,3)).get());
    //seeing if the opposite cell was flipped to other color
    Assert.assertTrue(model.getHexAt(new SquarePosition(4,3)).isPresent());
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new SquarePosition(4,3)).get());
    model.executeMove(new SquarePosition(5,3));
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new SquarePosition(4,3)).get());
  }

  //cannot move a cell if the game is already over
  @Test(expected = IllegalStateException.class)
  public void testCannotMoveCellAfterGameOver() {
    IReversiModel model2 = new SquareReversiModel(2);
    IView view = new SquareReversiView(model2);
    model2.startGame();
    view.renderTextualView();
    Assert.assertEquals(Cell.BLACK, model2.getCurrentTurn());
    model2.passTurn(); //b
    view.renderTextualView();
    Assert.assertEquals(Cell.WHITE, model2.getCurrentTurn());
    model2.executeMove(new SquarePosition(0,1)); //w
    view.renderTextualView();
    Assert.assertEquals(Cell.BLACK, model2.getCurrentTurn());
    model2.passTurn(); //b
    view.renderTextualView();
    Assert.assertEquals(Cell.WHITE, model2.getCurrentTurn());
    model2.executeMove(new SquarePosition(3,2)); //w
    view.renderTextualView();
    model2.executeMove(new SquarePosition(0,1)); //b
    Assert.assertTrue(model2.isGameOver());
    Assert.assertEquals(Cell.WHITE, model2.getWinner().get());
  }

  //testing passing turn if the game is already over
  /*@Test(expected = IllegalStateException.class)
  public void testGameOverCannotPassTurn() {
    model.startGame();
    model.passTurn();
    model.passTurn();
    model.passTurn();
  }
   */
}
