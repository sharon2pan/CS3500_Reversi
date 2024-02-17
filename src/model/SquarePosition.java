package model;

import java.util.Objects;

/**
 * Represents the position of a cell on a square board. Uses the coordinates
 * q and r, which represent x and y, respectively. The top left of the board
 * is (0,0) and the bottom right is (size * 2 - 1, size * 2 - 1).
 */
public class SquarePosition implements IPosition {
  private final int q;
  private final int r;

  /**
   * Constructs a position.
   *
   * @param q coordinate
   * @param r coordinate
   */
  public SquarePosition(int q, int r) {
    this.q = q;
    this.r = r;
  }

  /**
   * Puts the SquarePosition coordinates in string form.
   *
   * @return a string of a cell's position.
   */
  @Override
  public String toString() {
    return "Position[q=" + q + ", r=" + r + "]";
  }

  /**
   * Compares the current SquarePosition to the specified object for equality.
   *
   * @param o the object to compare with.
   * @return {@code true} if the specified object
   *         represents an equivalent card; {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SquarePosition)) {
      return false;
    }
    SquarePosition that = (SquarePosition) o;
    return this.q == that.q && this.r == that.r;}

  /**
   * Returns a hash code value for the SquarePosition, based on its q and r coordinates.
   *
   * @return a hash code value for this SquarePosition.
   */
  @Override
  public int hashCode() {
    return Objects.hash(q, r);
  }

  //Left as package-private to lessen the ability for modification

  /**
   * Gets the q coordinate.
   *
   * @return q
   */
  public int getQ() {
    return q;
  }

  /**
   * Gets the r coordinate.
   *
   * @return r
   */
  public int getR() {
    return r;
  }

  /**
   * Gets the s coordinate.
   *
   * @return s
   */
  public int getS() {
    return 0; //there is no s coordinate in a SquarePosition because it only has 2 axis
  }
}
