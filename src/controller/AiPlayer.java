package controller;

import java.util.List;
import java.util.Optional;

import model.Cell;
import model.IPosition;
import model.IReversiModel;
import strategy.IReversiStrategy;

/**
 * Represents an AI (Artificial Intelligence) player in the Reversi game.
 * This class implements the IPlayer interface and makes
 * decisions based on the provided strategy.
 */
public class AiPlayer implements IPlayer {
  private final Cell player;
  private final IReversiStrategy strategy;

  /**
   * Constructs an AiPlayer with a specified cell color and strategy.
   *
   * @param player   The color of the cell
   *                (e.g., BLACK or WHITE) this AI player will use in the game.
   * @param strategy The strategy that this AI player will use to make decisions.
   */
  public AiPlayer(Cell player, IReversiStrategy strategy) {
    this.player = player;
    this.strategy = strategy;
  }

  /**
   * Plays a move based on the AI strategy. This method evaluates the possible moves and
   * selects the best move according to the strategy defined.
   *
   * @param model The current state of the Reversi game model.
   * @return An Optional containing the chosen Position for the next move,
   *         if a move is available.
   */
  @Override
  public Optional<IPosition> play(IReversiModel model) {
    List<IPosition> possibleMoves = strategy.choosePositions(model, player);
    return strategy.chooseBestPosition(possibleMoves);
  }

  /**
   * Retrieves the cell color assigned to this AI player.
   *
   * @return The cell color of this AI player.
   */
  @Override
  public Cell getCell() {
    return player;
  }

  /**
   * Checks if this player is an AI.
   * Always returns true for an AI player.
   *
   * @return True, as this is an AI player.
   */
  @Override
  public boolean isAI() {
    return true;
  }
}

