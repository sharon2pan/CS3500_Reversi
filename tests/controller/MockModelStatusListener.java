package controller;

import model.Cell;

/**
 * Mock model status listener.
 */
public class MockModelStatusListener implements ModelStatusListener {
  private String log = "";

  @Override
  public void updateTurnChanged(Cell currentPlayer) {
    log += "TurnChanged: " + currentPlayer + "\n";
  }

  @Override
  public void updateGameBoard() {
    log += "GameBoardUpdated\n";
  }

  @Override
  public void updateScore(int scoreBlack, int scoreWhite) {
    log += "ScoreUpdated: Black - " + scoreBlack + ", White - " + scoreWhite + "\n";
  }

  public String getLog() {
    return log;
  }
}
