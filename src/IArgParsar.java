import java.util.List;

/**
 * Interface for parsing arguments.
 */
public interface IArgParsar {

  /**
   * Parses the command-line arguments to extract player
   * types and their strategies.
   * Expects at least two arguments for player types.
   * Additional arguments for strategies are optional.
   *
   * @throws IllegalArgumentException If the provided arguments
   *                                  do not meet the required format.
   */
  void parseArguments();

  /**
   * Retrieves the type of the first player.
   *
   * @return The type of the first player.
   */
  String getPlayer1();

  /**
   * Retrieves the type of the second player.
   *
   * @return The type of the second player.
   */
  String getPlayer2();

  /**
   * Retrieves the list of strategies for the first player.
   *
   * @return The list of strategies for the first player.
   */
  List<String> getStrategiesPlayer1();

  /**
   * Retrieves the list of strategies for the second player.
   *
   * @return The list of strategies for the second player.
   */
  List<String> getStrategiesPlayer2();


}
