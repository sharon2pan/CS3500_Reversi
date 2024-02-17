package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;

import model.Cell;
import model.IPosition;


/**
 * The {@code HintDecorator} class represents the "hint" mode
 * that the user can enable. Using this decorator, the selected cell
 * shows how many discs would be flipped if that player chose that move.
 */
public class HintDecorator extends PanelDecorator {
  /**
   * Constructs a {@code HintDecorator} with the specified IReversiPanelView.
   * The HintDecorator is layered around the IReversiPanelView as a feature
   * that can be enabled on or off at runtime.
   *
   * @param panelView the view that this HintDecorator visually wraps around
   */
  public HintDecorator(IReversiPanelView panelView) {
    super(panelView);
  }

  /**
   * Paints the features that change when the HintDecorator is used,
   * specifically text signifying how many discs would be flipped if
   * the player chose the highlighted cell. The method is called
   * automatically by Swing when the component needs to be redrawn.
   *
   * @param g The {@code Graphics} context used for painting.
   */
  @Override
  public void paintComponent(Graphics g) {
    if (getHighlightedCell().isPresent()) {
      IPosition pos = getHighlightedCell().get();
      Cell currentPlayer = getModel().getCurrentTurn();
      int captures = getModel().countCaptures(pos, currentPlayer);

      drawCaptureHint(g, pos, captures);
    }
  }

  /**
   * Retrieve the IReversiPanelView that is used to create
   * this HintDecorator.
   *
   * @return the base view that is to be layered with this HintDecorator
   */
  public IReversiPanelView getDecoratedPanel() {
    return this.panelView;
  }

  /**
   * Draws the text signifying how many discs would be flipped if
   * the player chose the highlighted cell.
   *
   * @param g The {@code Graphics} context used for painting.
   * @param pos The Position to check
   * @param captures The number of discs flipped at the given Position
   */
  private void drawCaptureHint(Graphics g, IPosition pos, int captures) {
    Graphics2D g2d = (Graphics2D) g;
    int x = logicalToPixelCoordsX(pos.getQ(), pos.getR());
    int y = logicalToPixelCoordsY(pos.getR());
    String captureText = String.valueOf(captures);

    // Set font, could be adjusted to your preference
    g2d.setFont(new Font("Arial", Font.BOLD, 14));
    FontMetrics fm = g2d.getFontMetrics();
    int textWidth = fm.stringWidth(captureText);

    // Draw the string in the center of the hex
    g2d.setColor(Color.BLACK); // Text color
    g2d.drawString(captureText, x - (textWidth / 2), y + (fm.getAscent() / 2) - 2);
  }
}