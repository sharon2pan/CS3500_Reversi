package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Cell;
import model.IPosition;
import model.Position;
import model.ReadOnlyReversiModel;

/**
 * An abstract base class for strategies used in the Reversi game.
 * This class provides common utility methods that can be used by various Reversi strategies.
 */
public abstract class BaseReversiStrategy implements IReversiStrategy {

  /**
   * Retrieves a list of legal moves for a given player
   * based on the current state of the Reversi game model.
   * This method iterates over the entire game board and
   * adds positions to the list if they are legal moves
   * for the specified player.
   *
   * @param model  The current state of the Reversi game.
   * @param player The player (Cell.BLACK or Cell.WHITE)
   *               for whom to find legal moves.
   * @return A list of legal positions for the specified player.
   */
  protected List<IPosition> getLegalMoves(ReadOnlyReversiModel model, Cell player) {
    List<IPosition> legalMoves = new ArrayList<>();
    List<Integer> size = model.createSize();
    for (int q = size.get(0); q <= size.get(1); q++) {
      for (int r = size.get(0); r <= size.get(1); r++) {
        // int s = -q - r;
        //Position pos = new Position(q, r, s);
        IPosition pos = model.createPosition(q, r);
        if (model.isALegalMove(pos, player)) {
          legalMoves.add(pos);
        }
      }
    }
    System.out.println(legalMoves);
    return legalMoves;
  }

  /**
   * Selects the best position from a list of positions based
   * on a specific criterion.
   * The method defines the 'best' position as the
   * uppermost-leftmost position in the list.
   * If the list is empty or null, it returns an empty Optional.
   * The method first checks if the provided list is null
   * or empty. If it is, it returns an empty Optional.
   * If there are positions in the list, the method iterates
   * through them and selects the position that is
   * highest on the board (smallest 'r' value). If there is
   * a tie for the highest position, it then selects
   * the position that is furthest to the left (smallest
   * 'q' value).
   *
   * @param positions The list of positions to choose from.
   * @return An Optional containing the best position
   *         according to the criteria, or an empty Optional
   *         if the list is null or empty.
   */
  @Override
  public Optional<IPosition> chooseBestPosition(List<IPosition> positions) {
    if (positions == null || positions.isEmpty()) {
      return Optional.empty();
    }

    IPosition bestPosition = positions.get(0);
    for (IPosition pos : positions) {
      if (pos.getR() < bestPosition.getR() ||
              (pos.getR() == bestPosition.getR() && pos.getQ() < bestPosition.getQ())) {
        bestPosition = pos;
      }
    }

    return Optional.of(bestPosition);
  }
}