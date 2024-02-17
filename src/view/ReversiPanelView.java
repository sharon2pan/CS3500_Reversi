package view;

import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JPanel;


import controller.Features;
import model.Cell;
import model.IPosition;
import model.Position;
import model.ReadOnlyReversiModel;

/**
 * The {@code ReversiPanelView} class manages the graphical
 * representation of the Reversi board game.
 * It extends {@code JPanel} to handle custom drawing and
 * user interactions such as mouse clicks
 * and keyboard events. The class maintains the game state
 * and updates the display accordingly.
 */
public class ReversiPanelView extends JPanel implements IReversiPanelView {
  private final ReadOnlyReversiModel model; //model that holds the game state
  private int size; //size of the board
  private final Point origin; //origin point for drawing the board
  private final int radiusSize; //radius size of the board, used for bounds checking
  private final Cell[][] boardState; //state of the board array
  private Optional<IPosition> highlightedCell; //holds currently highlighted cell, if any
  private final List<Features> featureListeners;
  private IReversiPanelView decorator;

  //////////////// Initialization and Setup ////////////////

  /**
   * Constructs a {@code ReversiPanelView} with the specified
   * model. It initializes the board size,
   * origin for drawing, radius size for bounds checking,
   * board state, and the current player.
   * It also sets up mouse and key listeners for handling
   * user interactions.
   *
   * @param model The read-only model of the Reversi game
   *              that provides the necessary information
   *              to render the game state.
   */
  public ReversiPanelView(ReadOnlyReversiModel model) {
    this.model = model;
    this.size = calculateHexagonSize();
    this.origin = new Point();
    this.radiusSize = (model.getBoard().length - 1) / 2;
    this.boardState = new Cell[model.getBoard().length][model.getBoard().length];
    this.highlightedCell = Optional.empty();
    this.featureListeners = new ArrayList<>();
    setupMouseListener2();
    setupKeyListener2();
    resizingListener();
  }

  /**
   * Retrieve the model.
   *
   * @return the read-only model of the Reversi game
   *              that provides the necessary information
   *              to render the game state.
   */
  public ReadOnlyReversiModel getModel() {
    return this.model;
  }

  /**
   * Retrieves the Cell that is currently highlighted,
   * if there is one.
   *
   * @return the Position of the currently highlighted Cell,
   *         or Optional.empty() if there is no Cell highlighted
   */
  public Optional<IPosition> getHighlightedCell() {
    return this.highlightedCell;
  }

  /**
   * Set the decorator if the user indicates that
   * they would like to use it.
   *
   * @param decorator the decorator to set to the current view
   */
  public void setDecorator(IReversiPanelView decorator) {
    this.decorator = decorator;
  }

  //////////////// Event Handling ////////////////

  /**
   * Registers a set of game features with the panel view.
   * This method allows the panel to interact with the game controller, enabling
   * user interactions to initiate game actions.
   *
   * @param f The set of features (game actions) to be enabled in the view.
   */
  @Override
  public void addFeatures(Features f) {
    this.featureListeners.add(f);
  }

