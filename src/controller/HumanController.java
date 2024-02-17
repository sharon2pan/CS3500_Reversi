package controller;

import model.IPosition;
import model.IReversiModel;
import view.IReversiFrameView;

import javax.swing.JOptionPane;

/**
 * The {@code HumanController} class extends the
 * {@code BasicController} to specifically manage a human player in a Reversi game.
 * It handles the execution of moves initiated by human players through the game's user interface.
 */
public class HumanController extends BasicController {

  /**
   * Constructs a new {@code HumanController} with the specified game model, human player, and view.
   * Initializes the controller for a human player in the Reversi game.
   *
   * @param model The game model to be managed by this controller.
   * @param player The human player associated with this controller.
   * @param view The game view to be updated by this controller.
   */
  public HumanController(IReversiModel model, IPlayer player, IReversiFrameView view) {
    super(model, player, view);
  }

  /**
   * Executes a move for the human player.
   * This method is called when a human player selects a position on the game board.
   * It attempts to execute the move in the game model, updates the game view, and checks for game
   * over conditions.
   *
   * @param pos The position on the board where the human player attempts to make a move.
   * @throws IllegalArgumentException If the move is invalid as per the game rules.
   */
  @Override
  public void executeMove(IPosition pos) {
    if (model.getCurrentTurn() == player.getCell() && !player.isAI()) {
      try {
        model.executeMove(pos);
        updateView();
        checkGameOver();
      } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(null, "Invalid Move");
      }
    }
  }
}
