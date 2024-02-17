package view;

import java.util.Optional;

import model.Cell;
import model.IReversiModel;

/**
 * Represents a textual view for the SquareReversi game.
 * This class is responsible for rendering
 * the game board to the console in a textual format.
 */
public class SquareReversiView implements IView {
  private final IReversiModel model;

  /**
   * Constructs a new SquareReversiView given a game model.
   *
   * @param model The model of the SquareReversi game that this
   *              view will render.
   */
  public SquareReversiView(IReversiModel model) {
    this.model = model;
  }

  /**
   * Renders a textual representation of the game board
   * to the console.
   * The board is displayed using the following notations:
   * - `_` for empty cells.
   * - `O` for cells occupied by the white player.
   * - `X` for cells occupied by the black player.
   */
  public void renderTextualView() {
    System.out.print(toString());
  }

  /**
   * Renders a textual representation of the game board
   * in the form of a string.
   * The board is displayed using the following notations:
   * - `_` for empty cells.
   * - `O` for cells occupied by the white player.
   * - `X` for cells occupied by the black player.
   *
   * @throws IllegalStateException if the board cells sare not initialized
   */
  public String toString() {
    StringBuilder boardView = new StringBuilder();
    Optional<Cell>[][] boardCells = model.getBoard();
    if (boardCells == null) {
      throw new IllegalStateException("Board cells have not been initialized.");
    }
    for (int i = 0; i < boardCells.length; i++) {
      for (int j = 0; j < boardCells[i].length; j++) {
        if (j < boardCells.length && i < boardCells[j].length) { // Check bounds
          Optional<Cell> cell = boardCells[j][i];
          if (cell == null) {
            boardView.append(""); // Handle for null cells
          } else if (cell.isEmpty()) {
            boardView.append("_ "); //for the empty cells
          } else if (cell.get() == Cell.WHITE) {
            boardView.append("O ");  //O's are for the white cells
          } else if (cell.get() == Cell.BLACK) {
            boardView.append("X "); //X's are for the black cells
          }
        }
      }
      boardView.append("\n");
    }
    return boardView.toString();
  }
}
