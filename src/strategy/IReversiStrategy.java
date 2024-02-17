package strategy;

import java.util.List;
import java.util.Optional;

import model.Cell;
import model.IPosition;
import model.Position;
import model.ReadOnlyReversiModel;

/**
 * Represents a strategy interface for deciding the next
 * move in a game of Reversi.
 * Implementations of this interface define various strategies
 * for choosing positions on the Reversi board.
 * A strategy could be based on different tactics like
 * maximizing captures, controlling corners, or responding
 * to the opponent's moves.
 */
public interface IReversiStrategy {
  /**
   * Chooses positions for the next move in a game of
   * Reversi based on the implemented strategy.
   * Implementations of this method should consider the
   * current state of the game and the strategy's
   * approach (e.g., maximizing captures, controlling
   * corners, responding to opponent's moves) to
   * determine the best possible moves.
   *
   * @param model  The current state of the Reversi game
   *               model, providing information about the board
   *               and the state of the game.
   * @param player The cell representing the current player,
   *              used to determine legal and strategic moves.
   * @return A list of positions objects representing the
   *         chosen positions for the next move. The list
   *         may be empty if there are no legal or strategic
   *         moves available.
   */
  List<IPosition> choosePositions(ReadOnlyReversiModel model, Cell player);

  /**
   * Selects the best position from a list of potential
   * positions for the next move.
   * This method is typically used to refine the
   * choice of positions obtained from the method.
   * The criteria for selecting the best position are defined
   * by the specific strategy implementation.
   *
   * @param positions A list of position objects representing
   *                  potential positions for the next move.
   * @return An optional containing the best position from the provided list.
   *         If no suitable position is found, or the list
   *         is empty, returns an empty.
   */
  Optional<IPosition> chooseBestPosition(List<IPosition> positions);
}