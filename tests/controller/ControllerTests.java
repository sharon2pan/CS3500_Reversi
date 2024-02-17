package controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import model.Cell;
import model.IReversiModel;
import model.Position;
import model.ReversiModel;
import strategy.AvoidNeighboringCornersStrategy;
import strategy.MaximumCaptureStrategy;
import strategy.MinimizeMaxMoveStrategy;
import view.IReversiFrameView;
import view.ReversiFrameView;
import view.ReversiPanelView;

/**
 * Tests for the Controller.
 */
public class ControllerTests {
  private IReversiModel model;
  private HumanController controllerHuman;
  private AiController controllerAI;
  private IPlayer playerHuman;
  //private IPlayer playerAi;
  private IReversiFrameView view;

  @Before
  public void setUp() {
    model = new ReversiModel(6);
    view = new ReversiFrameView(model);
    playerHuman = new HumanPlayer(Cell.BLACK);
    controllerHuman = new HumanController(model, playerHuman, view);

    IPlayer playerAi = new AiPlayer(Cell.WHITE, new MaximumCaptureStrategy());
    controllerAI = new AiController(model, playerAi, view);
    model.startGame();
  }

  //testing that execute move in the controller works correctly
  @Test
  public void testValidMoveExecution() {
    // Assuming position (0, -1, 1) is a valid move for BLACK at the start
    Position validMove = new Position(2, -1, -1);
    controllerHuman.executeMove(validMove);
    Assert.assertTrue("The cell should have a black piece after the move.",
            model.getHexAt(validMove).isPresent()
            && model.getHexAt(validMove).get() == Cell.BLACK);
    Assert.assertEquals("The turn should have passed to WHITE.", Cell.WHITE,
            model.getCurrentTurn());
  }

