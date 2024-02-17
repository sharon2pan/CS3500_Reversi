package view;

import javax.swing.KeyStroke;

import controller.Features;
import model.ReadOnlyReversiModel;

/**
 * The IReversiFrameView interface defines the contract for a
 * frame view in a Reversi game application.
 * This interface is responsible for outlining the essential
 * functionalities of the main window (frame)
 * of the Reversi game. Implementations of this interface
 * manage the overall layout and
 * visibility of the game's graphical user interface.
 */
public interface IReversiFrameView {

  /**
   * Displays the main game window (frame) of the Reversi
   * application.
   * This method should be responsible for making the game
   * frame visible to the user.
   */
  void display();

  /**
   * Registers a set of game features with the frame view.
   * This method allows the frame to interact with the game
   * controller, enabling
   * user interactions to initiate game actions.
   *
   * @param f The set of features (game actions) to be
   *          enabled in the view.
   */
  void addFeatures(Features f);

  /**
   * Sets a keyboard shortcut (hotkey) for a specific game
   * feature.
   * This method links a key press to a particular game action,
   * enhancing the game's usability and accessibility.
   *
   * @param key The KeyStroke object representing the
   *            keyboard shortcut.
   * @param featureName The name of the feature to be triggered
   *                    by the hotkey.
   */
  void setHotKey(KeyStroke key, String featureName);

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
  void updateView(ReadOnlyReversiModel model);

  /**
   * Shows a turn indicator in the game view.
   * This method updates the view to notify the player
   * whose turn it is currently.
   *
   * @param s The message to display as the turn indicator.
   */
  void showTurnIndicator(String s);

  /**
   * Displays the current score of the game.
   * This method updates the score display in the view,
   * showing the points
   * accumulated by each player.
   *
   * @param s The formatted string representing the current
   *          game score.
   */
  void showScore(String s);

  /**
   * Updates the visual representation of the game board or
   * other components.
   * This method is used to refresh specific parts of the view
   * without updating the entire game state.
   */
  void repaintView();
}
