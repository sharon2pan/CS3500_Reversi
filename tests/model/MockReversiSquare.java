package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import controller.ModelStatusListener;

/**
 * Mock Model for SquareReversi that transcribes what positions
 * the strategies call.
 */
public class MockReversiSquare implements IReversiModel {
  private final int size;
  final StringBuilder log;

  public MockReversiSquare(StringBuilder log, int size) {
    this.size = size;
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public int getScore(Cell hex) {
    return 0;
  }

  @Override
  public Optional<Cell> getWinner() {
    return Optional.empty();
  }

  @Override
  public Optional<Cell> getHexAt(IPosition p) {
    return Optional.empty();
  }

  @Override
  public void startGame() {
    return;
  }

  @Override
  public void passTurn() {
    return;
  }

  @Override
  public Optional<Cell>[][] getBoard() {
    return new Optional[0][];
  }

  @Override
  public Cell getCurrentTurn() {
    return null;
  }

  // Verifies that all neighbors of the given Cell are checked
  // by appending each position to the StringBuilder
  @Override
  public boolean isALegalMove(IPosition pos, Cell hex) {
    if (!isValidPosition(pos)) {
      return false;
    }
    for (SquarePosition neighbor : getNeighbors(pos)) {
      if (!isValidPosition(neighbor)) { //for edge cases with less than 6 neighbors
        continue;
      }
    }
    log.append(pos + "\n");
    return false;
  }

  protected boolean isValidPosition(IPosition pos) {
    return pos.getQ() < size * 2 && pos.getQ() >= 0
            && pos.getR() < size * 2 && pos.getR() >= 0;
  }

  /**
   * Provides an array of neighboring positions in a hexagonal grid.
   *
   * @return An array of neighboring positions.
   */
  protected SquarePosition[] getNeighbors(IPosition pos) {
    return new SquarePosition[]{
            new SquarePosition(pos.getQ() - 1, pos.getR() - 1), //top-left
            new SquarePosition(pos.getQ(), pos.getR() - 1), // up
            new SquarePosition(pos.getQ() + 1, pos.getR() - 1), //top-right
            new SquarePosition(pos.getQ() - 1, pos.getR() + 1), //bottom-left
            new SquarePosition(pos.getQ(), pos.getR() + 1), //down
            new SquarePosition(pos.getQ() + 1, pos.getR() + 1), //bottom-right
            new SquarePosition(pos.getQ() - 1, pos.getR()), //left
            new SquarePosition(pos.getQ() + 1, pos.getR())}; //right
  }

  /**
   * Adds the coordinates of two positions together.
   *
   * @param pos1 The first position.
   * @param pos2 The second position.
   * @return A new position obtained by adding the coordinates of the two input positions.
   */
  protected IPosition addPositions(IPosition pos1, IPosition pos2) {
    return new SquarePosition(pos1.getQ() + pos2.getQ(), pos1.getR() + pos2.getR());
  }

  @Override
  public void executeMove(IPosition pos) {
    return; //empty
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public int countCaptures(IPosition pos, Cell hex) {
    return 0;
  }

  /** Verifies if all possible locations for possible moves are checked
   * by appending each position to the StringBuilder.
   **/

  @Override
  public boolean hasLegalMove(Cell hex) {
    for (int q = 0; q <= size * 2 - 1; q++) {
      for (int r = 0; r <= size * 2 - 1; r++) {
        SquarePosition pos = new SquarePosition(q, r);
        log.append(pos + "\n");
      }
    }
    return false;
  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    //empty because public method, so needs to be implemented, but it is
    //irrelevant in this mock
  }

  @Override
  public IPosition createPosition(int q, int r) {
    return new SquarePosition(q, r);
  }

  @Override
  public List<Integer> createSize() {
    List<Integer> size = new ArrayList<>();
    size.add(0);
    size.add(getSize() * 2 - 1);
    return size;
  }

  @Override
  public List<IPosition> getCorners() {
    List<IPosition> corners = Arrays.asList(
            new SquarePosition(0, 0),
            new SquarePosition(0, getSize() * 2 - 1),
            new SquarePosition(getSize() * 2 - 1, 0),
            new SquarePosition(getSize() * 2 - 1, getSize() * 2 - 1)
    );
    return corners;
  }

  @Override
  public boolean isAdjacentToCorner(IPosition corner, IPosition pos) {
    return Math.abs(corner.getQ() - pos.getQ()) <= 1 &&
            Math.abs(corner.getR() - pos.getR()) <= 1;
  }
}

