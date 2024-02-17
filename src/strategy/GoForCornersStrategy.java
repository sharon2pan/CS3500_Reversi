package strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Cell;
import model.IPosition;
import model.Position;
import model.ReadOnlyReversiModel;

/**
 * A strategy for the Reversi game that prioritizes
 * occupying corner positions on the board.
 * Corners are valuable in Reversi as they cannot be
 * flipped by the opponent, offering a strategic advantage.
 */
public class GoForCornersStrategy extends BaseReversiStrategy {

  /**
   * Chooses positions for a move in the Reversi game, focusing on occupying corner positions.
   * This method evaluates the available legal moves and
   * prioritizes those that allow the player
   * to occupy corner positions on the board. Corners are a
   * strategic asset in Reversi as once
   * occupied, they cannot be flipped by the opponent.
   * The method first retrieves all legal moves for the current
   * player. If there are no legal moves,
   * it passes the turn and returns an empty list. It then
   * filters these legal moves to find those
   * that are corner positions, returning a list of these
   * corner positions if any are available.
   *
   * @param model  The current state of the Reversi game
   *               model, providing information about the board
   *               and the state of the game.
   * @param player The cell representing the current player, used to determine legal moves.
   * @return A list of positions representing the prioritized
   *         corner positions available
   *         for the player to make a move. If no corner moves
   *         are available, this list will be empty.
   *         If no moves are legal, the turn is passed and an
   *         empty list is returned.
   */
  @Override
  public List<IPosition> choosePositions(ReadOnlyReversiModel model, Cell player) {
    List<IPosition> legalMoves = getLegalMoves(model, player);
    if (legalMoves.isEmpty()) {
      return new ArrayList<>();
    }

    List<IPosition> cornersAvailable = new ArrayList<>();
    List<IPosition> corners = model.getCorners();

    // Check for legal corner moves
    for (IPosition corner : corners) {
      if (model.isALegalMove(corner, player)) {
        cornersAvailable.add(corner);
      }
    }
    return cornersAvailable;
  }
}