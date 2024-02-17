package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Cell;
import model.IPosition;
import model.IReversiModel;

/**
 * Mock model of ReversiModel, testing input and which methods are called
 * with a player action.
 */
public class MockReversiModel implements IReversiModel {
  private StringBuilder log;
  private boolean gameStarted = false;
  private Cell currentTurn = Cell.BLACK; // Default starting turn

  public MockReversiModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void executeMove(IPosition pos) {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started!");
    }
    log.append(String.format("executeMove %s\n", pos));
  }

  @Override
  public boolean isGameOver() {
    log.append("isGameOver\n");
    return false;
  }

  @Override
  public int getScore(Cell hex) {
    log.append(String.format("getScore %s\n", hex));
    return 0; // Mock implementation
  }

  @Override
  public Optional<Cell> getWinner() {
    log.append("getWinner\n");
    return Optional.empty(); // Mock implementation
  }

  @Override
  public Optional<Cell> getHexAt(IPosition p) {
    log.append(String.format("getHexAt %s\n", p));
    return Optional.empty(); // Mock implementation
  }

  @Override
  public void startGame() {
    log.append("startGame\n");
    this.gameStarted = true;
  }

  @Override
  public void passTurn() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started!");
    }
    log.append("passTurn\n");
    // Toggle current turn in mock implementation
    currentTurn = (currentTurn == Cell.BLACK) ? Cell.WHITE : Cell.BLACK;
  }

  @Override
  public Optional<Cell>[][] getBoard() {
    log.append("getBoard\n");
    return new Optional[0][]; // Mock implementation
  }

  @Override
  public Cell getCurrentTurn() {
    log.append("getCurrentTurn\n");
    return currentTurn; // Return current turn
  }

  @Override
  public boolean isALegalMove(IPosition pos, Cell hex) {
    log.append(String.format("isALegalMove %s %s\n", pos, hex));
    return true; // Mock implementation, can be set as needed for tests
  }

  @Override
  public int getSize() {
    log.append("getSize\n");
    return 0; // Mock implementation
  }

  @Override
  public int countCaptures(IPosition pos, Cell hex) {
    log.append(String.format("countCaptures %s %s\n", pos, hex));
    return 0; // Mock implementation
  }

  @Override
  public boolean hasLegalMove(Cell hex) {
    log.append(String.format("hasLegalMove %s\n", hex));
    return true; // Mock implementation
  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    log.append("addModelStatusListener\n");
  }

  @Override
  public IPosition createPosition(int q, int r) {
    log.append("createPosition: q = %d, r = %d", q, r);
    return null;
  }

  @Override
  public List<Integer> createSize() {
    log.append("createSize");
    return new ArrayList<>();
  }

  @Override
  public List<IPosition> getCorners() {
    return null;
  }

  @Override
  public boolean isAdjacentToCorner(IPosition corner, IPosition pos) {
    return false;
  }

  // Additional methods to manipulate and check mock state as needed
  public void setCurrentTurn(Cell currentTurn) {
    this.currentTurn = currentTurn;
  }

  public void setGameStarted(boolean gameStarted) {
    this.gameStarted = gameStarted;
  }

  // Getters for test assertions
  public String getLog() {
    return log.toString();
  }
}