package controller;

import javax.swing.KeyStroke;

import model.ReadOnlyReversiModel;
import view.IReversiFrameView;

/**
 * Mock view of ReversiFrameView.
 */
public class MockReversiFrameView implements IReversiFrameView {
  private final StringBuilder log = new StringBuilder();

  @Override
  public void display() {
    //empty because public method, so needs to be implemented, but it is
    //irrelevant in this mock
  }

  @Override
  public void addFeatures(Features f) {
    //empty because public method, so needs to be implemented, but it is
    //irrelevant in this mock
  }

  @Override
  public void setHotKey(KeyStroke key, String featureName) {
    //empty because public method, so needs to be implemented, but it is
    //irrelevant in this mock
  }

  @Override
  public void updateView(ReadOnlyReversiModel model) {
    log.append("View's current turn is updated\n");
  }

  @Override
  public void showTurnIndicator(String s) {
    log.append("showTurnIndicator: ").append(s).append("\n");

  }

  @Override
  public void showScore(String s) {
    log.append("showScore: ").append(s).append("\n");
  }

  @Override
  public void repaintView() {
    log.append("repaintView: ").append("\n");
  }

  /**
   * Shows the log output.
   * @return the string of the log.
   */
  public String getLog() {
    return log.toString();
  }
}
