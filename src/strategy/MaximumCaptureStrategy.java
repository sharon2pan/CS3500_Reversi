package strategy;

import java.util.ArrayList;
import java.util.List;

import model.Cell;
import model.IPosition;
import model.Position;
import model.ReadOnlyReversiModel;

/**
 * A strategy for the Reversi game that aims to maximize
 * the number of opponent pieces captured with each move.
 * This strategy evaluates all legal moves and selects the
 * one that results in the highest number of captures.
 */
public class MaximumCaptureStrategy extends BaseReversiStrategy {

  /**
   * Chooses positions to play for a given player in the
   * Reversi game that maximizes the number of opponent
   * pieces captured.
   * If multiple positions result in the same maximum
   * number of captures, it chooses the uppermost-leftmost
   * position as a tiebreaker.
   * If no legal moves are available, it passes the turn.
   *
   * @param model  The current state of the Reversi game.
   * @param player The player (Cell.BLACK or Cell.WHITE) for
   *              whom to choose the position.
   * @return A list containing the chosen positions that
   *         maximizes captures, or empty if no suitable move is found.
   */
  @Override
  public List<IPosition> choosePositions(ReadOnlyReversiModel model, Cell player) {
    List<IPosition> legalMoves = getLegalMoves(model, player);
    if (legalMoves.isEmpty()) {
      return new ArrayList<>(); // No legal moves available
    }

    int maxCaptures = 0;
    List<IPosition> maxCapturePositions = new ArrayList<>();

    for (IPosition pos : legalMoves) {
      int captures = model.countCaptures(pos, player);
      if (captures > maxCaptures) {
        maxCaptures = captures;
        maxCapturePositions.clear();
        maxCapturePositions.add(pos);
      } else if (captures == maxCaptures) {
        maxCapturePositions.add(pos);
      }
    }

    return maxCapturePositions;
  }
}