  //testing the game state when an invalid move is made
  @Test
  public void testTwoHumans() {
    IReversiModel model = new ReversiModel(3);
    IReversiFrameView view1 = new ReversiFrameView(model);
    IPlayer player1 = new HumanPlayer(Cell.WHITE);
    HumanController controller1 = new HumanController(model, player1, view1);
    IPlayer player2 = new HumanPlayer(Cell.BLACK);
    HumanController controller2 = new HumanController(model, player2, view1);

    Assert.assertEquals(3, model.getSize());
    Assert.assertEquals(7, model.getBoard().length);

    model.startGame();
    controller1.executeMove(new Position(2,-1,-1));
    controller2.executeMove(new Position(1,-2,1));
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new Position(1,-2,1)).get());
  }

  //testing that the updateView in the controller works correctly
  @Test
  public void testValidUpdateView() {
    MockReversiFrameView mockView = new MockReversiFrameView();
    HumanController controller = new HumanController(model, playerHuman, mockView);
    controller.updateView();
    Assert.assertTrue(mockView.getLog().contains("View's current turn is updated"));

  }

  //testing if passturn works correctly
  @Test
  public void testPassTurn() {
    // Current player is BLACK
    Assert.assertEquals("The current turn should initially be BLACK.", Cell.BLACK,
            model.getCurrentTurn());
    controllerHuman.passTurn();
    Assert.assertEquals("The turn should have passed to WHITE.", Cell.WHITE,
            model.getCurrentTurn());
  }

  //testing that the game over works
  @Test
  public void testGameOverControllerPassesTwice() {
    controllerHuman.passTurn();
    controllerHuman.passTurn();
    Assert.assertTrue(model.isGameOver());
  }

  //starting turn is always black, so trying to move white while its turn for black
  @Test
  public void testWrongTurnMoving() {
    IReversiModel model = new ReversiModel(6);
    IReversiFrameView view = new ReversiFrameView(model);
    IPlayer player = new HumanPlayer(Cell.WHITE);
    HumanController controller = new HumanController(model, player, view);
    model.startGame();

    Position attemptedMovePosition = new Position(-1, -1, 2);
    Optional<Cell> cellBeforeMove = model.getHexAt(attemptedMovePosition);

    controller.executeMove(attemptedMovePosition);

    Optional<Cell> cellAfterMove = model.getHexAt(attemptedMovePosition);

    Assert.assertEquals(cellBeforeMove, cellAfterMove); //Cell state should not change
    // when wrong player tries to move

    Assert.assertEquals(Cell.BLACK, model.getCurrentTurn()); //Turn should not change when
    // wrong player tries to move


  }

  //testing the game state when an invalid move is made
  @Test
  public void testExecuteInvalidMove() {
    Cell initialTurn = model.getCurrentTurn();
    Optional<Cell>[][] initialBoard = model.getBoard();

    Position invalidPos = new Position(5, 5, 2); // Assuming this is an invalid position
    controllerHuman.executeMove(invalidPos);

    Assert.assertEquals("Current turn should not change after an invalid move",
            initialTurn, model.getCurrentTurn());
    Assert.assertArrayEquals("Board configuration should not change after an invalid move",
            initialBoard, model.getBoard());
  }

  //testing passing a turn when it is not your turn to pass
  @Test
  public void testPassingTurnWhenNotYourTurn() {
    Cell initialTurn = model.getCurrentTurn();
    Position validMove = new Position(2, -1, -1);
    controllerHuman.executeMove(validMove);
    controllerHuman.passTurn();

    // Verify that the state remains unchanged
    Assert.assertEquals("Current turn should not change when passing turn not allowed",
            initialTurn, model.getCurrentTurn());
  }



  //testing the turn indicator gets called when it is supposed to
  @Test
  public void testTurnIndicatorUpdates() {
    IReversiModel model = new ReversiModel(6);
    MockReversiFrameView mockView = new MockReversiFrameView();

    IPlayer playerBlack = new HumanPlayer(Cell.BLACK);
    HumanController controller = new HumanController(model, playerBlack, mockView);

    model.startGame();

    // Simulate a move by the black player
    controller.executeMove(new Position(2, -1, -1));
    Assert.assertTrue(mockView.getLog().contains("showTurnIndicator: It's your turn!\n"));

  }

  //testing the score indicator gets called when it is supposed to
  @Test
  public void testScoreIndicatorUpdates() {
    IReversiModel model = new ReversiModel(6);
    MockReversiFrameView mockView = new MockReversiFrameView();

    IPlayer playerBlack = new HumanPlayer(Cell.BLACK);
    HumanController controller = new HumanController(model, playerBlack, mockView);

    model.startGame();

    // Simulate a move by the black player
    controller.executeMove(new Position(2, -1, -1));
    Assert.assertTrue(mockView.getLog().contains("showScore: "));
    Assert.assertEquals("showScore: SCORE - Black: 3 White: 3\n"
            + "View's current turn is updatedshowTurnIndicator: It's your turn!\n"
            + "showTheView: \n"
            + "showScore: SCORE - Black: 5 White: 2\n"
            + "View's current turn is updated", mockView.getLog());

  }

  //testing that executeMove in the controller works correctly
  @Test
  public void testValidMoveExecution2() {
    // Assuming position (0, -1, 1) is a valid move for BLACK at the start
    Position validMove = new Position(2, -1, -1);
    Assert.assertEquals("Starting score for BLACK", 3,
            model.getScore(Cell.BLACK));
    Assert.assertEquals("Starting score for WHITE", 3,
            model.getScore(Cell.WHITE));

    controllerHuman.executeMove(validMove);

    Assert.assertTrue("The cell should have a black piece after the move.",
            model.getHexAt(validMove).isPresent()
                    && model.getHexAt(validMove).get() == Cell.BLACK);
    Assert.assertEquals("The turn should have passed to WHITE.", Cell.WHITE,
            model.getCurrentTurn());
    Assert.assertEquals("Score should increase for BLACK", 5,
            model.getScore(Cell.BLACK));
    Assert.assertEquals("Score should increase for WHITE",2,
            model.getScore(Cell.WHITE));
  }



  //starting turn is always black, so trying to move white while it's black's turn
  @Test
  public void testWrongTurnMoving2() {
    IReversiModel model = new ReversiModel(6);
    IReversiFrameView view = new ReversiFrameView(model);
    IPlayer player = new HumanPlayer(Cell.WHITE);
    HumanController controller = new HumanController(model, player, view);
    model.startGame();

    Position attemptedMovePosition = new Position(-1, -1, 2);
    Optional<Cell> cellBeforeMove = model.getHexAt(attemptedMovePosition);

    controller.executeMove(attemptedMovePosition);

    Optional<Cell> cellAfterMove = model.getHexAt(attemptedMovePosition);

    Assert.assertEquals(cellBeforeMove, cellAfterMove); //Cell state should not change
    // when wrong player tries to move

    Assert.assertEquals(Cell.BLACK, model.getCurrentTurn()); //Turn should not change when
    // wrong player tries to move

    Assert.assertEquals(3, model.getScore(Cell.BLACK)); //Initial score should not change
    Assert.assertEquals(3, model.getScore(Cell.WHITE)); //Initial score should not change
  }


  //testing if game is over between two Human players when playing until no moves left
  //and Black wins
  @Test
  public void testGameOverAndBlackWins() {
    IReversiModel model = new ReversiModel(2);
    IPlayer playerBlack = new HumanPlayer(Cell.BLACK);
    IPlayer playerWhite = new HumanPlayer(Cell.WHITE);
    HumanController controller = new HumanController(model, playerBlack, view);
    HumanController controller2 = new HumanController(model, playerWhite, view);

    model.startGame();

    controller.executeMove(new Position(2, -1, -1)); //b
    Assert.assertEquals(Cell.WHITE, model.getCurrentTurn());
    controller2.executeMove(new Position(-2, 1, 1)); //w
    Assert.assertEquals(Cell.BLACK, model.getCurrentTurn());
    controller.passTurn(); //b
    Assert.assertEquals(Cell.WHITE, model.getCurrentTurn());
    controller2.executeMove(new Position(1, -2, 1)); //w
    controller.executeMove(new Position(-1, -1, 2)); //b
    controller2.executeMove(new Position(1, 1, -2)); //w
    controller.executeMove(new Position(-1, 2, -1)); //b

    Assert.assertTrue(model.isGameOver());
    Assert.assertEquals(Optional.of(Cell.BLACK), model.getWinner());
  }

  //testing that the strategy chooses the right move in the controller
  @Test
  public void testAIMaxCaptureExecutesMoveCorrectly() {
    IReversiModel model = new ReversiModel(6);
    IReversiFrameView view = new ReversiFrameView(model);
    IPlayer player = new HumanPlayer(Cell.BLACK);
    IPlayer playerAI = new AiPlayer(Cell.WHITE, new MaximumCaptureStrategy());
    HumanController controller = new HumanController(model, player, view);
    AiController controller2 = new AiController(model, playerAI, view);
    model.startGame();
    controller.executeMove(new Position(2, -1, -1));
    Assert.assertTrue("AI move should be legal",
            model.isALegalMove(new Position(3, -2, -1), Cell.WHITE));
    controller2.executeMove(new Position(0, 0, 0));
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new Position(3, -2, -1)).get());
  }

  //testing that the score updates correctly.
  @Test
  public void testScoringAfterMove() {
    IReversiModel model = new ReversiModel(6);
    IReversiFrameView view = new ReversiFrameView(model);
    IPlayer player = new HumanPlayer(Cell.BLACK);
    IPlayer playerAI = new AiPlayer(Cell.WHITE, new MaximumCaptureStrategy());
    HumanController controller = new HumanController(model, player, view);
    AiController controller2 = new AiController(model, playerAI, view);
    model.startGame();
    controller.executeMove(new Position(2, -1, -1));
    Assert.assertEquals("Black score should be updated correctly",
            5, model.getScore(Cell.BLACK));
    Assert.assertEquals("White score should be updated correctly",
            2, model.getScore(Cell.WHITE));

    controller2.executeMove(new Position(0, 0, 0));
    Assert.assertEquals("Black score should be updated correctly",
            3, model.getScore(Cell.BLACK));
    Assert.assertEquals("White score should be updated correctly",
            5, model.getScore(Cell.WHITE));
  }

  //testing that the current turn updates correctly.
  @Test
  public void testCurrentTurnAfterMove() {
    IReversiModel model = new ReversiModel(6);
    IReversiFrameView view = new ReversiFrameView(model);
    IPlayer player = new HumanPlayer(Cell.BLACK);
    IPlayer playerAI = new AiPlayer(Cell.WHITE, new MaximumCaptureStrategy());
    HumanController controller = new HumanController(model, player, view);
    AiController controller2 = new AiController(model, playerAI, view);

    model.startGame();

    controller.executeMove(new Position(2, -1, -1));
    Assert.assertEquals(Cell.WHITE, model.getCurrentTurn());

    controller2.executeMove(new Position(0, 0, 0));
    Assert.assertEquals(Cell.BLACK, model.getCurrentTurn());
  }

  //testing that discs are present where both the human and AI players have made moves.
  @Test
  public void testDiscPlacementfterMove() {
    IReversiModel model = new ReversiModel(6);
    IReversiFrameView view = new ReversiFrameView(model);
    IPlayer player = new HumanPlayer(Cell.BLACK);
    IPlayer playerAI = new AiPlayer(Cell.WHITE, new MaximumCaptureStrategy());
    HumanController controller = new HumanController(model, player, view);
    AiController controller2 = new AiController(model, playerAI, view);

    model.startGame();

    Position validPosition = new Position(2, -1, -1);
    controller.executeMove(validPosition);
    Assert.assertEquals(Cell.BLACK, model.getHexAt(validPosition).get());

    controller2.executeMove(new Position(0, 0, 0));
    Position maxCapturePosition = new Position(3, -2, -1);
    Assert.assertEquals(Cell.WHITE, model.getHexAt(maxCapturePosition).get());
  }

  //testing when game is played fully to the end with an AI player
  @Test
  public void testGameOverWithAI() {
    IReversiModel model = new ReversiModel(2);
    IReversiFrameView view = new ReversiFrameView(model);
    IPlayer player = new HumanPlayer(Cell.BLACK);
    IPlayer playerAI = new AiPlayer(Cell.WHITE, new MaximumCaptureStrategy());
    HumanController controller = new HumanController(model, player, view);
    AiController controller2 = new AiController(model, playerAI, view);
    model.startGame();

    controller.executeMove(new Position(2, -1, -1));
    Assert.assertTrue("AI move should be legal",
            model.isALegalMove(new Position(1, -2, 1), Cell.WHITE));

    controller2.executeMove(new Position(0, 0, 0));
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new Position(1, -2, 1)).get());

    controller.executeMove(new Position(-1, 2, -1));
    controller2.executeMove(new Position(0, 0, 0));
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new Position(1, 1, -2)).get());

    controller.executeMove(new Position(-1, -1, 2));
    controller2.executeMove(new Position(0, 0, 0));
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new Position(-2, 1, 1)).get());

    controller.passTurn();
    controller2.executeMove(new Position(0, 0, 0));

    Assert.assertTrue(model.isGameOver());
  }

  //testing the turn indicator gets called when it is supposed to for an invalid move
  @Test
  public void testTurnIndicatorUpdatesForInvalidMove() {
    IReversiModel model = new ReversiModel(6);
    MockReversiFrameView mockView = new MockReversiFrameView();

    IPlayer playerBlack = new HumanPlayer(Cell.BLACK);
    HumanController controller = new HumanController(model, playerBlack, mockView);

    model.startGame();

    // Simulate a move by the black player
    controller.executeMove(new Position(2, -1, -1));
    Assert.assertTrue(mockView.getLog().contains("showTurnIndicator: It's your turn!\n"));

  }

  //using the mock model to test if input to executeMove is correct and goes through the correct
  //steps of adding the controller as a ModelStatusListener, starting the game, and checking
  //the current turn for a human
  @Test
  public void testInputsExecuteMoveHuman() {
    StringBuilder log = new StringBuilder();
    MockReversiModel mockModel = new MockReversiModel(log);

    HumanController controller = new HumanController(mockModel, playerHuman, view);

    mockModel.startGame();

    controller.executeMove(new Position(2,-1,-1));

    Assert.assertEquals("addModelStatusListener\nstartGame\n"
                    + "getCurrentTurn\nexecuteMove Position[q=2, r=-1, s=-1]\n"
                    + "getCurrentTurn\nisGameOver\n", mockModel.getLog());
  }

  //using the mock model to test if the correct steps of adding the controller as a
  //ModelStatusListener and starting the game are performed before passing the turn for a human
  @Test
  public void testInputsPassTurnHuman() {
    StringBuilder log = new StringBuilder();
    MockReversiModel mockModel = new MockReversiModel(log);

    HumanController controller = new HumanController(mockModel, playerHuman, view);

    mockModel.startGame();

    controller.passTurn();

    Assert.assertEquals("addModelStatusListener\nstartGame\npassTurn\nisGameOver\n",
            mockModel.getLog());

  }

  //using the mock model to test if all correct steps are taken as two Human player passes to end
  //the game
  //GET RID???
  @Test
  public void testInputsEndGameByPassingTwice() {
    StringBuilder log = new StringBuilder();
    MockReversiModel mockModel = new MockReversiModel(log);

    IPlayer playerWhite = new HumanPlayer(Cell.WHITE);

    HumanController controller = new HumanController(mockModel, playerHuman, view);
    HumanController controller2 = new HumanController(mockModel, playerWhite, view);

    mockModel.startGame();

    controller.passTurn();
    controller2.passTurn();

    Assert.assertEquals("addModelStatusListener\naddModelStatusListener\nstartGame\n"
                    + "passTurn\nisGameOver\npassTurn\nisGameOver\n", mockModel.getLog());
  }

  //using the mock model to test if all correct steps are taken as one human player and one AI
  // player passes to end the game
  //GET RID???
  @Test
  public void testInputsEndGameByPassingTwice2() {
    StringBuilder log = new StringBuilder();
    MockReversiModel mockModel = new MockReversiModel(log);

    IPlayer playerWhite = new AiPlayer(Cell.WHITE, new MinimizeMaxMoveStrategy());

    HumanController controller = new HumanController(mockModel, playerHuman, view);
    AiController controller2 = new AiController(mockModel, playerWhite, view);

    mockModel.startGame();

    controller.passTurn();
    controller2.passTurn();

    Assert.assertEquals("addModelStatusListener\naddModelStatusListener\nstartGame\n"
                    + "passTurn\nisGameOver\npassTurn\nisGameOver\n", mockModel.getLog());
  }

  //testing maxcapture strategy
  @Test
  public void testAIControllerDifferentStrategies() {
    model.executeMove(new Position(2, -1, -1));
    controllerAI.executeMove(new Position(0, 0, 0));
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new Position(3, -2, -1)).get());
  }

  //testing avoidCorners strategy
  @Test
  public void testAIControllerDifferentStrategies2() {
    IPlayer playerAi2 = new AiPlayer(Cell.WHITE, new AvoidNeighboringCornersStrategy());
    AiController controllerAi = new AiController(model, playerAi2, view);
    model.executeMove(new Position(2, -1, -1));
    controllerAi.executeMove(new Position(0, 0, 0));
    Assert.assertEquals(Cell.WHITE, model.getHexAt(new Position(1, -2, 1)).get());
  }

  //////////Model Status Listener Tests////////////////

  // Check if the mock listener received the notifications
  @Test
  public void testAddModelStatusListener() {
    ReversiModel model = new ReversiModel(6);
    MockModelStatusListener mockListener = new MockModelStatusListener();
    model.addModelStatusListener(mockListener);

    model.startGame();
    model.executeMove(new Position(2, -1, -1));

    // Check if the mock listener received the notifications
    String log = mockListener.getLog();
    Assert.assertTrue(log.contains("TurnChanged"));
    Assert.assertTrue(log.contains("GameBoardUpdated"));
    Assert.assertTrue(log.contains("ScoreUpdated"));
  }

  //TestNotifyTurnChange
  @Test
  public void testNotifyTurnChanged() {
    ReversiModel model = new ReversiModel(6);
    MockModelStatusListener mockListener = new MockModelStatusListener();
    model.addModelStatusListener(mockListener);

    model.startGame();

    String log = mockListener.getLog();
    Assert.assertTrue(log.contains("TurnChanged: Black"));
  }

  //TestNotifyTurnChange
  @Test
  public void testNotifyTurnChanged2() {
    ReversiModel model = new ReversiModel(6);
    MockModelStatusListener mockListener = new MockModelStatusListener();
    model.addModelStatusListener(mockListener);

    model.startGame();
    model.passTurn();

    String log = mockListener.getLog();
    Assert.assertTrue(log.contains("TurnChanged: White"));
  }

  //TestNotifyTurnChange
  @Test
  public void testNotifyTurnChanged3() {
    ReversiModel model = new ReversiModel(6);
    MockModelStatusListener mockListener = new MockModelStatusListener();
    model.addModelStatusListener(mockListener);

    model.startGame();
    model.executeMove(new Position(2, -1, -1));

    String log = mockListener.getLog();
    Assert.assertTrue(log.contains("TurnChanged: White"));
  }



  //TestGameBoardUpdated
  @Test
  public void testNotifyGameStateChanged() {
    ReversiModel model = new ReversiModel(6);
    MockModelStatusListener mockListener = new MockModelStatusListener();
    model.addModelStatusListener(mockListener);

    model.startGame();
    model.executeMove(new Position(2, -1, -1)); // Trigger game state change

    String log = mockListener.getLog();
    Assert.assertTrue(log.contains("GameBoardUpdated"));
  }

  //TestScoreUpdated
  @Test
  public void testNotifyScoreChanged() {
    ReversiModel model = new ReversiModel(6);
    MockModelStatusListener mockListener = new MockModelStatusListener();
    model.addModelStatusListener(mockListener);

    model.startGame();
    model.executeMove(new Position(2, -1, -1)); // Trigger score change

    String log = mockListener.getLog();
    Assert.assertTrue(log.contains("ScoreUpdated"));
  }

  //////////Features Tests////////////////

  //testing executeMove
  @Test
  public void testExecuteMoveMockFeatures() {
    ReversiModel model = new ReversiModel(6);
    ReversiFrameView view = new ReversiFrameView(model);
    ReversiPanelView panel = new ReversiPanelView(model);

    String input = "m";

    MockFeatures mockFeatures = new MockFeatures(input);
    view.addFeatures(mockFeatures);

    model.startGame();


    Assert.assertEquals(mockFeatures.getLog(), ("passTurn"));
  }
}




