package controller;

import model.IPosition;

/**
 * The Features interface represents the set of actions that
 * can be performed in the Reversi game.
 * It encapsulates the core functionalities required for
 * interacting with the game model and view.
 */
public interface Features {

  /**
   * Executes a move at a specified position on the Reversi board.
   * This method is typically called when a player selects a
   * position on the board to place their piece.
   *
   * @param pos The position on the board where the move is
   *            to be executed.
   */
  void executeMove(IPosition pos);

  /**
   * Passes the turn to the next player.
   * This method is used when a player decides to skip their
   * turn without making a move.
   */
  void passTurn();

  /**
   * Updates the view of the Reversi game.
   * This method is responsible for refreshing the game's
   * graphical user interface.
   */
  void updateView();
}
