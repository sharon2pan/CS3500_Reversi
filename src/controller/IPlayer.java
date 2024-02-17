package controller;

import java.util.Optional;

import model.Cell;
import model.IPosition;
import model.IReversiModel;

/**
 * The IPlayer interface represents a player in a Reversi game.
 * It provides methods to get the player's move, their cell color,
 * and to check if the player is an AI.
 */
public interface IPlayer {

  /**
   * Requests the player to make a move based on the
   * current state of the game model.
   * For human players, this method may not be used as
   * their moves
   * are made via the GUI.
   * For AI players, this method should return the chosen move.
   *
   * @param model The current state of the Reversi game model.
   * @return An Optional containing the position of the chosen move,
   *         or empty if no move is made.
   */
  Optional<IPosition> play(IReversiModel model);

  /**
   * Retrieves the color of the Player.
   * @return the color assigned to the player
   */
  Cell getCell();

  /**
   * Checks if the player is an AI.
   *
   * @return True if the player is an AI, false otherwise.
   */
  boolean isAI();
}
