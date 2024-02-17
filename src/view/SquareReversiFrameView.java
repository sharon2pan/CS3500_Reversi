package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import controller.Features;
import model.ReadOnlyReversiModel;

/**
 * The {@code SquareReversiFrameView} class is responsible for creating the main window (frame)
 * for the square grid version of the Reversi game. It extends {@code JFrame} and contains the
 * game board panel along with any other UI components that might be needed. This class sets up
 * the main window properties and initializes the game board for display.
 */
public class SquareReversiFrameView extends JFrame implements IReversiFrameView {
  private final SquareReversiPanelView gameBoard;
  private final JLabel score;

  /**
   * Constructs a {@code SquareReversiFrameView} which sets up the main window properties
   * for the square grid Reversi game. It creates and adds the game board panel to the frame.
   *
   * @param model The read-only model of the Reversi game which is used
   *             to initialize the board panel.
   */
  public SquareReversiFrameView(ReadOnlyReversiModel model) {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    gameBoard = new SquareReversiPanelView(model);
    this.add(gameBoard, BorderLayout.CENTER);

    score = new JLabel();
    JPanel scorePanel = new JPanel();
    scorePanel.add(score);
    this.add(scorePanel, BorderLayout.SOUTH);

    pack();
    display();
  }

  /**
   * Makes the game frame visible and ensures that all UI components are properly rendered.
   */
  @Override
  public void display() {
    setVisible(true);
  }

  /**
   * Sets a keyboard shortcut for a specific action within the game.
   *
   * @param key          The KeyStroke that represents the keyboard shortcut.
   * @param featureName  The name of the feature to be triggered by the keyboard shortcut.
   */
  @Override
  public void setHotKey(KeyStroke key, String featureName) {
    gameBoard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key, featureName);
  }

  /**
   * Registers the game features with the view, enabling user interactions to initiate
   * game actions.
   *
   * @param features The set of features (game actions) to be added to the view.
   */
  @Override
  public void addFeatures(Features features) {
    gameBoard.addFeatures(features);
  }

  /**
   * Updates the game frame view based on the current state of the game model.
   *
   * @param model The read-only model of the Reversi game providing the necessary information.
   */
  @Override
  public void updateView(ReadOnlyReversiModel model) {
    setTitle("Square Reversi - " + model.getCurrentTurn().toString() + "'s Turn");

  }

  /**
   * Refreshes the game board view to reflect any changes
   * in the game state.
   */
  @Override
  public void repaintView() {
    gameBoard.repaintView();
  }

  /**
   * Displays a message indicating whose turn it is in the game.
   *
   * @param message The message to display as the turn indicator.
   */
  @Override
  public void showTurnIndicator(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  /**
   * Displays the current score of the game in the view.
   *
   * @param s The formatted string representing the current game score.
   */
  @Override
  public void showScore(String s) {
    score.setText(s);
  }
}