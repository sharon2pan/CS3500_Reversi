import java.util.ArrayList;
import java.util.List;

import controller.AiPlayer;
import controller.HumanPlayer;
import controller.IPlayer;
import model.Cell;
import strategy.AvoidNeighboringCornersStrategy;
import strategy.GoForCornersStrategy;
import strategy.IReversiStrategy;
import strategy.MaximumCaptureStrategy;
import strategy.TryTwo;

/**
 * This class provides static methods for creating
 * players in a Reversi game.
 * It supports the creation of both human and AI players,
 * with specified strategies for AI players.
 */
public class ReversiCreator {

  /**
   * Creates a player for the Reversi game based on the specified type and strategy.
   *
   * @param playerType   The type of player, either "human" or "AI".
   * @param playerColor  The color assigned to the player (e.g., BLACK or WHITE).
   * @param strategies   A list of strategy names for AI players.
   *                     Ignored for human players.
   * @return An IPlayer instance representing the created player.
   * @throws IllegalArgumentException If an unknown player type or an
   *                                  invalid strategy configuration is provided.
   */
  public static IPlayer createPlayer(String playerType, Cell playerColor, List<String> strategies) {
    if (playerType.equalsIgnoreCase("human")) {
      return new HumanPlayer(playerColor);
    } else if (playerType.equalsIgnoreCase("AI")) {
      if (strategies.isEmpty()) {
        throw new IllegalArgumentException("No strategy provided for AI player.");
      }
      IReversiStrategy strategy = createStrategy(strategies);
      return new AiPlayer(playerColor, strategy);
    } else {
      throw new IllegalArgumentException("Unknown player type: " + playerType);
    }
  }

  /**
   * Returns an IReversiStrategy instance based on the given strategy name.
   *
   * @param strategyName The name of the strategy.
   * @return An instance of the specified strategy.
   * @throws IllegalArgumentException If the strategy name is unknown.
   */
  private static IReversiStrategy getStrategyByName(String strategyName) {
    switch (strategyName) {
      case "ChooseCorners":
        return new GoForCornersStrategy();
      case "MaxCapture":
        return new MaximumCaptureStrategy();
      case "AvoidNextToCorners":
        return new AvoidNeighboringCornersStrategy();
      default:
        throw new IllegalArgumentException("Unknown strategy name: " + strategyName);
    }
  }

  /**
   * Creates a strategy for AI players based on a list of strategy names.
   *
   * @param strategies A list of strategy names.
   *                   Can include a special "TryTwo" strategy.
   * @return An IReversiStrategy instance representing the combined strategy.
   * @throws IllegalArgumentException If the strategy configuration is invalid.
   */
  private static IReversiStrategy createStrategy(List<String> strategies) {
    if (strategies.isEmpty()) {
      throw new IllegalArgumentException("Strategy list cannot be empty.");
    }

    String firstStrategy = strategies.get(0);

    if (firstStrategy.equalsIgnoreCase("TryTwo")) {
      if (strategies.size() < 3) { // Minimum required for a valid TryTwo configuration
        throw new IllegalArgumentException("TryTwo strategy requires at least two strategies.");
      }
      return processTryTwoStrategy(new ArrayList<>(strategies.subList(1, strategies.size())));
    } else {
      return getStrategyByName(firstStrategy);
    }
  }

  /**
   * Processes and creates a TryTwo strategy using a list of remaining strategies.
   *
   * @param remainingStrategies A list containing the remaining strategies for processing.
   * @return An IReversiStrategy representing the TryTwo strategy.
   * @throws IllegalArgumentException If the remaining strategies list is invalid for TryTwo.
   */
  private static IReversiStrategy processTryTwoStrategy(List<String> remainingStrategies) {
    if (remainingStrategies.isEmpty()) {
      throw new IllegalArgumentException("No strategies left to process for TryTwo.");
    }
    if (!remainingStrategies.get(0).equalsIgnoreCase("TryTwo")
            && !remainingStrategies.get(1).equalsIgnoreCase("TryTwo")) {
      return new TryTwo(getStrategyByName(remainingStrategies.get(0)),
              getStrategyByName(remainingStrategies.get(1)));
    }

    List<String> strat1List = new ArrayList<>();
    List<String> strat2List = new ArrayList<>();

    // Split the remaining strategies into two lists for each TryTwo strategy
    splitStrategyList(remainingStrategies, strat1List, strat2List);

    IReversiStrategy strat1 = createStrategy(strat1List);
    IReversiStrategy strat2 = createStrategy(strat2List);

    return new TryTwo(strat1, strat2);
  }

  /**
   * Splits the list of strategies into two separate lists, each for a nested TryTwo strategy.
   *
   * @param remainingStrategies The list of strategies to be split.
   * @param strat1List          The list to hold the strategies for the first TryTwo.
   * @param strat2List          The list to hold the strategies for the second TryTwo.
   * @throws IllegalArgumentException If splitting results in invalid TryTwo configurations.
   */
  private static void splitStrategyList(List<String> remainingStrategies, List<String> strat1List,
                                        List<String> strat2List) {
    boolean isFirstStrategy = true;

    for (String strategy : remainingStrategies) {
      if (strategy.equalsIgnoreCase("TryTwo")) {
        // Switch to the second strategy list when encountering another TryTwo
        isFirstStrategy = false;
        continue;
      }
      if (isFirstStrategy) {
        strat1List.add(strategy);
      } else {
        strat2List.add(strategy);
      }
    }
    if (strat1List.isEmpty() || strat2List.isEmpty()) {
      throw new IllegalArgumentException("Invalid strategy configuration for TryTwo.");
    }
  }



}
