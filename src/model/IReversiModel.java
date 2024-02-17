package model;

import java.util.List;
import java.util.Optional;

import controller.ModelStatusListener;

/**
 * Interface for the Reversi game model, defining the main
 * operations needed to play and manage the game.
 */
public interface IReversiModel extends ReadOnlyReversiModel {

  /**
   * Executes a move at the specified position on the game board.
   *
   * @param pos The position where the move should be executed.
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the game is already over
   */
  void executeMove(IPosition pos);

  /**
   * Checks if the game is over based on the current state of the board.
   *
   * @return True if the game is over, false otherwise.
   * @throws IllegalStateException if the game has not started
   */
  boolean isGameOver();

  /**
   * Retrieves the score for the specified hex (cell).
   *
   * @param hex The cell for which the score is to be calculated.
   * @return The score associated with the specified cell.
   * @throws IllegalStateException if the game has not started
   */
  int getScore(Cell hex);

  /**
   * Determines the winner of the game based on the current board state.
   *
   * @return The winning cell. This can be BLACK, WHITE, or EMPTY in case of a tie.
   * @throws IllegalStateException if the game has not started
   */
  Optional<Cell> getWinner();

  /**
   * Retrieves the hex (cell) at the specified position on the game board.
   *
   * @param p The position of the desired cell.
   * @return The cell located at the given position.
   * @throws IllegalStateException if the game has not started
   */
  Optional<Cell> getHexAt(IPosition p);

  /**
   * Initializes and starts a new game.
   * @throws IllegalStateException if the game is already started
   */
  void startGame();

  /**
   * Allows a player to pass their turn if they have no valid moves.
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the pass turn count is greater than 2
   */
  void passTurn();

  /**
   * Retrieves the entire game board represented as a 2D array of cells.
   *
   * @return A 2D array of cells representing the current state of the game board.
   */
  Optional<Cell>[][] getBoard();

  /**
   * Retrieves the cell representing the current turn in the game.
   *
   * @return The cell indicating which player's turn it is.
   *         This could be Cell.WHITE, Cell.BLACK, or another predefined cell value.
   * @throws IllegalStateException if the game has not started
   */
  Cell getCurrentTurn();

  /**
   * Checks if a move to a specific position is legal for the given cell.
   *
   * @param pos  The position on the board where the move is being considered.
   * @param hex  The cell (player) attempting the move.
   * @return True if the move is legal according to the rules of Reversi, false otherwise.
   * @throws IllegalStateException if the game has not started
   */
  boolean isALegalMove(IPosition pos, Cell hex);

  /**
   * Retrieves the size of the game board.
   *
   * @return The size of one side of the hexagonal game board.
   */
  int getSize();

  /**
   * Counts the number of opponent's pieces that would be captured
   * if a move is made at the given position.
   *
   * @param pos  The position to check for potential captures.
   * @param hex  The cell (player) making the move.
   * @return The number of captures that would result from making a move at the specified position.
   * @throws IllegalStateException if the game has not started
   */
  int countCaptures(IPosition pos, Cell hex);

  /**
   * Checks if there are any legal moves available for the specified cell (player).
   *
   * @param hex The cell (player) for whom to check legal moves.
   * @return True if there are legal moves available for the specified cell, false otherwise.
   * @throws IllegalStateException if the game has not started
   */
  boolean hasLegalMove(Cell hex);

  /**
   * Adds a ModelStatusListener to the list of listeners
   * that will be notified about changes in the
   * game model's state.
   *
   * @param listener The listener to be added.
   */
  void addModelStatusListener(ModelStatusListener listener);

  /**
   * Creates a position.
   * @param q q coordinate.
   * @param r r coordinate.
   * @return the position.
   */
  IPosition createPosition(int q, int r);

  /**
   * Differentiates size for iterations for square and hexagonal reversis.
   * @return a list, first item is the starting iteration, second is the ending.
   */
  List<Integer> createSize();

  List<IPosition> getCorners();
}