package model;

import java.util.Objects;

/**
 * Represents the position of a cell on the board. Uses the cubic coordinate
 * system of q, r, and s. The origin is at Position(0, 0, 0)
 */
public class Position implements IPosition {
  private final int q;
  private final int r;
  private final int s; //coordinates for a cell/hexagon on the board

  /**
   * Constructs a position.
   *
   * @param q coordinate
   * @param r coordinate
   * @param s coordinate
   */
  public Position(int q, int r, int s) {
    this.q = q;
    this.r = r;
    this.s = s;
  }

  /**
   * Puts the Position coordinates in string form.
   *
   * @return a string of a cell's position.
   */
  @Override
  public String toString() {
    return "Position[q=" + q + ", r=" + r + ", s=" + s + "]";
  }

  /**
   * Compares the current Position to the specified object for equality.
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
    if (!(o instanceof Position)) {
      return false;
    }
    Position that = (Position) o;
    return this.q == that.q && this.r == that.r
            && this.s == that.s;
  }

  /**
   * Returns a hash code value for the position, based on its axial coordinates.
   *
   * @return a hash code value for this position.
   */
  @Override
  public int hashCode() {
    return Objects.hash(q, r, s);
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
    return s;
  }


}
