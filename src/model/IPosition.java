package model;

/**
 * The IPosition interface represents a position on a Reversi board.
 * It encapsulates the coordinates of a position in a hexagonal grid system,
 * typically used in games like Reversi. Each position is defined by three
 * coordinates: q, r, and s.
 */
public interface IPosition {
  /**
   * Returns a string representation of the position.
   * This typically includes the coordinates (q, r, s) in a readable format.
   *
   * @return A string representation of the position.
   */
  String toString();

  /**
   * Compares this position with the specified object for equality.
   * The result is {@code true} if and only if the argument is not
   * {@code null} and is an IPosition object that represents the same
   * position as this object.
   *
   * @param o The object to compare this IPosition against.
   * @return {@code true} if the given object represents an IPosition
   *         equivalent to this position, {@code false} otherwise.
   */
  boolean equals(Object o);

  /**
   * Returns a hash code value for the position.
   * This method is supported for the benefit of hash tables such as those
   * provided by {@link java.util.HashMap}.
   *
   * @return A hash code value for this position.
   */
  int hashCode();

  /**
   * Gets the Q coordinate of this position.
   * The Q coordinate is one of the three coordinates used to define a position
   * in the hexagonal grid of the Reversi board.
   *
   * @return The Q coordinate of this position.
   */
  int getQ();

  /**
   * Gets the R coordinate of this position.
   * The R coordinate is another of the three coordinates used to define a position
   * in the hexagonal grid of the Reversi board.
   *
   * @return The R coordinate of this position.
   */
  int getR();

  /**
   * Gets the S coordinate of this position.
   * The S coordinate is the third of the three coordinates used to define a position
   * in the hexagonal grid of the Reversi board.
   *
   * @return The S coordinate of this position.
   */
  int getS();
}
