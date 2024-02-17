package controller;

import model.IPosition;

public class MockFeatures implements Features {
  private final String input;
  private final StringBuilder log = new StringBuilder();

  public MockFeatures(String input) {
    this.input = input;
  }

  @Override
  public void executeMove(IPosition pos) {
    if (input.equals("m")) {
      log.append("executeMove:" + pos.toString());
    }
  }

  @Override
  public void passTurn() {
    log.append("passTurn");
  }

  @Override
  public void updateView() {
    log.append("updateView");
  }

  public String getLog() {
    return log.toString();
  }
}
