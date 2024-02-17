package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import controller.ModelStatusListener;

/**
 * Mock Model for Reversi that transcribes what positions
 * the strategies call.
 */
public class MockReversi1 implements IReversiModel {
  private final int size;
  final StringBuilder log;

  public MockReversi1(StringBuilder log, int size) {
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
    for (Position dir : getNeighbors()) {
      Position neighbor = addPositions(pos, dir);
      if (!isValidPosition(neighbor)) { //for edge cases with less than 6 neighbors
        continue;
      }
    }
    log.append(pos + "\n");
    return false;
  }

  protected boolean isValidPosition(IPosition pos) {
    return pos.getQ() + pos.getR() + pos.getS() == 0 &&
            Math.abs(pos.getQ()) <= size &&
            Math.abs(pos.getR()) <= size &&
            Math.abs(pos.getS()) <= size;

  }

  /**
   * Provides an array of neighboring positions in a hexagonal grid.
   *
   * @return An array of neighboring positions.
   */
  protected Position[] getNeighbors() {
    return new Position[]{new Position(1, 0, -1), new Position(-1, 0, 1),
      new Position(0, 1, -1), new Position(0, -1, 1),
      new Position(1, -1, 0), new Position(-1, 1, 0)};
  }

  /**
   * Adds the coordinates of two positions together.
   *
   * @param pos1 The first position.
   * @param pos2 The second position.
   * @return A new position obtained by adding the coordinates of the two input positions.
   */
  protected Position addPositions(IPosition pos1, IPosition pos2) {
    return new Position(pos1.getQ() + pos2.getQ(), pos1.getR() + pos2.getR(),
            pos1.getS() + pos2.getS());
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
    for (int q = -size; q <= size; q++) {
      int r1 = Math.max(-size, -q - size);
      int r2 = Math.min(size, -q + size);
      for (int r = r1; r <= r2; r++) {
        int s = -q - r;
        Position pos = new Position(q, r, s);
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
    int s = -q - r;
    return new Position(q, r, s);
  }

  @Override
  public List<Integer> createSize() {
    List<Integer> size = new ArrayList<>();
    size.add(-getSize());
    size.add(getSize());
    return size;
  }

  @Override
  public List<IPosition> getCorners() {
    return List.of(new Position(-getSize(), getSize(), 0),
            new Position(getSize(), -getSize(), 0),
            new Position(0, -getSize(), getSize()),
            new Position(0, getSize(), -getSize()));
  }

  @Override
  public boolean isAdjacentToCorner(IPosition corner, IPosition pos) {
    return false;
  }
}

