package view;


import java.awt.Component;
import java.awt.BorderLayout;

import javax.swing.*;

import controller.Features;
import model.ReadOnlyReversiModel;

/**
 * The {@code ReversiFrameView} class is responsible
 * for creating the main window (frame) of the Reversi game.
 * It extends {@code JFrame} and contains the game board
 * along with any other UI components that might be needed.
 * This class sets up the main window properties and
 * initializes the game board for display.
 */

public class ReversiFrameView extends JFrame implements IReversiFrameView {
  private IReversiPanelView gameBoard;
  private final JLabel score;
  private JButton hintToggleButton;
  private boolean hints;

  /**
   * Constructs a {@code ReversiFrameView} which sets up
   * the main window properties
   * for the Reversi game. It creates and adds the game
   * board panel to the frame.
   *
   * @param model The read-only model of the Reversi game
   *              which is used to initialize the board panel.
   */
  public ReversiFrameView(ReadOnlyReversiModel model) {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    setLayout(new BorderLayout());
    gameBoard = new ReversiPanelView(model);
    this.add((Component) gameBoard, BorderLayout.CENTER);
    score = new JLabel();
    JPanel scorePanel = new JPanel();
    scorePanel.add(score);
    this.add(scorePanel, BorderLayout.SOUTH);

    hintToggleButton = new JButton("Toggle Hints");
    hintToggleButton.addActionListener(e -> toggleHints());

    JPanel controlPanel = new JPanel();
    controlPanel.add(hintToggleButton);
    this.add(controlPanel, BorderLayout.NORTH);
    hints = false;
    pack();
    ((Component) gameBoard).requestFocusInWindow();
    //display();
    ((Component) gameBoard).requestFocusInWindow();
  }

  /**
   * Displays the hints on the game board or hides the hints
   * on the game board when the button is pressed.
   */
  private void toggleHints() {
    if (hints) {
      hints = false;
      gameBoard.setDecorator(null);
    } else {
      hints = true;
      System.out.print("Hints is true\n");
      gameBoard.setDecorator(new HintDecorator(gameBoard));
    }
    ((Component) gameBoard).repaint();
    ((Component) gameBoard).requestFocusInWindow();
  }


  /**
   * Displays the main game window (frame) of the Reversi
   * application.
   * This method should be responsible for making the game
   * frame visible to the user,
   * initializing and rendering all necessary UI components.
   */
  @Override
  public void display() {
    setVisible(true);
  }


  /**
   * Sets a keyboard shortcut (hotkey) for a specific game
   * feature.
   * This method links a key press to a particular game action, enhancing the
   * game's usability and accessibility.
   *
   * @param key The KeyStroke object representing the
   *            keyboard shortcut.
   * @param featureName The name of the feature to be
   *                    triggered by the hotkey.
   */
  @Override
  public void setHotKey(KeyStroke key, String featureName) { //do we need this????

  }

  /**
   * Registers a set of game features with the frame view.
   * This method allows the frame to interact with the game
   * controller, enabling
   * user interactions to initiate game actions.
   *
   * @param features The set of features (game actions) to be enabled in the view.
   */
  @Override
  public void addFeatures(Features features) {
    this.gameBoard.addFeatures(features);
  }

  /**
   * Updates the frame view based on the current state of
   * the game model.
   * This method refreshes the view to reflect any changes
   * in the game state,
   * ensuring that the visual representation is consistent
   * with the game's logic.
   *
   * @param model The read-only model of the Reversi game
   *              providing the necessary information.
   */
  @Override
  public void updateView(ReadOnlyReversiModel model) {
    setVisible(true);

    setTitle("Reversi - " + model.getCurrentTurn().toString() + "'s Turn");
  }

  /**
   * Updates the visual representation of the game board or
   * other components.
   * This method is used to refresh specific parts of the
   * view without updating the entire game state.
   */
  @Override
  public void repaintView() {
    gameBoard.repaintView();
  }

  /**
   * Shows a turn indicator in the game view.
   * This method updates the view to notify the
   * player whose turn it is currently.
   *
   * @param message The message to display as the turn indicator.
   */
  @Override
  public void showTurnIndicator(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  /**
   * Displays the current score of the game.
   * This method updates the score display in the view,
   * showing the points
   * accumulated by each player.
   *
   * @param s The formatted string representing the current
   *          game score.
   */
  @Override
  public void showScore(String s) {
    score.setText(s);
  }
}