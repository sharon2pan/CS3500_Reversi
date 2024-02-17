package controller;

import java.util.Optional;

import model.Cell;
import model.IPosition;
import model.IReversiModel;

/**
 * Represents a human player in the Reversi game. This class implements the IPlayer interface.
 * The moves for a human player are made through the GUI, hence the play method may not be used.
 */
public class HumanPlayer implements IPlayer {
  private final Cell playerColor;

  /**
   * Constructs a HumanPlayer with the specified cell color.
   *
   * @param playerColor The color of the cell
   *                    (e.g., BLACK or WHITE) this player will use in the game.
   */
  public HumanPlayer(Cell playerColor) {
    this.playerColor = playerColor;
  }

  @Override
  public Optional<IPosition> play(IReversiModel model) {
    // Human player moves are made via the GUI, so this method may not be used.
    return Optional.empty();
  }

  /**
   * Retrieves the cell color assigned to this human player.
   *
   * @return The cell color of this player.
   */
  @Override
  public Cell getCell() {
    return playerColor;
  }

  /**
   * Checks if this player is an AI. Always returns false for a human player.
   *
   * @return False, as this is a human player.
   */
  @Override
  public boolean isAI() {
    return false;
  }
}
