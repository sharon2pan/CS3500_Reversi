package model;

import java.util.Optional;

/**
 * Represents the game board for Reversi.
 */
public class Board implements IBoard {
  private final int size; //size of the board
  private final Optional<Cell>[][] board; //game board

  /**
   * Constructs the board object.
   *
   * @param size size of the board.
   */
  public Board(int size) {
    if (size <= 0) {
      throw new IllegalArgumentException("Size cannot be non-positive");
    }
    this.size = size;
    int gridLength = 2 * size + 1;
    //Need to have positive and negative directions, plus 1 for center
    board = new Optional[gridLength][gridLength]; //initializes board with our length

    for (int q = -size; q <= size; q++) {
      int r1 = Math.max(-size, -q - size);
      int r2 = Math.min(size, -q + size); //determine r range for each q
      for (int r = r1; r <= r2; r++) {
        int s = -q - r; //computes s
        board[q + size][r + size] = Optional.empty(); // All cells start empty
      }
    }
  }

  /**
   * Retrieves the size of the board.
   *
   * @return The size of the board as an integer.
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Constructs a new Board instance with a given board
   * and size.
   *
   * @param existingBoard The initial configuration of the
   *                      board as a 2D array of {@link Optional} {@link Cell} objects.
   *                      Each cell can be either present
   *                      (non-empty) or absent (empty).
   * @param size          The size of the board. This
   *                      typically represents the dimension (width/height) of the board.
   */
  public Board(Optional<Cell>[][] existingBoard, int size) {
    this.size = size;
    this.board = deepCopyBoard(existingBoard);
  }

  /**
   * Creates a deep copy of the original board.
   * This method is used internally to ensure that the board's
   * state is independent of the
   * state of any board passed into the constructor.
   *
   * @param originalBoard The original board as a 2D array of {@link Optional} {@link Cell} objects.
   * @return A new 2D array of {@link Optional} {@link Cell} objects, which
   *         is a deep copy of the original board.
   */
  private Optional<Cell>[][] deepCopyBoard(Optional<Cell>[][] originalBoard) {
    Optional<Cell>[][] copy = new Optional[originalBoard.length][originalBoard[0].length];
    for (int i = 0; i < originalBoard.length; i++) {
      System.arraycopy(originalBoard[i], 0, copy[i], 0, originalBoard[i].length);
    }
    return copy;
  }

  /**
   * Gets the Cell value at a given position.
   *
   * @param pos the given position
   * @return the cell value
   */
  public Optional<Cell> getCell(IPosition pos) {
    return board[pos.getQ() + size][pos.getR() + size];
  }

  /**
   * Places a Cell value hex at a given position.
   *
   * @param pos position of a cell
   * @param hex a cell that holds a value.
   */
  public void placeHex(IPosition pos, Cell hex) {
    if (!isValidPosition(pos)) {
      throw new IllegalArgumentException("Invalid Position");
    }
    board[pos.getQ() + size][pos.getR() + size] = Optional.of(hex);
  }

  /**
   * Checks if the given position is valid on the board.
   *
   * @param pos the given position to check
   * @return true if the position is on the board and false if
   *         the position if off the board.
   */
  public boolean isValidPosition(IPosition pos) {
    return pos.getQ() + pos.getR() + pos.getS() == 0
            && Math.abs(pos.getQ()) <= size
            && Math.abs(pos.getR()) <= size
            && Math.abs(pos.getS()) <= size;
  }

  /**
   * Be able to access the board of cells.
   *
   * @return the board of cells.
   */
  public Optional<Cell>[][] getBoardCells() {
    return this.board;
  }
}
