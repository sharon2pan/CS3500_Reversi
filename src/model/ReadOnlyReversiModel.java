package model;

import java.util.List;
import java.util.Optional;

/**
 * ReadOnlyReversi represents an unmodidifiable version that only allows uses for
 * observable methods to be called upon.
 */
public interface ReadOnlyReversiModel {
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
   * Checks if a move to a cell is legal.
   *
   * @param pos position of the proposed move.
   * @param hex players cell that it wants to move.
   * @return if the move is legal, return true, if not, return false.
   */
  boolean isALegalMove(IPosition pos, Cell hex);

  /**
   * Checks if the game is over based on the current state of the board.
   *
   * @return true if the game is over, false otherwise.
   * @throws IllegalStateException if the game has not started
   */
  boolean isGameOver();

  /**
   * Retrieves the radius of the outer circle that would
   * touch the corners.
   *
   * @return size of the hexagon
   */
  int getSize();

  /**
   * Determines if there exists a legal move for the specified hex on the board.
   * A move is considered legal if the hex can be placed on an empty cell
   * and the move is validated as legal by the isALegalMove method.
   *
   * @param hex The hex to check for potential legal moves.
   * @return True if there exists a legal move for the specified hex; false otherwise.
   */
  boolean hasLegalMove(Cell hex);

  /**
   * The number of opposite-colored discs turned when a move is made.
   *
   * @param pos The position the current player wants to make a move at
   * @param hex The current player
   * @return Number of discs turned
   */
  int countCaptures(IPosition pos, Cell hex);

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

  boolean isAdjacentToCorner(IPosition corner, IPosition pos);
}