package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import controller.ModelStatusListener;

/**
 * This class represents the model for the Reversi game.
 * It maintains the current game state, manages player turns, scores, and board updates.
 */
public class ReversiModel implements IReversiModel {

  //The game board which maintains the state of cells (hexagons) on the board.
  private final Board board;

  //The cell representing the current player's turn, this will be either
  //BLACK  or WHITE
  //INVARIANT: currentTurn should always be BLACK or WHITE.
  //This is crucial for determining whose move it is.
  private Cell currentTurn;

  //Score for white player
  // INVARIANT: scoreWhite and scoreBlack should be non-negative
  // and their sum should be less than or equal to the total
  // number of cells in the board.
  //Essential for determining the winner
  private int scoreWhite;

  //Score for black player
  //Essential for determining the winner
  private int scoreBlack;

  //Board size
  // INVARIANT: size should always be positive.
  //Crucial for establishing the board's dimensions
  private final int size;

  //boolean for if the game has started
  //Essential for restricting operations for when only true
  private boolean hasGameStarted;

  //count to keep track of consecutive pass turns by the players
  //game ends if both players pass consecutively
  private int passTurnCount; //INVARIANT: passTurnCount is greater than zero
  // and cannot be greater than 2

  //holds the ModelStatusListeners that need to be updated when a move is made
  private List<ModelStatusListener> modelStatusListeners = new ArrayList<>();

  /**
   * Constructor for ReversiModel. Initializes the board with a size
   * sets the initial turns and scores
   *
   * @param size The size of the board.
   */
  public ReversiModel(int size) {
    if (size <= 0) { //enforces invariant by having throwing an exception if less than are equal
      throw new IllegalArgumentException("Size should always be positive");
    }

    this.size = size;
    this.board = new Board(size);
    this.currentTurn = Cell.BLACK; //Black is starting, enforcing invariant by setting it to BLACK.
    this.scoreBlack = 0; //score cannot be negative
    this.scoreWhite = 0; //score cannot be negative
    this.hasGameStarted = false;
    this.passTurnCount = 0; //pass turn can only be non-negative and not greater than 2 (game is
    //over if the passTurnCount is 2)
  }

  /**
   * Constructor for ReversiModel. Initializes the board with a size
   * sets the initial turns and scores
   *
   * @param board The board.
   */
  public ReversiModel(Board board) {
    this.board = board;
    this.size = board.getSize();
    this.currentTurn = Cell.BLACK; //Black is starting, enforcing invariant by setting it to BLACK.
    this.scoreBlack = 0; //score cannot be negative
    this.scoreWhite = 0; //score cannot be negative
    this.hasGameStarted = true;
    this.passTurnCount = 0;
  }

  /**
   * Gets the board cells that make up the board.
   *
   * @return the board cells that make up the board as a 2D array.
   */
  public Optional<Cell>[][] getBoard() {
    return board.getBoardCells();
  }

  /**
   * Starts the game by initializing the cells on the board.
   * Throws an exception if the game has not yet started
   */
  @Override
  public void startGame() {
    if (hasGameStarted) {
      throw new IllegalStateException("Cannot start the game more than once");
    }
    initializeCells(); //initializes the starting cells on the board and sets the score
    this.hasGameStarted = true; //other methods recognize that the game has started and can
    //be called
    notifyTurnChanged();
  }

