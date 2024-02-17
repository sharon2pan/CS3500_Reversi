package controller;

import java.util.Optional;

import model.IPosition;
import model.IReversiModel;
import view.IReversiFrameView;


/**
 * The {@code AiController} class extends the
 * {@code BasicController} to specifically manage an AI player in a Reversi game.
 * It overrides necessary methods to ensure that AI-specific logic is handled correctly,
 * particularly the execution of moves based on AI strategies.
 */
public class AiController extends BasicController {

  /**
   * Constructs a new {@code AiController} with the given model, AI player, and view.
   * Initializes the controller for an AI player in the Reversi game.
   *
   * @param model The game model to be managed by this controller.
   * @param player The AI player associated with this controller.
   * @param view The game view to be updated by this controller.
   */
  public AiController(IReversiModel model, IPlayer player, IReversiFrameView view) {
    super(model, player, view);
  }

  /**
   * Executes a move for the AI player.
   * This method is called when it is the AI player's turn to make a move.
   * It determines the AI's next move using its strategy and updates the game model accordingly.
   *
   * @param pos The position for the move, typically determined by the AI strategy.
   */
  @Override
  public void executeMove(IPosition pos) {
    if (model.getCurrentTurn() == player.getCell() && player.isAI()) {
      executeAiMove();
      checkGameOver();
    }
  }

  /**
   * Determines and executes the AI player's move.
   * This method calculates the best move for the AI player using its strategy,
   * executes the move in the game model, and updates the game view.
   */
  private void executeAiMove() {
    Optional<IPosition> move = player.play(model);
    if (move.isPresent()) {
      model.executeMove(move.get());
    } else {
      model.passTurn();
    }
    updateView();
  }



}
