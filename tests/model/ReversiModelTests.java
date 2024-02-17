package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import view.IView;
import view.ReversiView;

/**
 * Tests for the ReversiModel.
 */

public class ReversiModelTests {
  private ReversiModel model;

  @Before
  public void setUp() {
    model = new ReversiModel(3);
  }

  //valid execute move, 1 moves
  @Test
  public void testValidExecuteMoveMovingOverWhite() {
    model.startGame();
    Assert.assertEquals(1,model.countCaptures(new Position(1, 1, -2),Cell.BLACK));
    Assert.assertTrue(model.isALegalMove(new Position(1, 1, -2), Cell.BLACK));
    model.executeMove(new Position(1, 1, -2));
    Assert.assertTrue(model.getHexAt(new Position(1, 1, -2)).isPresent());
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new Position(1, 1, -2)).get());

    //seeing if the opposite cell was flipped to other color
    Assert.assertTrue(model.getHexAt(new Position(0, 1, -1)).isPresent());
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new Position(0, 1, -1)).get());
  }

  //cannot execute a move on a filled cell
  @Test
  public void testExecuteMoveOnFilledCell() {
    model.startGame();
    model.executeMove(new Position(1, 1, -2));
    Assert.assertTrue(model.getHexAt(new Position(1, 1, -2)).isPresent());
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new Position(1, 1, -2)).get());
    //seeing if the opposite cell was flipped to other color
    Assert.assertTrue(model.getHexAt(new Position(0, 1, -1)).isPresent());
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new Position(0, 1, -1)).get());
    model.executeMove(new Position(1, 1, -2));
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new Position(0, 1, -1)).get());
  }

  //cannot execute a move outside board
  @Test(expected = IllegalArgumentException.class)
  public void testExecuteMoveOutsideBoard() {
    model.startGame();
    model.executeMove(new Position(80, 80, -160));

  }

  //valid execute move, 2 moves in a row
  @Test
  public void testExecuteMoveMovingOverBlack() {
    model.startGame();
    model.executeMove(new Position(1, 1, -2));
    model.executeMove(new Position(-1, -1, 2));
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new Position(-1, -1, 2)).get());
    //if opposite cell flipped
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new Position(0, -1, 1)).get());
  }

  //testing flipping multiple cells
  @Test
  public void testMultipleFlipsMovingOver() {
    IReversiModel model = new ReversiModel(4);
    model.startGame();
    model.executeMove(new Position(1, 1, -2));
    model.executeMove(new Position(-1, -1, 2));
    Assert.assertEquals(2,model.countCaptures(new Position(-1, -2, 3),Cell.BLACK));
    model.executeMove(new Position(-1, -2, 3));
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new Position(-1, -2, 3)).get());
    //checks if the others flipped
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new Position(-1, 0, 1)).get());
    Assert.assertEquals(Cell.BLACK, model.getHexAt(new Position(-1, -1, 2)).get());
  }

  //starting the game twice
  @Test(expected = IllegalStateException.class)
  public void testStartGameAlreadyStarted() {
    model.startGame();
    model.startGame();
  }

  //executing the move without game started
  @Test(expected = IllegalStateException.class)
  public void testExecuteMoveNoGameStarted() {
    model.executeMove(new Position(1, 1, -2));

  }

  //game over not game started
  @Test(expected = IllegalStateException.class)
  public void testGameOverNoGameStarted() {
    model.isGameOver();
  }

  //score without game started
  @Test(expected = IllegalStateException.class)
  public void testGetScoreNoGameStarted() {
    model.getScore(Cell.BLACK);
  }

  //passTurn without game started
  @Test(expected = IllegalStateException.class)
  public void testPassTurnNoGameStarted() {
    model.passTurn();
  }

  //getting winner before game started
  @Test(expected = IllegalStateException.class)
  public void testGetWinnerNoGameStarted() {
    model.getWinner();
  }

  //getting teh cell without game started
  @Test(expected = IllegalStateException.class)
  public void testGetHexAtNoGameStarted() {
    model.getHexAt(new Position(1, 1, -2));
  }

  //testing the score at the start of the game
  @Test
  public void testScoreAtStartGame() {
    model.startGame();
    Assert.assertEquals(3, model.getScore(Cell.BLACK));
    Assert.assertEquals(3, model.getScore(Cell.WHITE));
  }

  @Test
  //testing for equality with creating a board in the Model + in the Board class
  public void testGetBoardAndGetBoardCellsEquality() {
    ReversiModel smallBoardModel = new ReversiModel(2);
    Board smallBoard = new Board(2);
    Assert.assertEquals(smallBoard.getBoardCells(), smallBoardModel.getBoard());
    Assert.assertEquals(5, smallBoard.getBoardCells().length);
    Assert.assertEquals(5, smallBoardModel.getBoard().length);
    Assert.assertEquals(smallBoard.getBoardCells().length, smallBoardModel.getBoard().length);
  }


  @Test
  //testing isGameOver is false when there are still legal moves left on the board
  public void testGameOverFalseSinceStillMovesLeft() {
    model.startGame();
    model.executeMove(new Position(1, 1, -2));
    model.executeMove(new Position(-1, -1, 2));
    Assert.assertFalse(model.isGameOver());
  }


  //testing moving a cell if the game is already over
  @Test(expected = IllegalStateException.class)
  public void testGameOverTrue2() {
    IReversiModel model2 = new ReversiModel(2);
    model2.startGame();
    IView view = new ReversiView(model2);
    view.renderTextualView();
    model2.executeMove(new Position(-1, -1, 2)); //b
    view.renderTextualView();
    model2.executeMove(new Position(1, 1, -2)); //w
    view.renderTextualView();
    model2.passTurn(); //b
    view.renderTextualView();
    model2.executeMove(new Position(1, -2, 1)); //w, not a legal
    model2.passTurn();
    view.renderTextualView();
    model2.executeMove(new Position(2, -1, -1)); //b
    view.renderTextualView();
    model2.executeMove(new Position(-2, 1, 1)); //w
    view.renderTextualView();
    model2.executeMove(new Position(-1, 2, -1)); //b
    model2.executeMove(new Position(1, -2, 1)); //w
    view.renderTextualView();
    Assert.assertTrue(model2.isGameOver());
    Assert.assertEquals(Cell.WHITE, model2.getWinner().get());

  }

  @Test(expected = IllegalArgumentException.class)
  //exception thrown since size given to the model is negative (an invalid number)
  public void testExceptionSinceSizeIsNegative() {
    ReversiModel negSizeModel = new ReversiModel(-3);
  }

  @Test(expected = IllegalArgumentException.class)
  //exception thrown since size given to the model is zero (an invalid number)
  public void testExceptionSinceSizeIsZero() {
    ReversiModel zeroSizeModel = new ReversiModel(0);
  }

  @Test(expected = IllegalStateException.class)
  //exception thrown since game is already started, but trying to start game again
  public void testStartGameExceptionSinceGameIsAlreadyStarted() {
    model.passTurn();
  }

  @Test(expected = IllegalStateException.class)
  //exception thrown for executeMove since startGame is not called
  public void testExecuteMoveExceptionSinceGameIsNotStarted() {
    model.executeMove(new Position(-1, 1, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  //exception thrown for executeMove since coordinates of given Position do not add to zero
  public void testExecuteMoveExceptionSincePositionCoordinatesAllPosDoNotAddToZero() {
    model.startGame();
    model.executeMove(new Position(60, 60, 40));
  }

  @Test(expected = IllegalArgumentException.class)
  //exception thrown for executeMove since coordinates of given Position do not add to zero
  public void testExecuteMoveExceptionSincePositionCoordinatesAllNegDoNotAddToZero() {
    model.startGame();
    model.executeMove(new Position(-60, -60, -40));
  }

  @Test(expected = IllegalArgumentException.class)
  //exception thrown for executeMove since given Position is not on the board
  public void testExecuteMoveExceptionSinceositionCoordinatesAreNotOnBoard() {
    model.startGame();
    model.executeMove(new Position(-60, 60, 0));
  }

  @Test(expected = IllegalStateException.class)
  //exception thrown for getCurrentTurn since startGame is not called
  public void testGetCurrentTurnExceptionSinceGameIsNotStarted() {
    model.getCurrentTurn();
  }

  @Test(expected = IllegalStateException.class)
  //exception thrown for passTurn since startGame is not called
  public void testPassTurnExceptionSinceGameIsNotStarted() {
    model.passTurn();
  }

  @Test(expected = IllegalStateException.class)
  //exception thrown for isGameOver since startGame is not called
  public void testIsGameOverExceptionSinceGameIsNotStarted() {
    model.isGameOver();
  }

  @Test(expected = IllegalStateException.class)
  //exception thrown for getScore since startGame is not called
  public void testGetScoreExceptionSinceGameIsNotStarted() {
    model.getScore(Cell.BLACK);
  }


  @Test(expected = IllegalStateException.class)
  //exception thrown for getWinner since startGame is not called
  public void testGetWinnerExceptionSinceGameIsNotStarted() {
    model.getWinner();
  }

  @Test(expected = IllegalStateException.class)
  //exception thrown for getWinner since game is not over
  public void testGetWinnerExceptionSinceGameIsNotOver() {
    model.startGame();
    model.getWinner();
  }

  @Test(expected = IllegalStateException.class)
  //exception thrown for getHexAt since startGame is not called
  public void testGetHexAtExceptionSinceGameIsNotStarted() {
    model.getHexAt(new Position(-1, 1, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  //exception thrown for getHexAt since positions coordinates do not add up to zero
  public void testGetHexAtExceptionSincePositionCoordinatesAllPosDoNotAddToZero() {
    //maybe put this for testing Position class??
    model.startGame();
    model.getHexAt(new Position(60, 60, 40));
  }

  @Test(expected = IllegalArgumentException.class)
  //exception thrown for getHexAt since positions coordinates do not add up to zero
  public void testGetHexAtExceptionSincePositionCoordinatesAllNegDoNotAddToZero() {
    //maybe put this for testing Position class??
    model.startGame();
    model.getHexAt(new Position(-60, -60, -40));
  }

  @Test(expected = IllegalArgumentException.class)
  //exception thrown for getHexAt since positions coordinates are not on board
  public void testGetHexAtExceptionSincePositionCoordinatesAreNotOnBoard() {
    model.startGame();
    model.getHexAt(new Position(-60, 60, 0));
  }

  //creating a reversiModel with zero size
  @Test(expected = IllegalArgumentException.class)
  public void testReversiModelWithSizeZero() {
    IReversiModel badModel = new ReversiModel(0);
    badModel.startGame();
  }

  //creating a reversiModel with negative size
  @Test(expected = IllegalArgumentException.class)
  public void testReversiModelWithSizeNegative() {
    IReversiModel badModel = new ReversiModel(-1);
    badModel.startGame();
  }

  //calling startgame after a game has already started
  @Test(expected = IllegalStateException.class)
  public void testStartGameTwice() {
    model.startGame();
    model.startGame();
  }

  //Test passTurn after the game has started
  @Test
  public void testPassTurnAfterGameStarted() {
    model.startGame();
    model.passTurn();
    Assert.assertEquals(Cell.WHITE, model.getCurrentTurn());
    model.passTurn();
    Assert.assertEquals(Cell.BLACK, model.getCurrentTurn());
  }

  //test passTurn before the game has started
  @Test(expected = IllegalStateException.class)
  public void testPassTurnBeforeGameStarted() {
    model.passTurn();
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

  }

  //testing for illegalMove
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveOutOfBoard() {
    model.startGame();
    model.executeMove(new Position(-5, 1, 4));
  }

  //testing for illegalMove
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveOutOfBoard2() {
    model.startGame();
    model.executeMove(new Position(1, -5, 4));
  }

  //testing for illegalMove, s + r + q does not equal 0
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveOutOfBoard3() {
    model.startGame();
    model.executeMove(new Position(2, 1, 10));
  }

  //testing for illegalMove, s + r + q does not equal 0
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveOutOfBoard4() {
    model.startGame();
    model.executeMove(new Position(2, 1, 3));
  }

  //testing for illegalMove, s + r + q does not equal 0
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveOutOfBoard5() {
    model.startGame();
    model.executeMove(new Position(2, 1, -10));
  }

  //making sure an illegalMove does not set it to a player
  @Test
  public void testIllegalMove() {
    model.startGame();
    model.executeMove(new Position(2, 0, -2));
    Assert.assertEquals(Optional.empty(), model.getHexAt(new Position(2, 0, -2)));
  }

  //testing getScore works correctly
  @Test
  public void testGetScoreWorksCorrectly() {
    IReversiModel model2 = new ReversiModel(2);
    model2.startGame();
    IView view = new ReversiView(model2);
    view.renderTextualView();
    model2.executeMove(new Position(-1, -1, 2)); //b
    view.renderTextualView();
    model2.executeMove(new Position(1, 1, -2)); //w
    Assert.assertFalse(model2.isGameOver());
    Assert.assertEquals(4, model2.getScore(Cell.BLACK));
    Assert.assertEquals(4, model2.getScore(Cell.WHITE));

  }

  //test to make sure the score starts at 4
  @Test
  public void testGetScoreInitial() {
    model.startGame();
    Assert.assertEquals(3, model.getScore(Cell.BLACK));
    Assert.assertEquals(3, model.getScore(Cell.WHITE));

  }

  //test for handling a tie

  //handling getHexAt incorrectly
  @Test(expected = IllegalArgumentException.class)
  public void testGetHexAtWrongPosition() {
    model.startGame();
    model.getHexAt(new Position(1, -1, -5));
  }

  //handling getHexAt incorrectly
  @Test(expected = IllegalArgumentException.class)
  public void testGetHexAtWrongPosition2() {
    model.startGame();
    model.getHexAt(new Position(1, 5, 3));
  }

  //handling getHexAt incorrectly
  @Test(expected = IllegalArgumentException.class)
  public void testGetHexAtWrongPosition3() {
    model.startGame();
    model.getHexAt(new Position(1, -6, 3));
  }

  //handling getHexAt incorrectly
  @Test(expected = IllegalArgumentException.class)
  public void testGetHexAtWrongPosition4() {
    model.startGame();
    model.getHexAt(new Position(30, -1, 3));
  }

  //handling getHexAt incorrectly
  @Test(expected = IllegalArgumentException.class)
  public void testGetHexAtWrongPosition5() {
    model.startGame();
    model.getHexAt(new Position(-5, 2, 3));
  }




  @Test
  //test when game is only played halfway and isGameOver should be false
  //since still legal moves left
  public void testPlayingGameMidwayWithNoPassTurns() {
    model.startGame();
    IView view = new ReversiView(model);
    view.renderTextualView();
    model.executeMove(new Position(1, 1, -2)); //b
    view.renderTextualView();
    model.executeMove(new Position(-1, -1, 2)); //w
    view.renderTextualView();
    model.executeMove(new Position(1, -2, 1)); //b
    view.renderTextualView();
    model.executeMove(new Position(2, -1, -1)); //w
    view.renderTextualView();
    model.executeMove(new Position(-2, 1, 1)); //b
    view.renderTextualView();
    model.executeMove(new Position(-1, 2, -1)); //w
    view.renderTextualView();
    Assert.assertFalse(model.isGameOver());
  }

  @Test
  //test with a single turn passed and Black wins
  public void testPlayingGameWithOnePassTurnUntilNoMovesLeft() {
    IReversiModel model2 = new ReversiModel(2);
    model2.startGame();
    IView view = new ReversiView(model2);
    Assert.assertEquals(3, model2.getScore(Cell.BLACK));
    Assert.assertEquals(3, model2.getScore(Cell.WHITE));
    view.renderTextualView();
    model2.executeMove(new Position(2, -1, -1)); //b
    view.renderTextualView();
    model2.executeMove(new Position(-2, 1, 1)); //w
    view.renderTextualView();
    model2.passTurn(); //b
    view.renderTextualView();
    model2.executeMove(new Position(1, -2, 1)); //w
    view.renderTextualView();
    model2.executeMove(new Position(-1, -1, 2)); //b
    view.renderTextualView();
    model2.executeMove(new Position(1, 1, -2)); //w
    view.renderTextualView();
    model2.executeMove(new Position(-1, 2, -1)); //b
    view.renderTextualView();
    Assert.assertTrue(model2.isGameOver());
    Assert.assertEquals(8, model2.getScore(Cell.BLACK));
    Assert.assertEquals(4, model2.getScore(Cell.WHITE));
    Assert.assertEquals(Cell.BLACK, model2.getWinner().get());
  }

  @Test(expected = IllegalStateException.class)
  //test with multiple passes and Black wins
  public void testPlayingGameWithMultPassTurnsUntilNoMovesLeftBlackWins() {
    IReversiModel model2 = new ReversiModel(2);
    IView view = new ReversiView(model2);
    model2.startGame();
    view.renderTextualView();
    model2.executeMove(new Position(-1, -1, 2)); //b
    view.renderTextualView();
    model2.passTurn(); //w
    view.renderTextualView();
    Assert.assertEquals(Cell.BLACK, model2.getCurrentTurn());
    model2.passTurn(); //b
    view.renderTextualView();
    Assert.assertEquals(Cell.WHITE, model2.getCurrentTurn());
    model2.passTurn(); //w
    view.renderTextualView();
    Assert.assertEquals(Cell.BLACK, model2.getCurrentTurn());
    model2.executeMove(new Position(2, -1, -1)); //b
    view.renderTextualView();
    model2.passTurn(); //w
    view.renderTextualView();
    model2.executeMove(new Position(-1, 2, -1)); //b
    view.renderTextualView();
    Assert.assertTrue(model2.isGameOver());
    Assert.assertEquals(Cell.BLACK, model2.getWinner().get());
  }

  @Test(expected = IllegalStateException.class)
  //test with multiple passes and Black wins
  public void testPlayingGameWithMultPassTurnsUntilNoMovesLeftBlackWins2() {
    IReversiModel model2 = new ReversiModel(2);
    IView view = new ReversiView(model2);
    model2.startGame();
    view.renderTextualView();
    model2.executeMove(new Position(-1, -1, 2)); //b
    view.renderTextualView();
    model2.passTurn(); //w
    view.renderTextualView();
    model2.passTurn(); //b --> if both pass, game ends!!!!!!!!!!!
    view.renderTextualView();
    model2.executeMove(new Position(-2, 1, 1));
    view.renderTextualView();
    model2.passTurn(); //b
    view.renderTextualView();
    model2.executeMove(new Position(1, -2, 1)); //w
    view.renderTextualView();
    model2.passTurn(); //b
    view.renderTextualView();
    model2.executeMove(new Position(1, 1, -2)); //w
    view.renderTextualView();
    model2.executeMove(new Position(2, -1, -1)); //b
    view.renderTextualView();
    model2.passTurn(); //w
    view.renderTextualView();
    Assert.assertFalse(model2.isGameOver());
    model2.executeMove(new Position(-1, 2, -1)); //b
    view.renderTextualView();
    Assert.assertTrue(model2.isGameOver());
    Assert.assertEquals(9, model2.getScore(Cell.BLACK));
    Assert.assertEquals(3, model2.getScore(Cell.WHITE));
    Assert.assertEquals(Cell.BLACK, model2.getWinner().get());
  }


  @Test
  //test where same score ends up in a tie
  public void testPlayingGameUntilStalemate() {
    IReversiModel model2 = new ReversiModel(2);
    model2.startGame();
    IView view = new ReversiView(model2);
    Assert.assertEquals(3, model2.getScore(Cell.BLACK));
    Assert.assertEquals(3, model2.getScore(Cell.WHITE));
    view.renderTextualView();
    model2.executeMove(new Position(2, -1, -1)); //b
    view.renderTextualView();
    model2.executeMove(new Position(-2, 1, 1)); //w
    view.renderTextualView();
    model2.passTurn(); //b
    view.renderTextualView();
    model2.executeMove(new Position(1, -2, 1)); //w
    view.renderTextualView();
    model2.executeMove(new Position(-1, -1, 2)); //b
    view.renderTextualView();
    Assert.assertEquals(5, model2.getScore(Cell.BLACK));
    Assert.assertEquals(5, model2.getScore(Cell.WHITE));
    model2.passTurn(); //w
    view.renderTextualView();
    model2.passTurn(); //b
    view.renderTextualView();
    Assert.assertTrue(model2.isGameOver());
    Assert.assertEquals(Optional.empty(), model2.getWinner());
  }

  @Test
  //test game is over when both players pass their turn consecutively
  public void testPlayingGameWithTwoPassTurnsSoGameOver() {
    IReversiModel model2 = new ReversiModel(2);
    IView view = new ReversiView(model2);
    model2.startGame();
    view.renderTextualView();
    model2.executeMove(new Position(-1, -1, 2)); //b
    view.renderTextualView();
    model2.passTurn(); //w
    view.renderTextualView();
    model2.passTurn(); //b --> if both pass, game ends!!!!!!!!!!!
    view.renderTextualView();
    Assert.assertTrue(model2.hasLegalMove(Cell.BLACK));
    Assert.assertTrue(model2.isGameOver());
    Assert.assertEquals(5, model2.getScore(Cell.BLACK));
    Assert.assertEquals(2, model2.getScore(Cell.WHITE));
    Assert.assertEquals(Cell.BLACK, model2.getWinner().get());
  }

  @Test
  //test when Black only passes its turn and White only plays and wins
  //and game is not over after each pass because only Black passes (White never passes)
  public void testPlayingGameWithOnlyBlackPassesAndWhiteWins() {
    IReversiModel model2 = new ReversiModel(2);
    IView view = new ReversiView(model2);
    model2.startGame();
    view.renderTextualView();
    Assert.assertEquals(Cell.BLACK, model2.getCurrentTurn());
    model2.passTurn(); //b
    view.renderTextualView();
    Assert.assertEquals(Cell.WHITE, model2.getCurrentTurn());
    model2.executeMove(new Position(-2, 1, 1)); //w
    view.renderTextualView();
    Assert.assertEquals(Cell.BLACK, model2.getCurrentTurn());
    model2.passTurn(); //b
    view.renderTextualView();
    Assert.assertEquals(Cell.WHITE, model2.getCurrentTurn());
    model2.executeMove(new Position(1, -2, 1)); //w
    view.renderTextualView();
    Assert.assertEquals(Cell.BLACK, model2.getCurrentTurn());
    model2.passTurn(); //b
    view.renderTextualView();
    Assert.assertEquals(Cell.WHITE, model2.getCurrentTurn());
    model2.executeMove(new Position(1, 1, -2)); //w
    view.renderTextualView();
    Assert.assertTrue(model2.isGameOver());
    Assert.assertEquals(0, model2.getScore(Cell.BLACK));
    Assert.assertEquals(9, model2.getScore(Cell.WHITE));
    Assert.assertEquals(Cell.WHITE, model2.getWinner().get());
  }
}
