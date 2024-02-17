package strategy;

import java.util.List;

import model.Cell;
import model.IPosition;
import model.Position;
import model.ReadOnlyReversiModel;

/**
 * A composite Reversi strategy that attempts to apply
 * two different strategies sequentially.
 * It first tries the primary strategy, and if that does
 * not result in a valid move, it then tries the secondary
 * strategy.
 */
public class TryTwo extends BaseReversiStrategy implements IReversiStrategy {
  private final IReversiStrategy first; //first strategy
  private final IReversiStrategy second; //second strategy

  /**
   * Constructs a TryTwo strategy with two provided Reversi strategies.
   *
   * @param first  The primary strategy to be attempted first.
   * @param second The secondary strategy to be attempted
   *               if the primary strategy does not find a valid move.
   */
  public TryTwo(IReversiStrategy first, IReversiStrategy second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Chooses positions for the next move in a game of
   * Reversi by trying two different strategies sequentially.
   * This method first attempts to find a valid move
   * using the primary strategy. If the primary strategy yields
   * one or more valid moves, these moves are returned.
   * If the primary strategy does not yield any valid moves,
   * the secondary strategy is then applied to find valid moves.
   * This approach allows for a flexible strategy
   * selection, where the secondary strategy acts as a fallback
   * in case the primary strategy does not provide
   * a satisfactory outcome.
   *
   * @param model  The current state of the Reversi
   *               game model, providing information about
   *               the board
   *               and the state of the game.
   * @param player The cell representing the current player, used to determine legal moves
   *               according to each strategy.
   * @return A list of position objects representing the
   *         chosen positions for the next move.
   *         This list is derived either from the primary strategy,
   *         if it yields valid moves, or from
   *         the secondary strategy otherwise. Returns an
   *         empty list if neither strategy yields a valid move.
   */
  @Override
  public List<IPosition> choosePositions(ReadOnlyReversiModel model, Cell player) {
    List<IPosition> firstChoice = first.choosePositions(model, player);
    if (!firstChoice.isEmpty()) {
      return firstChoice;
    }
    return second.choosePositions(model, player);
  }
}