  /**
   * Checks if the game has started, if not, throws an exception.
   *
   * @throws IllegalStateException when the game has not started but a method is called
   */
  public void checkGameStarted() {
    if (!hasGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
  }

  /**
   * Passes the turn to the other player. Manages the passing of the turn.
   * Increments the pass turn counter and switches to the next player.
   */
  @Override
  public void passTurn() {
    if (passTurnCount > 2) { //makes sure that the count is not greater than 2, at 2, gameOver
      throw new IllegalStateException("Pass Turn Count cannot be greater than 2");
    }
    checkGameStarted();
    nextTurn();
    notifyTurnChanged();
    passTurnCount++;
  }

  /**
   * Initializes the board at the start of the game.
   * Sets up the initial configuration of the cells on the
   * board.
   * Adds the initial scores for the game.
   */
  private void initializeCells() {
    // Top-left
    placeHexAt(new Position(0, -1, 1), Cell.BLACK);
    scoreBlack++;
    // Top-right
    placeHexAt(new Position(1, -1, 0), Cell.WHITE);
    scoreWhite++;
    // Bottom-left
    placeHexAt(new Position(-1, 1, 0), Cell.BLACK);
    scoreBlack++;
    // Bottom-right
    placeHexAt(new Position(0, 1, -1), Cell.WHITE);
    scoreWhite++;
    // Left
    placeHexAt(new Position(-1, 0, 1), Cell.WHITE);
    scoreWhite++;
    // Right
    placeHexAt(new Position(1, 0, -1), Cell.BLACK);
    scoreBlack++;
    notifyScoreChanged();
  }

  /**
   * Places a hex at the specified position on the board.
   *
   * @param pos The position on the board to place the hex.
   * @param hex The color/type of the hex to be placed.
   */
  private void placeHexAt(IPosition pos, Cell hex) {
    if (board.getCell(pos).isPresent()) {
      throw new IllegalStateException("Cell is already filled");
    } else {
      this.board.placeHex(pos, hex);
    }
  }

  /**
   * Executes a move by the current player at the specified position.
   * If the move is legal, it places the player's hex at the position,
   * updates scores, flips opponent's hexes, and switches the turn.
   *
   * @param pos The position where the current player intends to make a move.
   * @throws IllegalStateException If the move is invalid or the game hasn't started.
   * @throws IllegalStateException if the game is already over
   */
  @Override
  public void executeMove(IPosition pos) {
    // INVARIANT maintained: Current turn changes, scores updated.
    Cell hex = currentTurn;
    checkGameStarted();
    if (isPositionInvalid(pos)) {
      throw new IllegalArgumentException("Cannot be an invalid position");
    }
    if (isGameOver()) {
      throw new IllegalStateException("Cannot execute a move if game is over");
    }
    if (isALegalMove(pos, hex)) {
      placeHexAt(pos, hex);
      if (hex == Cell.BLACK) {
        scoreBlack++; //Invariant maintained: scoreBlack is non-negative
      } else {
        scoreWhite++; //Invariant maintained: scoreWhite is non-negative
      }
      flipHex(pos, hex);
      nextTurn();
      notifyGameStateChanged();
      notifyTurnChanged();
      notifyScoreChanged();
      passTurnCount = 0; //Invariant maintained: resets the passs turn count to zero
    } else {
      throw new IllegalArgumentException("Invalid move");
    }
  }

  /**
   * Checks if a move to a cell is legal.
   *
   * @param pos position of the proposed move.
   * @param hex players cell that it wants to move.
   * @return if the move is legal, return true, if not, return false.
   */
  public boolean isALegalMove(IPosition pos, Cell hex) {
    if (isPositionInvalid(pos)) {
      return false;
    }
    if (board.getCell(pos).isPresent()) {
      return false;
    }
    for (Position dir : getNeighbors()) {
      Position neighbor = addPositions(pos, dir);
      if (isPositionInvalid(neighbor)) { //for edge cases with less than 6 neighbors
        continue;
      }
      if (isHexOpposite(neighbor, hex) && consecutiveLineCheck(neighbor, dir, hex)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the size of the board.
   * @return the size of the board.
   */
  @Override
  public int getSize() {
    return size;
  }

  /**
   * Checking if the proposed position in on the game board.
   *
   * @param pos proposed position
   * @return true position is not on the game board, otherwise false
   */
  private boolean isPositionInvalid(IPosition pos) {
    return !this.board.isValidPosition(pos);
  }

  /**
   * Flips the state (color) of the cells when a player
   * executes a move and takes them.
   *
   * @param pos position of cell
   * @param hex cell that contains its state, black, white, or empty
   */
  private void flipHex(IPosition pos, Cell hex) {
    for (Position dir : getNeighbors()) {
      Position p = addPositions(pos, dir);
      if (isPositionInvalid(p)) {
        continue;
      }
      List<Position> toFlip = new ArrayList<>();
      while (true) {
        if (isPositionInvalid(p) || !isHexOpposite(p, hex)) {
          break;
        }
        toFlip.add(p);
        p = addPositions(p, dir);

      }
      if (!isPositionInvalid(p)) {
        Optional<Cell> cell = board.getCell(p);
        if (cell.isPresent() && cell.get() == hex) {
          for (Position flipPos : toFlip) {
            board.placeHex(flipPos, hex);
            if (hex == Cell.BLACK) {
              scoreBlack++;
              scoreWhite--;
            } else {
              scoreWhite++;
              scoreBlack--;
            }
          }
        }
      }
    }
  }

  /**
   * Checks if the hex at the given position is not empty and is the opposite of the specified hex.
   *
   * @param pos The position to check.
   * @param hex The hex to compare against.
   * @return True if the hex at the given position is opposite
   *         to the specified hex; false otherwise.
   */
  private boolean isHexOpposite(IPosition pos, Cell hex) {
    return board.getCell(pos).isPresent() && board.getCell(pos).get() != hex;
  }

  /**
   * Determines if there's a consecutive line of opposite
   * hexes starting from the given position in the specified direction
   * that can be terminated by the player's hex.
   *
   * @param strtPos The starting position.
   * @param dir     The direction to check.
   * @param hex     The player's hex to check for termination.
   * @return True if a consecutive line can be terminated by the player's hex; false otherwise.
   */
  private boolean consecutiveLineCheck(IPosition strtPos, IPosition dir, Cell hex) {
    Position p = addPositions(strtPos, dir);
    while (!isPositionInvalid(p) && isHexOpposite(p, hex)) {
      p = addPositions(p, dir);
    }
    return !isPositionInvalid(p) &&
            board.getCell(p).isPresent() &&
            board.getCell(p).get() == hex;
  }

  /**
   * Provides an array of neighboring positions in a hexagonal grid.
   *
   * @return An array of neighboring positions.
   */
  private Position[] getNeighbors() {
    return new Position[]{new Position(1, 0, -1), new Position(-1, 0, 1),
            new Position(0, 1, -1), new Position(0, -1, 1),
            new Position(1, -1, 0), new Position(-1, 1, 0)};
  }

  /**
   * Adds the coordinates of two positions together.
   *
   * @param pos1 The first position.
   * @param pos2 The second position.
   * @return A new position obtained by adding the coordinates of the two input positions.
   */
  private Position addPositions(IPosition pos1, IPosition pos2) {
    return new Position(pos1.getQ() + pos2.getQ(), pos1.getR() + pos2.getR(),
            pos1.getS() + pos2.getS());
  }

  /**
   * Checks if the game has ended.
   *
   * @return true if the game is over, false otherwise.
   */
  @Override
  public boolean isGameOver() {
    checkGameStarted();

    //check if both players pass their turn, if so, the game is over.
    if (passTurnCount >= 2) {
      return true;
    }
    //check if there's a legal move for the current playe
    if (hasLegalMove(currentTurn)) {
      return false;
    }

    Cell nextPlayer = (currentTurn == Cell.BLACK) ? Cell.WHITE : Cell.BLACK;
    return !hasLegalMove(nextPlayer);
  }

  /**
   * Determines if there exists a legal move for the specified hex on the board.
   * A move is considered legal if the hex can be placed on an empty cell
   * and the move is validated as legal by the isALegalMove method.
   *
   * @param hex The hex to check for potential legal moves.
   * @return True if there exists a legal move for the specified hex; false otherwise.
   */
  @Override
  public boolean hasLegalMove(Cell hex) {
    Optional<Cell>[][] boardCells = getBoard();
    for (int q = -size; q <= size; q++) {
      int r1 = Math.max(-size, -q - size);
      int r2 = Math.min(size, -q + size);
      for (int r = r1; r <= r2; r++) {
        int s = -q - r;
        Position pos = new Position(q, r, s);
        if (boardCells[q + size][r + size].isEmpty() && isALegalMove(pos, hex)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns the score of a specific hex color.
   *
   * @param hex the hex color to get the score for.
   * @return the score of the given hex color.
   */
  @Override
  public int getScore(Cell hex) {
    // INVARIANT checked: Scores is positive.
    checkGameStarted();
    int score = 0;
    if (scoreBlack < 0 || scoreWhite < 0) {
      throw new IllegalStateException("Individual scores should be non-negative.");
    }
    if (hex == Cell.BLACK) {
      score = scoreBlack;
    } else if (hex == Cell.WHITE) {
      score = scoreWhite;
    } else {
      throw new IllegalStateException("Hexagon cell must be black or white");
    }
    return score;
  }

  /**
   * Determines and returns the winner of the game.
   *
   * @return the winning hex color, or empty in case of a tie.
   */
  @Override
  public Optional<Cell> getWinner() {
    checkGameStarted();
    if (!isGameOver()) {
      throw new IllegalStateException("Game is not over.");
    }

    int bScore = getScore(Cell.BLACK);
    int wScore = getScore(Cell.WHITE);

    if (bScore > wScore) {
      return Optional.of(Cell.BLACK); //player black wins
    } else if (wScore > bScore) {
      return Optional.of(Cell.WHITE); //player white wins
    } else {
      return Optional.empty(); // tie
    }
  }

  /**
   * Switches the turn to the next player.
   *
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the current turn is not white or black
   */
  private void nextTurn() {
    //INVARIANT checked: current turn can only be black or white cells
    checkGameStarted();
    if (currentTurn == Cell.BLACK) {
      currentTurn = Cell.WHITE;
    } else if (currentTurn == Cell.WHITE) {
      currentTurn = Cell.BLACK;
    } else {
      throw new IllegalStateException("CurrentTurn must be white or black");
    }
  }

  /**
   * Retrieves the hex color at a specific position.
   *
   * @param p the position to get the hex from.
   * @return the hex color at the specified position.
   */
  @Override
  public Optional<Cell> getHexAt(IPosition p) {
    if (isPositionInvalid(p)) {
      throw new IllegalArgumentException("Invalid position");
    }
    return board.getCell(p);
  }

  /**
   * Keeps track of whose turn it currently is.
   *
   * @throws IllegalStateException if the game hasn't started.
   */
  @Override
  public Cell getCurrentTurn() {
    //INVARIANT checked: the current turn and only be BLACK or WHITE
    checkGameStarted();
    return currentTurn;
  }

  /**
   * Counts the number of opponent's pieces that would be captured if a move is made
   * at the given position by the specified player.
   *
   * @param pos  The position to place the hex.
   * @param hex  The player's hex color.
   * @return     The number of captures that would result from the move.
   */
  public int countCaptures(IPosition pos, Cell hex) {
    int captures = 0;
    if (!isALegalMove(pos, hex)) {
      return 0; // No captures possible if move is not legal
    }
    // Check each direction for potential captures
    for (Position dir : getNeighbors()) {
      Position p = addPositions(pos, dir);
      List<Position> potentialFlips = new ArrayList<>();
      while (!isPositionInvalid(p) && isHexOpposite(p, hex)) {
        potentialFlips.add(p);
        p = addPositions(p, dir);
      }
      // Check if the line ends with the player's own hex
      if (!isPositionInvalid(p) && board.getCell(p).isPresent() && board.getCell(p).get() == hex) {
        captures += potentialFlips.size(); // Add all potential flips to captures
      }
    }
    return captures;
  }

  /**
   * Adds a ModelStatusListener to the list of listeners
   * that will be notified about changes in the
   * game model's state.
   *
   * @param listener The listener to be added.
   */
  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    modelStatusListeners.add(listener);
  }

  /**
   * Notifies all registered ModelStatusListeners when the
   * current turn changes in the game.
   * This method should be called whenever there is a change
   * in the player's turn.
   */
  private void notifyTurnChanged() {
    for (ModelStatusListener listener : modelStatusListeners) {
      listener.updateTurnChanged(currentTurn);
    }
  }

  /**
   * Notifies all registered ModelStatusListeners when
   * the game state changes.
   * This could be after a move is made, a pass is done,
   * or any other event that changes
   * the state of the game board.
   */
  private void notifyGameStateChanged() {
    for (ModelStatusListener listener : modelStatusListeners) {
      listener.updateGameBoard();
    }
  }

  /**
   * Notifies all registered ModelStatusListeners
   * when the score changes in the game.
   * This method should be called whenever there is a change
   * in the scores of the players,
   * typically after a move results in capturing opponent's pieces.
   */
  private void notifyScoreChanged() {
    for (ModelStatusListener listener : modelStatusListeners) {
      listener.updateScore(scoreBlack, scoreWhite);
    }
  }

  /**
   * Creates a new position.
   * @param q q coordinate.
   * @param r r coordinate.
   * @return the position.
   */
  @Override
  public IPosition createPosition(int q, int r) {
    int s = -q - r;
    return new Position(q, r, s);
  }

  /**
   * Differentiates size for iterations for square and hexagonal reversis.
   * @return the size for the type of board.
   */
  @Override
  public List<Integer> createSize() {
    List<Integer> size = new ArrayList<>();
    size.add(-getSize());
    size.add(getSize());
    return size;
  }

  @Override
  public List<IPosition> getCorners() {
    return List.of(new Position(-getSize(), getSize(), 0),
            new Position(getSize(), -getSize(), 0),
            new Position(0, -getSize(), getSize()),
            new Position(0, getSize(), -getSize()));
  }

  /**
   * Checks if the given Position is next to a corner.
   *
   * @param corner a corner of the board
   * @param pos the Position to check
   * @return true if the given Position is adjacent to the given corner
   */
  @Override
  public boolean isAdjacentToCorner(IPosition corner, IPosition pos) {
    return Math.abs(corner.getQ() - pos.getQ()) <= 1 &&
            Math.abs(corner.getR() - pos.getR()) <= 1 &&
            Math.abs(corner.getS() - pos.getS()) <= 1;
  }
}