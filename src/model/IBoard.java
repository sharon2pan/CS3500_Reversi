package model;

import java.util.Optional;

/**
 * Interface representing a game board for Reversi. It provides functionalities to get a cell,
 * place a hex, and check if a given position is valid. The reason for having this
 * interface is to enable flexibility for future implementations of a board, such that it could
 * be different shapes, etc.
 */
public interface IBoard {

  /**
   * Retrieves the cell at the given position on the board.
   *
   * @param pos The position of the cell to retrieve.
   * @return The cell at the specified position.
   */
  Optional<Cell> getCell(IPosition pos);

  /**
   * Places a hex at the specified position on the board.
   *
   * @param pos The position where the hex should be placed.
   * @param hex The hex (cell) to be placed on the board.
   */
  void placeHex(IPosition pos, Cell hex);

  /**
   * Checks if the given position is valid on the board.
   *
   * @param pos The position to check for validity.
   * @return {@code true} if the position is valid; {@code false} otherwise.
   */
  boolean isValidPosition(IPosition pos);

  /**
   * Retrieves the entire game board represented as a 2D array of cells.
   *
   * @return A 2D array of cells representing the current state of the game board.
   */
  Optional<Cell>[][] getBoardCells();

  /**
   * Retrieves the size of the board.
   *
   * @return The size of the board as an integer.
   */
  int getSize();
}
