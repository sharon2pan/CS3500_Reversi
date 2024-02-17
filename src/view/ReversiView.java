package view;

import java.util.Optional;

import model.Cell;
import model.IReversiModel;

/**
 * Represents a textual view for the Reversi game.
 * This class is responsible for rendering
 * the game board to the console in a textual format.
 */
public class ReversiView implements IView {
  private final IReversiModel model;

  /**
   * Constructs a new ReversiView given a game model.
   *
   * @param model The model of the Reversi game that this
   *              view will render.
   */
  public ReversiView(IReversiModel model) {
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

    int size = (boardCells.length - 1) / 2;

    for (int i = 0; i < boardCells.length; i++) {
      int padding = Math.abs(size - i);
      for (int pad = 0; pad < padding; pad++) {
        boardView.append(" ");
      }

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

  //FOR THE TA: HOW TO PRODUCE THE VISUALIZATION
  //1.) Could write a test or look at our tests with the toString method
  //to see the correct visualization
  //2.) Look at our model tests that use the void renderTextualView that prints
  //the visualization to the consol and its updates as the game is played
}




