package model;

import java.util.Arrays;
import java.util.List;

/**
 * Mock that lies about what moves are legal.
 */
public class MockReversiAvoidCorners extends MockReversi1 {

  /** Constructor for mock.
   * Mock Constructor
   * @param log constructs the string.
   * @param size size of the board
   */
  public MockReversiAvoidCorners(StringBuilder log, int size) {
    super(log, size);
  }

  @Override
  public boolean isALegalMove(IPosition pos, Cell hex) {
    List<Position> corners = Arrays.asList(
            new Position(-getSize(), getSize(), 0),
            new Position(getSize(), -getSize(), 0),
            new Position(0, -getSize(), getSize()),
            new Position(0, getSize(), -getSize())
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
    for (Position dir : getNeighbors()) {
      Position neighbor = addPositions(corner, dir);
      if (neighbor.equals(pos)) {
        return true;
      }
    }
    return false;
  }
}