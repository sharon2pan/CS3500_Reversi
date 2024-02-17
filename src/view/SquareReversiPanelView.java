package view;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import controller.Features;
import model.Cell;
import model.IPosition;
import model.ReadOnlyReversiModel;
import model.SquarePosition;

/**
 * The {@code SquareReversiPanelView} class manages the graphical
 * representation of the Reversi board game.
 * It extends {@code JPanel} to handle custom drawing and
 * user interactions such as mouse clicks
 * and keyboard events. The class maintains the game state
 * and updates the display accordingly.
 */
public class SquareReversiPanelView extends JPanel implements IReversiPanelView {
  private final ReadOnlyReversiModel model; //model that holds the game state
  private int size; //size of the board
  private final Point origin; //origin point for drawing the board
  private final int radiusSize; //radius size of the board, used for bounds checking
  private final Cell[][] boardState; //state of the board array
  private Optional<IPosition> highlightedCell; //holds currently highlighted cell, if any
  private final List<Features> featureListeners;


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
  public SquareReversiPanelView(ReadOnlyReversiModel model) {
    this.model = model;
    //this.size = calculateSquareSize();
    this.origin = new Point();
    this.radiusSize = model.getBoard().length / 2;
    this.boardState = new Cell[model.getBoard().length][model.getBoard().length];
    this.highlightedCell = Optional.empty();
    this.featureListeners = new ArrayList<>();
    setupMouseListener2();
    setupKeyListener2();
    resizingListener();
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
        SquarePosition pos = pixelToLogicalCoords(e.getX(), e.getY());
        if (boardLimits(pos)) {
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
  private void highlightCell(SquarePosition pos) {
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
  private boolean boardLimits(SquarePosition pos) {
    int boardSize = model.getSize() * 2;
    return pos.getQ() >= 0 && pos.getQ() < boardSize &&
            pos.getR() >= 0 && pos.getR() < boardSize;}

  /**
   * Resizes the board.
   */
  private void resizingListener() {
    this.addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent componentEvent) {
        size = calculateSquareSize();
        repaint();
      }
    });
  }

  //////////////// Painting and Drawing ////////////////

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

    this.size = calculateSquareSize();
    updateOrigin();
    drawBoard(g2d);
  }

  @Override
  public ReadOnlyReversiModel getModel() {
    return this.model;
  }

  @Override
  public Optional<IPosition> getHighlightedCell() {
    return this.highlightedCell;
  }

  @Override
  public void setDecorator(IReversiPanelView decorator) {
    //nothing to do because SquareReversi does not have the hints
  }

  /**
   * Draws the graphical representation of the board by
   * iterating through the board's coordinates and
   * drawing squares and game pieces at
   * each position. It uses the drawSquare
   * method to draw each hexagon and optionally highlights
   * them if they are the currently highlighted cell.
   *
   * @param g2d The Graphics2D context on which to draw the
   *            board.
   */

  private void drawBoard(Graphics2D g2d) {
    int cellSize = calculateSquareSize() + 1;

    for (int q = 0; q < model.getSize() * 2; q++) {
      for (int r = 0; r < model.getSize() * 2; r++) {
        // Calculate the top left corner of the cell
        int x = q * cellSize;
        int y = r * cellSize;

        // Determine if this cell should be highlighted
        boolean isHighlighted = highlightedCell.isPresent() &&
                highlightedCell.get().getQ() == q &&
                highlightedCell.get().getR() == r;


        g2d.setColor(isHighlighted ? Color.CYAN : Color.GRAY);
        g2d.fillRect(x, y, cellSize, cellSize);


        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, cellSize, cellSize);

        // Check if the cell contains a disc and draw it
        Optional<Cell> cell = model.getHexAt(new SquarePosition(q, r));
        if (cell.isPresent()) {
          drawDisc(g2d, x, y, cellSize, cell.get());
        }
      }
    }
  }

  /**
   * Calculates the size of each square cell in the board based on the current size of the
   * panel. This is used to ensure that the board fits nicely within the panel, regardless
   * of its size.
   *
   * @return The size of each square cell to be drawn on the panel.
   */
  private int calculateSquareSize() {
    int totalCells = model.getSize() * 2;
    int horizontalSize = getWidth() / totalCells;
    int verticalSize = getHeight() / totalCells;

    return Math.min(horizontalSize, verticalSize);
  }

  /**
   * Draws a disc in the specified cell. This method will draw a disc with padding inside
   * the cell based on the cell's occupant (black or white player's disc). The disc is
   * drawn as an oval inside the cell's square.
   *
   * @param g2d      The Graphics2D context on which to draw the disc.
   * @param x        The x-coordinate of the top left corner of the cell.
   * @param y        The y-coordinate of the top left corner of the cell.
   * @param cellSize The size of the cell where the disc is to be drawn.
   * @param cell     The cell which determines the color of the disc to be drawn.
   */
  private void drawDisc(Graphics2D g2d, int x, int y, int cellSize, Cell cell) {
    int padding = cellSize / 10; // Add some padding to the disc
    int discSize = cellSize - padding * 2;
    int discX = x + padding;
    int discY = y + padding;

    if (cell == Cell.BLACK) {
      g2d.setColor(Color.BLACK);
    } else if (cell == Cell.WHITE) {
      g2d.setColor(Color.WHITE);
    }

    g2d.fillOval(discX, discY, discSize, discSize); // Draw the disc
  }

  //////////////// Coordinate Conversion ////////////////

  /**
   * Converts pixel coordinates to logical board coordinates.
   *
   * @param x The X pixel coordinate.
   * @param y The Y pixel coordinate.
   * @return The logical position on the board.
   */
  private SquarePosition pixelToLogicalCoords(int x, int y) {
    int cellSize = calculateSquareSize() + 1;
    // Calculate logical coordinates based on the top-left origin
    int q = x / cellSize;
    int r = y / cellSize;
    return new SquarePosition(q, r);
  }

  /**
   * Converts logical coordinates to pixel board coordinates.
   *
   * @param q The Q logical coordinate.
   * @param r The R logical coordinate.
   * @return The X coordinate of the pixel position on the board.
   */
  @Override
  public int logicalToPixelCoordsX(int q, int r) {
    int cellSize = calculateSquareSize() + 1;
    return q * cellSize;
  }

  /**
   * Converts logical coordinates to pixel board coordinates.
   *
   * @param r The R logical coordinate.
   * @return The Y coordinate of the pixel position on the board.
   */
  @Override
  public int logicalToPixelCoordsY(int r) {
    int cellSize = calculateSquareSize() + 1;
    return r * cellSize;
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

  /**
   * Updates the origin point for drawing the hexagonal
   * grid based on the current size of the window.
   */
  private void updateOrigin() {
    origin.x = getWidth() / 2;
    origin.y = getHeight() / 2;
  }
}