  /**
   * Initializes the mouse listener to handle click events on the Reversi board.
   * It determines if a click is within the bounds of the board and updates the
   * highlighted cell or performs an action based on the game rules.
   */
  @Override
  public void setupMouseListener2() {
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Position pos = pixelToLogicalCoords(e.getX(), e.getY());
        if (pos != null && boardLimits(pos)) {
          if (highlightedCell.isPresent() && highlightedCell.get().equals(pos)) {
            unhighlightCell();
          } else {
            highlightCell(pos);
          }
        } else {
          unhighlightCell();
        }
        repaint();
      }
    });
  }

  /**
   * Updates the visual of the board.
   */
  @Override
  public void repaintView() {
    repaint();
  }

  /**
   * Initializes the key listener to handle key press events for the Reversi game.
   * It allows players to make moves using keyboard input, such as placing a piece
   * on the board or passing the turn to the opponent.
   */
  @Override
  public void setupKeyListener2() {
    this.setFocusable(true);
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'm' && highlightedCell.isPresent()) {
          for (Features f : featureListeners) {
            f.executeMove(highlightedCell.get());
          }
        } else if (e.getKeyChar() == 'p') {
          for (Features f : featureListeners) {
            f.passTurn();
          }
        }
      }
    });
  }

  /**
   * Removes the highlight from the currently highlighted cell.
   */
  private void unhighlightCell() {
    highlightedCell = Optional.empty();
    repaint();
  }

  /**
   * Highlights the given cell.
   *
   * @param pos The position of the cell to highlight.
   */
  private void highlightCell(Position pos) {
    highlightedCell = Optional.of(pos);
    System.out.println("Highlighted Position: " + pos);
    repaint();
  }

  /**
   * Checks if the given position is within the board limits.
   *
   * @param pos The position to check.
   * @return True if the position is within board limits, false otherwise.
   */
  private boolean boardLimits(Position pos) {
    return pos.getQ() >= -radiusSize && pos.getQ() <= radiusSize
            && pos.getR() >= -radiusSize && pos.getR() <= radiusSize
            && pos.getS() >= -radiusSize && pos.getS() <= radiusSize;
  }

  /**
   * Resizes the board.
   */
  private void resizingListener() {
    this.addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent componentEvent) {
        size = calculateHexagonSize();
        repaint();
      }
    });
  }

  //////////////// Painting and Drawing ////////////////

  /**
   * Draws a hexagon with pointed top orientation on the given graphics context.
   * The hexagon's position is determined by its axial coordinates (q, r) on the board.
   * It can be optionally highlighted.
   *
   * @param g2d       The Graphics2D context on which to draw the hexagon.
   * @param q         The q-coordinate of the hexagon on the board.
   * @param r         The r-coordinate of the hexagon on the board.
   * @param highlight A boolean indicating whether the hexagon should be highlighted.
   */
  private void drawPointyTopHexagon(Graphics2D g2d, int q, int r, boolean highlight) {
    double x = origin.x + size * (Math.sqrt(3) * q + Math.sqrt(3) / 2 * r);
    double y = origin.y + size * (3.0 / 2 * r);

    Path2D.Double path = new Path2D.Double();
    for (int i = 0; i < 6; i++) {
      double angle = Math.toRadians(60 * i - 30);
      double x_i = x + size * Math.cos(angle);
      double y_i = y + size * Math.sin(angle);
      if (i == 0) {
        path.moveTo(x_i, y_i);
      }
      else {
        path.lineTo(x_i, y_i);
      }
    }
    path.closePath();
    g2d.setColor(highlight ? Color.CYAN : Color.RED.darker());
    g2d.fill(path);
    g2d.setColor(Color.WHITE);
    g2d.draw(path);
  }

  /**
   * Draws a circle within a hexagon on the given graphics
   * context.
   * The circle's center is specified by centerX and centerY,
   * and it is filled with the specified color.
   *
   * @param g2d     The Graphics2D context on which to draw the circle.
   * @param centerX The x-coordinate of the circle's center.
   * @param centerY The y-coordinate of the circle's center.
   * @param color   The color to fill the circle with.
   */
  private void drawCircleInHexagon(Graphics2D g2d, int centerX, int centerY, Color color) {
    int circleRadius = size / 2;
    int circleDiameter = circleRadius * 2;
    g2d.setColor(color);
    g2d.fillOval((centerX - circleRadius),
            (centerY - circleRadius), circleDiameter, circleDiameter);
  }

  /**
   * Paints the Reversi board component. This includes the background, the hexagonal grid,
   * and the pieces on the board. The method is called automatically by Swing when the
   * component needs to be redrawn.
   *
   * @param g The {@code Graphics} context used for painting.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.GREEN.darker().darker().darker());
    g2d.fillRect(0, 0, getWidth(), getHeight());

    this.size = calculateHexagonSize();
    updateOrigin();

    drawBoard(g2d);
    if (decorator != null) {
      decorator.paintComponent(g);
      repaint();
    }
    drawCirclesFromState(g2d);
  }

  /**
   * Draws the graphical representation of the board by
   * iterating through the board's axial
   * coordinates and drawing hexagons and game pieces at
   * each position. It uses the drawPointyTopHexagon
   * method to draw each hexagon and optionally highlights
   * them if they are the currently highlighted cell.
   *
   * @param g2d The Graphics2D context on which to draw the
   *            board.
   */
  private void drawBoard(Graphics2D g2d) {
    int radius = (model.getBoard().length - 1) / 2;
    for (int q = -radius; q <= radius; q++) {
      int r1 = Math.max(-radius, -q - radius);
      int r2 = Math.min(radius, -q + radius);
      for (int r = r1; r <= r2; r++) {
        if (q + r <= radius) {
          Position pos = new Position(q, r, -q - r);
          boolean shouldHighlight = highlightedCell.isPresent()
                  && highlightedCell.get().equals(pos);
          drawPointyTopHexagon(g2d, q, r, shouldHighlight);
          drawModel(g2d, pos, q, r);
        }
      }
    }
  }

  /**
   * Draws a model representation based on the state of a hexagonal cell.
   * This method checks the state of the hex cell at the specified position
   * and draws a circle within the hexagon. The color of the circle is determined
   * by the cell state: green for a BLACK cell, and red for a WHITE cell.
   *
   * @param g2d the {@link Graphics2D} object used for drawing.
   * @param pos the {@link Position} object representing the position of the hex cell.
   * @param q   the horizontal coordinate in the hexagonal grid.
   * @param r   the vertical coordinate in the hexagonal grid.
   */
  private void drawModel(Graphics2D g2d, Position pos, int q, int r) {
    if (model.getHexAt(pos).isPresent()) {
      if (model.getHexAt(pos).get() == Cell.BLACK) {
        drawCircleInHexagon(g2d, logicalToPixelCoordsX(q, r),
                logicalToPixelCoordsY(r), Color.BLACK);
      } else if (model.getHexAt(pos).get() == Cell.WHITE) {
        drawCircleInHexagon(g2d, logicalToPixelCoordsX(q, r),
                logicalToPixelCoordsY(r), Color.WHITE);
      }
    }
  }

  /**
   * Draws the game pieces on the board based on the current state. This method iterates through
   * the board state array and calls drawCircleInHexagon for each cell that has a game piece.
   *
   * @param g2d The Graphics2D context on which to draw the game pieces.
   */
  private void drawCirclesFromState(Graphics2D g2d) {
    for (int q = -radiusSize; q <= radiusSize; q++) {
      for (int r = -radiusSize; r <= radiusSize; r++) {
        if (boardState[q + radiusSize][r + radiusSize] != null) {
          int x = logicalToPixelCoordsX(q, r);
          int y = logicalToPixelCoordsY(r);
          Color color = (boardState[q +
                  radiusSize][r + radiusSize] == Cell.BLACK) ? Color.BLACK : Color.WHITE;
          drawCircleInHexagon(g2d, x, y, color);
        }
      }
    }
  }

  //////////////// Coordinate Conversion ////////////////

  /**
   * Converts logical board coordinates (axial) to pixel X coordinates for drawing on the screen.
   *
   * @param q The q-coordinate on the hexagonal board grid.
   * @param r The r-coordinate on the hexagonal board grid.
   * @return The X pixel coordinate corresponding to the logical board coordinates.
   */
  public int logicalToPixelCoordsX(int q, int r) {
    double x = (((q * size) + 1.0 / 3 * (logicalToPixelCoordsY(r) - origin.y))
            * 3 / Math.sqrt(3)) + origin.x;

    return (int) Math.round(x);
  }

  /**
   * Converts logical board coordinates to pixel Y coordinates for drawing.
   *
   * @param r The r-coordinate on the board.
   * @return The Y pixel coordinate.
   */
  public int logicalToPixelCoordsY(int r) {
    double y = (r * 3 * size / 2.0) + origin.y;

    return (int) Math.round(y);
  }

  /**
   * Converts pixel coordinates to logical board coordinates.
   *
   * @param x The X pixel coordinate.
   * @param y The Y pixel coordinate.
   * @return The logical position on the board.
   */
  private Position pixelToLogicalCoords(int x, int y) {
    double q = (Math.sqrt(3) / 3 * (x - origin.x) - 1.0 / 3 * (y - origin.y)) / size;
    double r = 2.0 / 3 * (y - origin.y) / size;
    double s = -q - r;

    return hexRound(q, r, s);
  }

  /**
   * Rounds the position.
   * @param q the q coordinate
   * @param r the r coordinate
   * @param s the s coordinate
   * @return the position rounded.
   */
  private Position hexRound(double q, double r, double s) {
    int roundedQ = (int) Math.round(q);
    int roundedR = (int) Math.round(r);
    int roundedS = (int) Math.round(s);

    double qDiff = Math.abs(roundedQ - q);
    double rDiff = Math.abs(roundedR - r);
    double sDiff = Math.abs(roundedS - s);

    if (qDiff > rDiff && qDiff > sDiff) {
      roundedQ = -roundedR - roundedS;
    } else if (rDiff > sDiff) {
      roundedR = -roundedQ - roundedS;
    } else {
      roundedS = -roundedQ - roundedR;
    }

    return new Position(roundedQ, roundedR, roundedS);
  }

  //////////////// Board Size and Scaling ////////////////

  /**
   * Provides the preferred size for this component. The preferred size is used by layout managers
   * to determine the best size for components.
   *
   * @return A Dimension object specifying the preferred size of the panel.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500, 500);

  }

  /**
   * Calculates the size of a hexagon based on the current window size to ensure that the
   * hexagons are appropriately scaled to fit the panel.
   *
   * @return The calculated size of a single hexagon.
   */
  private int calculateHexagonSize() {
    double horizontalSize = getWidth() / ((model.getBoard().length * 1.5) * 2);
    double verticalSize = getHeight() / ((model.getBoard().length * Math.sqrt(3)) + 0.5) + 1.25;

    return (int) Math.max(horizontalSize, verticalSize);
  }

  /**
   * Updates the origin point for drawing the hexagonal
   * grid based on the current size of the window.
   */
  private void updateOrigin() {
    origin.x = getWidth() / 2;
    origin.y = getHeight() / 2;
  }
}