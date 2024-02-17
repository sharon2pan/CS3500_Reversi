package view;

/**
 * Represents an interface for the view component of the application.
 * This view provides methods for rendering a textual representation
 * of the game or application state.
 */
public interface IView {

  /**
   * Renders a textual representation of the game or application state.
   * Implementations should display the current state to the user in an appropriate textual format.
   */
  void renderTextualView();

  /**
   * Renders a textual representation of the game or application state.
   * Implementations should display the current state in the form of a string.
   * @throws IllegalStateException if the board cells sare not initialized
   */
  String toString();
}
