package controller;

import java.util.Optional;
import javax.swing.JOptionPane;

import model.Cell;
import model.IReversiModel;
import view.IReversiFrameView;

/**
 * The {@code BasicController} class serves as an
 * abstract base for specific Reversi game controllers.
 * It implements the {@code Features} and
 * {@code ModelStatusListener} interfaces,
 * providing foundational control and response
 * capabilities for interacting with the game model and view.
 */
public abstract class BasicController implements Features, ModelStatusListener {
  protected final IReversiModel model; //the game model
  protected final IPlayer player; //the player controlled by this controller
  protected final IReversiFrameView view; //the game view
  protected boolean isHexPosition;

  /**
   * Constructs a new {@code BasicController} with the given model, player, and view.
   * This constructor initializes the controller, sets up model-view communication,
   * and registers the controller as a listener for model status updates.
   *
   * @param model The game model to be managed by this controller.
   * @param player The player (human or AI) associated with this controller.
   * @param view The game view to be updated by this controller.
   */
  public BasicController(IReversiModel model, IPlayer player, IReversiFrameView view) { //, boolean isHexPosition) {
    this.model = model;
    this.player = player;
    this.view = view;
    this.isHexPosition = true;
    view.addFeatures(this);
    model.addModelStatusListener(this);
  }

  /**
   * Updates the game view to reflect the current state
   * of the game model.
   */
  @Override
  public void updateView() {
    view.updateView(model);
  }

  /**
   * Passes the turn to the next player in the game.
   * This method also checks if the game is over after
   * the turn is passed.
   */
  @Override
  public void passTurn() {
    model.passTurn();
    checkGameOver();
  }

  /**
   * Responds to changes in the current player's turn.
   * Updates the view and shows a turn indicator
   * if it is the controlled player's turn.
   *
   * @param currentPlayer The player whose turn it is now.
   */
  @Override
  public void updateTurnChanged(Cell currentPlayer) {
    if (this.player.getCell() == currentPlayer) {
      updateView();
      view.showTurnIndicator("It's your turn!");
    }
  }

  /**
   * Updates the score display in the game view.
   *
   * @param scoreBlack The current score of the black player.
   * @param scoreWhite The current score of the white player.
   */
  @Override
  public void updateScore(int scoreBlack, int scoreWhite) {
    view.showScore("SCORE - Black: " + scoreBlack + " White: " + scoreWhite);
  }

  /**
   * Checks if the game is over and displays a message
   * indicating the game result.
   * The message includes information about the winning
   * player or states if the game ended in a tie.
   */
  protected void checkGameOver() {
    if (model.isGameOver()) {
      Optional<Cell> winner = model.getWinner();
      String message = "GAME OVER! "
              + (winner.isPresent() ? winner.get() + " wins!" : "It's a tie!");
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Triggers an update of the game board in the view.
   * This method is called in response to changes
   * in the game state.
   */
  @Override
  public void updateGameBoard() {
    view.repaintView();
  }
}
