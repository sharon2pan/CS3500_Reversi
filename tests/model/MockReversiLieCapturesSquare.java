package model;

import java.util.List;
import java.util.Optional;

import controller.ModelStatusListener;

/**
 * Mock that lies about the amount of captures for square Reversi.
 */
public class MockReversiLieCapturesSquare implements IReversiModel {
  StringBuilder log;
  IReversiModel delegate;

  public MockReversiLieCapturesSquare(IReversiModel delegate, StringBuilder log) {
    this.delegate = delegate;
    this.log = log;
  }

  @Override
  public void executeMove(IPosition pos) {
    this.delegate.executeMove(pos);
  }

  @Override
  public boolean isGameOver() {
    return this.delegate.isGameOver();
  }

  @Override
  public int getScore(Cell hex) {
    return this.delegate.getScore(hex);
  }

  @Override
  public Optional<Cell> getWinner() {
    return this.delegate.getWinner();
  }

  @Override
  public Optional<Cell> getHexAt(IPosition p) {
    return this.delegate.getHexAt(p);
  }

  @Override
  public void startGame() {
    this.delegate.startGame();
  }

  @Override
  public void passTurn() {
    this.delegate.passTurn();
  }

  @Override
  public Optional<Cell>[][] getBoard() {
    return this.delegate.getBoard();
  }

  @Override
  public Cell getCurrentTurn() {
    return this.delegate.getCurrentTurn();
  }

  @Override
  public boolean isALegalMove(IPosition pos, Cell hex) {
    return this.delegate.isALegalMove(pos, hex);
  }

  @Override
  public int getSize() {
    return this.delegate.getSize();
  }

  @Override
  public int countCaptures(IPosition pos, Cell hex) {
    int count = 0;
    if (pos.getQ() == 5 && pos.getR() == 3) {
      count = 5;
    }
    log.append(count + "\n");
    return count;
  }

  @Override
  public boolean hasLegalMove(Cell hex) {
    return this.delegate.hasLegalMove(hex);
  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    //empty because public method, so needs to be implemented, but it is
    //irrelevant in this mock
  }

  @Override
  public IPosition createPosition(int q, int r) {
    return delegate.createPosition(q, r);
  }

  @Override
  public List<Integer> createSize() {
    return delegate.createSize();
  }

  @Override
  public List<IPosition> getCorners() {
    return null;
  }

  @Override
  public boolean isAdjacentToCorner(IPosition corner, IPosition pos) {
    return false;
  }
}
