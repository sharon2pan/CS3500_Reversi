import java.util.ArrayList;
import java.util.List;

/**
 * Parses the command-line arguments for a Reversi game.
 * This parser interprets player types and their
 * associated strategies.
 */
public class ArgParser implements IArgParsar {
  private String[] args;
  private String player1;
  private String player2;
  private List<String> strategiesPlayer1 = new ArrayList<>();
  private List<String> strategiesPlayer2 = new ArrayList<>();

  /**
   * Constructs an ArgParsar with the provided
   * command-line arguments.
   *
   * @param args The command-line arguments to be parsed.
   */
  public ArgParser(String[] args) {
    this.args = args;
  }

  /**
   * Parses the command-line arguments to extract player
   * types and their strategies.
   * Expects at least two arguments for player types.
   * Additional arguments for strategies are optional.
   *
   * @throws IllegalArgumentException If the provided arguments
   *                                  do not meet the required format.
   */
  public void parseArguments() {
    if (args.length < 2) {
      throw new IllegalArgumentException("Insufficient "
              + "arguments. Required: playerType1 playerType2"
              + " [strategyName1] [strategyName2]");
    }
    int index = 0;
    player1 = args[index];
    if (!isValidPlayerType(player1)) {
      throw new IllegalArgumentException("Invalid player type: " + player1);
    }
    index++;
    // Parse strategies for player1
    while (index < args.length && !isValidPlayerType(args[index])) {
      strategiesPlayer1.add(args[index++]);
    }
    if (index >= args.length) {
      throw new IllegalArgumentException("Second player type is missing.");
    }
    // Parse player2 and their strategies
    player2 = args[index++];
    if (!isValidPlayerType(player2)) {
      throw new IllegalArgumentException("Invalid player type: " + player2);
    }
    while (index < args.length) {
      strategiesPlayer2.add(args[index++]);
    }
  }

  /**
   * Validates if the provided string is a valid player type.
   *
   * @param playerType The player type to validate.
   * @return True if the player type is valid, false otherwise.
   */
  private boolean isValidPlayerType(String playerType) {
    return playerType.equalsIgnoreCase("human") || playerType.equalsIgnoreCase("AI");
  }

  /**
   * Retrieves the type of the first player.
   *
   * @return The type of the first player.
   */
  public String getPlayer1() {
    return player1;
  }

  /**
   * Retrieves the type of the second player.
   *
   * @return The type of the second player.
   */
  public String getPlayer2() {
    return player2;
  }

  /**
   * Retrieves the list of strategies for the first player.
   *
   * @return The list of strategies for the first player.
   */
  public List<String> getStrategiesPlayer1() {
    return strategiesPlayer1;
  }

  /**
   * Retrieves the list of strategies for the second player.
   *
   * @return The list of strategies for the second player.
   */
  public List<String> getStrategiesPlayer2() {
    return strategiesPlayer2;
  }
}