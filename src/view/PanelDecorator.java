package view;

import java.awt.Graphics;
import java.util.Optional;

import controller.Features;
import model.IPosition;
import model.Position;
import model.ReadOnlyReversiModel;

/**
 * The {@code PanelDecorator} class represents an IReversiPanelView
 * that can be used with decorators.
 */
public abstract class PanelDecorator implements IReversiPanelView { //why abstract??
  protected final IReversiPanelView panelView;

  /**
   * Constructs a {@code PanelDecorator} with the specified IReversiPanelView.
   * The PanelDecorator is layered around the IReversiPanelView as a feature
   * that may be enabled on or off at runtime.
   *
   * @param panelView the view that this PanelDecorator visually wraps around
   */
  public PanelDecorator(IReversiPanelView panelView) {
    this.panelView = panelView;
  }

  /**
   * Registers a set of game features with the frame view.
   * This method allows the frame to interact with the game
   * controller, enabling
   * user interactions to initiate game actions.
   *
   * @param f The set of features (game actions) to be
   *          enabled in the view.
   */
  @Override
  public void addFeatures(Features f) {
    panelView.addFeatures(f);
  }

  /**
   * Initializes and sets up the mouse listener for
   * the IReversiPanelView.
   * This method should define and attach a MouseListener
   * to the panel, handling
   * mouse events such as clicks. Typically, this will
   * involve determining the game
   * board position that corresponds to the mouse clicked
   * location and handling the game logic accordingly.
   */
  @Override
  public void setupMouseListener2() {
    panelView.setupMouseListener2();
  }

  /**
   * Initializes and sets up the key listener for the
   * IReversiPanelView.
   * This method should define and attach a KeyListener
   * to the panel, handling
   * key events such as key presses. It allows users to
   * interact with the game using
   * the keyboard, providing an alternative to mouse interactions. For example, the key
   * listener could be used to implement keyboard shortcuts
   * for game actions like placing a piece or passing a turn.
   */
  @Override
  public void setupKeyListener2() {
    panelView.setupKeyListener2();
  }

  @Override
  public void repaintView() {
    panelView.repaintView();
  }

  /**
   * Paints this PanelDecorator accordingly, with all of its
   * decorations. The method is called automatically by Swing
   * when the component needs to be redrawn.
   *
   * @param g The {@code Graphics} context used for painting.
   */
  @Override
  public void paintComponent(Graphics g) {
    panelView.paintComponent(g);
  }

  /**
   * Updates the visual representation of the game board.
   * This method is called to refresh the panel view, ensuring that it accurately
   * reflects the current state of the game model. It is typically invoked after
   * game actions like moves or turns have been processed.
   */
  @Override
  public ReadOnlyReversiModel getModel() {
    return panelView.getModel();
  }

  /**
   * Retrieves the Cell that is currently highlighted,
   * if there is one.
   *
   * @return the Position of the currently highlighted Cell,
   *         or Optional.empty() if there is no Cell highlighted
   */
  @Override
  public Optional<IPosition> getHighlightedCell() {
    return panelView.getHighlightedCell();
  }

  /**
   * Converts logical board coordinates to pixel X coordinates for drawing on the screen.
   *
   * @param q The q-coordinate on the hexagonal board grid.
   * @param r The r-coordinate on the hexagonal board grid.
   * @return The X pixel coordinate corresponding to the logical board coordinates.
   */
  @Override
  public int logicalToPixelCoordsX(int q, int r) {
    return panelView.logicalToPixelCoordsX(q, r);
  }

  /**
   * Converts logical board coordinates to pixel Y coordinates for drawing.
   *
   * @param r The r-coordinate on the board.
   * @return The Y pixel coordinate.
   */
  @Override
  public int logicalToPixelCoordsY(int r) {
    return panelView.logicalToPixelCoordsY(r);
  }

  /**
   * Set the decorator if the user indicates that
   * they would like to use it.
   *
   * @param decorator the decorator to set to the current view
   */
  @Override
  public void setDecorator(IReversiPanelView decorator) {
    panelView.setDecorator(decorator);
  }
}