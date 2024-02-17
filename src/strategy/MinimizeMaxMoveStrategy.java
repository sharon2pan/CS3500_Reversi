package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Board;
import model.Cell;
import model.IPosition;
import model.IReversiModel;
import model.Position;
import model.ReadOnlyReversiModel;
import model.ReversiModel;

/**
 * Implements a Reversi game strategy that aims
 * to minimize the maximum possible advantage of the opponent.
 * This strategy evaluates potential moves by
 * simulating them and predicting the resulting
 * advantage for the opponent,
 * selecting those moves that minimize this advantage.
 * The strategy uses a combination of other strategies such as
 * prioritizing corner positions, avoiding neighboring
 * corners, and maximizing captures, assigning different
 * priority scores to each.
 */
public class MinimizeMaxMoveStrategy extends BaseReversiStrategy {

  /**
   * Chooses positions for the next move by evaluating
   * and minimizing the opponent's potential advantage.
   * This method simulates each legal move and uses the
   * method to evaluate
   * the opponent's advantage. The moves that result
   * in the least advantage for the opponent are selected.
   *
   * @param model  The current state of the Reversi
   *              game model, used to determine the current
   *              board state and legal moves.
   * @param player The cell representing the current player.
   * @return A list of positions objects representing the
   *         best moves that minimize the opponent's advantage.
   *        Returns an empty list if no moves are available.
   */
  @Override
  public List<IPosition> choosePositions(ReadOnlyReversiModel model, Cell player) {
    int minOpponentAdvantage = Integer.MAX_VALUE;
    List<IPosition> bestMoves = new ArrayList<>();

    List<IPosition> legalMoves = getLegalMoves(model, player);
    for (IPosition move : legalMoves) {
      // Simulate the move and evaluate opponent's advantage
      int opponentAdvantage = simulateAndPredictAdvantage(model, move, player);
      if (opponentAdvantage < minOpponentAdvantage) {
        minOpponentAdvantage = opponentAdvantage;
        bestMoves.clear();
        bestMoves.add(move);
      } else if (opponentAdvantage == minOpponentAdvantage) {
        bestMoves.add(move);
      }
    }

    return bestMoves;
  }

  /**
   * Simulates a move and predicts the resulting advantage
   * for the opponent.
   * This method creates a deep copy of the board, executes
   * the given move on the copy, and then evaluates
   * the opponent's advantage using various strategies.
   *
   * @param model  The current Reversi game model to be used for simulation.
   * @param move   The move representing the move to be simulated.
   * @param player The cell representing the current player.
   * @return An integer representing the predicted advantage
   *         for the opponent after the move.
   */
  private int simulateAndPredictAdvantage(ReadOnlyReversiModel model, IPosition move, Cell player) {
    // Create a deep copy of the current board and simulate the move
    Optional<Cell>[][] boardCopy = deepCopyBoard(model.getBoard());
    Board newBoard = new Board(boardCopy, model.getSize());
    IReversiModel simulatedModel = new ReversiModel(newBoard);

    Cell opponent = (player == Cell.BLACK) ? Cell.WHITE : Cell.BLACK;
    simulatedModel.executeMove(move);

    return predictOpponentAdvantage(simulatedModel, opponent);
  }

  /**
   * Creates a deep copy of the original board.
   * This method is used to simulate moves without altering the state of the actual game board.
   *
   * @param originalBoard The original board as a 2D array of {@link Optional} {@link Cell} objects.
   * @return A new 2D array of cell objects, which is a
   *         deep copy of the original board.
   */
  private Optional<Cell>[][] deepCopyBoard(Optional<Cell>[][] originalBoard) {
    Optional<Cell>[][] copy = new Optional[originalBoard.length][originalBoard[0].length];
    for (int i = 0; i < originalBoard.length; i++) {
      System.arraycopy(originalBoard[i], 0, copy[i], 0, originalBoard[i].length);
    }
    return copy;
  }

  /**
   * Predicts the opponent's advantage based on various strategies.
   * This method evaluates the advantage based on corner
   * positions, neighboring corners, and capture moves.
   * It assigns different scores to these strategies and
   * sums them up to get a total advantage score.
   *
   * @param model    The Reversi game model used for evaluating
   *                the opponent's advantage.
   * @param opponent The cell representing the opponent.
   * @return An integer representing the total
   *         advantage score for the opponent.
   */
  private int predictOpponentAdvantage(ReadOnlyReversiModel model, Cell opponent) {
    int countAdvantage = 0;

    // Go for Corners Strategy - Highest Priority
    List<IPosition> cornerMoves =
            new GoForCornersStrategy().choosePositions(model, opponent);
    if (!cornerMoves.isEmpty()) {
      countAdvantage += 3; // Highest advantage score for corner move
    }

    // Avoid Corners Strategy - Next Priority
    List<IPosition> avoidCornerMoves =
            new AvoidNeighboringCornersStrategy().choosePositions(model, opponent);
    if (!avoidCornerMoves.isEmpty()) {
      countAdvantage += 2; // Second highest advantage score for avoiding corners
    }

    // Maximum Capture Strategy - Lowest Priority
    List<IPosition> captureMoves =
            new MaximumCaptureStrategy().choosePositions(model, opponent);
    if (!captureMoves.isEmpty()) {
      // If there are capture moves that result in capturing more than 1 piece
      for (IPosition pos : captureMoves) {
        if (model.countCaptures(pos, opponent) > 1) {
          countAdvantage += 1;
          break; // Add advantage once for any capture move that captures more than 1 piece
        }
      }
    }

    return countAdvantage;
  }
}