package model;

/**
 * Represents a Cell on the board. It contains a certain state:
 * white, or black.
 */
public enum Cell {
  WHITE("White"), BLACK("Black"); //representing the states for players cells

  private final String cellStr;

  /**
   * Constructs a cell.
   * @param c the string that describes the cells state.
   */
  Cell(String c) {
    this.cellStr = c;
  }

  /**
   * Develops the cells state into a string.
   * @return a string describing the cell's state:
   *         black or white.
   */
  @Override
  public String toString() {
    return cellStr;
  }
}
