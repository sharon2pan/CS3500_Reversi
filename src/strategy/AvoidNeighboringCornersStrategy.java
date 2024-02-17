package strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import model.Cell;
import model.IPosition;
import model.Position;
import model.ReadOnlyReversiModel;

/**
 * This strategy for the Reversi game aims to avoid
 * making moves that are adjacent to the corners of the board.
 * The rationale behind this strategy is that placing
 * a piece adjacent to a corner can give the opponent a
 * strategic advantage.
 */
public class AvoidNeighboringCornersStrategy extends BaseReversiStrategy {

  /**
   * Chooses the positions to play for a given player in the Reversi game.
   * The method prioritizes moves that are not adjacent to any of the corners of the board.
   * If there are no legal moves available, or if all legal
   * moves are adjacent to corners, the method will pass the turn.
   *
   * @param model  The current state of the Reversi game.
   * @param player The player (Cell.BLACK or Cell.WHITE) for whom to choose the position.
   * @return A list  containing the chosen positions, or empty list if no suitable move is found.
   */
  @Override
  public List<IPosition> choosePositions(ReadOnlyReversiModel model, Cell player) {
    List<IPosition> legalMoves = getLegalMoves(model, player);
    System.out.println(legalMoves);
    if (legalMoves.isEmpty()) {
      //model.passTurn();
      return new ArrayList<>();
    }

    return legalMoves.stream()
            .filter(pos -> !isAdjacentToCorner(pos, model))
            .collect(Collectors.toList());
  }

  /**
   * Checks whether a given position on the board is adjacent to any of the corners.
   *
   * @param pos       The position to check.
   * @param model     The current Reversi game
   * @return True if the position is adjacent to any corner, false otherwise.
   */
  private boolean isAdjacentToCorner(IPosition pos, ReadOnlyReversiModel model) {
    List<IPosition> corners = model.getCorners();

    // Check if the position is next to any corner
    return corners.stream().anyMatch(corner -> model.isAdjacentToCorner(corner, pos));
  }
}