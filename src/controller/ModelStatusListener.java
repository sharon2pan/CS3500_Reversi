package controller;

import model.Cell;

/**
 * The ModelStatusListener interface is used to listen to
 * changes in the game state of a Reversi game.
 * Implementations of this interface can react to events
 * like turn changes and game state updates.
 */
public interface ModelStatusListener {

  /**
   * Called when the turn changes in the game.
   * Implementers can use this method to update their state
   * or UI based on the new current player.
   *
   * @param currentPlayer The player whose turn it is now.
   */
  void updateTurnChanged(Cell currentPlayer);

  /**
   * Called when the game board state is updated.
   * This can occur after a move or pass turn action.
   * Implementers can use this to refresh or update
   * the game board in the view.
   */
  void updateGameBoard();

  /**
   * Called when the score is updated in the game.
   * This method informs about the current score of both
   * Black and White players.
   *
   * @param scoreBlack The current score of the Black player.
   * @param scoreWhite The current score of the White player.
   */
  void updateScore(int scoreBlack, int scoreWhite);

}
