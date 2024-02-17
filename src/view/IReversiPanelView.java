package view;

import java.awt.Graphics;
import java.util.Optional;

import controller.Features;
import model.IPosition;
import model.Position;
import model.ReadOnlyReversiModel;

/**
 * The IReversiPanelView interface represents the contract
 * for a view component in a Reversi game.
 * It outlines the essential functionalities that a Reversi
 * game panel should provide, particularly
 * in terms of setting up user interaction listeners.
 * Implementing this interface allows for
 * different implementations of a Reversi game panel
 * while ensuring that they support
 * these fundamental interactions.
 */
public interface IReversiPanelView {

  void addFeatures(Features f);


  /**
   * Initializes and sets up the mouse listener for
   * the Reversi game panel.
   * This method should define and attach a MouseListener
   * to the panel, handling
   * mouse events such as clicks. Typically, this will
   * involve determining the game
   * board position that corresponds to the mouse clicked
   * location and handling the game logic accordingly.
   */
  void setupMouseListener2();

  /**
   * Initializes and sets up the key listener for the
   * Reversi game panel.
   * This method should define and attach a KeyListener
   * to the panel, handling
   * key events such as key presses. It allows users to
   * interact with the game using
   * the keyboard, providing an alternative to mouse interactions. For example, the key
   * listener could be used to implement keyboard shortcuts
   * for game actions like placing a piece or passing a turn.
   */
  void setupKeyListener2();

  /**
   * Updates the visual representation of the game board.
   * This method is called to refresh the panel view, ensuring that it accurately
   * reflects the current state of the game model. It is typically invoked after
   * game actions like moves or turns have been processed.
   */
  void repaintView();

  /**
   * Paints the Reversi board component. This includes the background, the hexagonal grid,
   * and the pieces on the board. The method is called automatically by Swing when the
   * component needs to be redrawn.
   *
   * @param g The {@code Graphics} context used for painting.
   */
  void paintComponent(Graphics g);

  /**
   * Retrieve the model.
   *
   * @return the read-only model of the Reversi game
   *              that provides the necessary information
   *              to render the game state.
   */
  ReadOnlyReversiModel getModel();

  /**
   * Retrieves the Cell that is currently highlighted,
   * if there is one.
   *
   * @return the Position of the currently highlighted Cell,
   *         or Optional.empty() if there is no Cell highlighted
   */
  Optional<IPosition> getHighlightedCell();

  /**
   * Converts logical board coordinates to pixel X coordinates for drawing on the screen.
   *
   * @param q The q-coordinate on the hexagonal board grid.
   * @param r The r-coordinate on the hexagonal board grid.
   * @return The X pixel coordinate corresponding to the logical board coordinates.
   */
  int logicalToPixelCoordsX(int q, int r);

  /**
   * Converts logical board coordinates to pixel Y coordinates for drawing.
   *
   * @param r The r-coordinate on the board.
   * @return The Y pixel coordinate.
   */
  int logicalToPixelCoordsY(int r);

  /**
   * Set the decorator if the user indicates that
   * they would like to use it.
   *
   * @param decorator the decorator to set to the current view
   */
  void setDecorator(IReversiPanelView decorator);
}