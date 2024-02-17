package model;

import java.util.Arrays;
import java.util.List;

/**
 * Mock that lies about what moves are legal for square Reversi.
 */
public class MockReversiAvoidCornersSquare extends MockReversiSquare {

  /** Constructor for mock.
   * Mock Constructor
   * @param log constructs the string.
   * @param size size of the board
   */
  public MockReversiAvoidCornersSquare(StringBuilder log, int size) {
    super(log, size);
  }

  @Override
  public boolean isALegalMove(IPosition pos, Cell hex) {
    List<IPosition> corners = Arrays.asList(
            new SquarePosition(0, 0),
            new SquarePosition(0, getSize() * 2 - 1),
            new SquarePosition(getSize() * 2 - 1, 0),
            new SquarePosition(getSize() * 2 - 1, getSize() * 2- 1)
    );

    if (!isValidPosition(pos)) {
      return false;
    }

    // Check if the position is adjacent to any corner
    boolean isLegal = corners.stream().anyMatch(corner -> isAdjacentTo(corner, pos));

    log.append(pos + (isLegal ? " legal\n" : " not legal\n"));
    return isLegal;
  }

  /**
   * Checks if the given position is adjacent to the specified corner.
   *
   * @param corner The corner position.
   * @param pos    The position to check.
   * @return true if pos is adjacent to corner, false otherwise.
   */
  private boolean isAdjacentTo(IPosition corner, IPosition pos) {
    return Math.abs(corner.getQ() - pos.getQ()) <= 1 &&
            Math.abs(corner.getR() - pos.getR()) <= 1;
  }
}
