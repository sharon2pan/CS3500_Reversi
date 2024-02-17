package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

/**
 * Tests the methods in the Board class for correct
 * behavior.
 */
public class BoardTests {
  private Board board;
  PrintStream outMessage;
  ByteArrayOutputStream outputStreamCaptor;

  @Before
  public void setUp() {
    board = new Board(3);
    outMessage = System.out;
    outputStreamCaptor = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @Test
  public void testGetCell() {
    //make for loop to test if all cells are empty
    Position pos = new Position(-1,1,0);
    Assert.assertEquals(Optional.empty(), board.getCell(pos));
    board.placeHex(pos, Cell.BLACK);
    Assert.assertTrue(board.getCell(pos).isPresent());
    Assert.assertEquals(Cell.BLACK, board.getCell(pos).get());
  }

  @Test
  public void testIsValidPosition() {
    //testing for a valid position
    Position validPos = new Position(-1,1,0);
    Assert.assertTrue(board.isValidPosition(validPos));
    Position invalidPos1 = new Position(-60,60,0);
    Assert.assertFalse(board.isValidPosition(invalidPos1));
    Position invalidPos2 = new Position(60,60,30);
    Assert.assertFalse(board.isValidPosition(invalidPos2));
    Position invalidPos3 = new Position(-60,-60,-30);
    Assert.assertFalse(board.isValidPosition(invalidPos3));
  }

  @Test
  public void testGetBoardCells() {
    //testing the length of th board is correct using the getBoard cells
    Assert.assertEquals(7, board.getBoardCells().length);
    Board b = new Board(5);
    Assert.assertEquals(11, b.getBoardCells().length);
  }

  //testng board initialization
  @Test
  public void testBoardInitialization() {
    IReversiModel model = new ReversiModel(5);
    int size = (model.getBoard().length - 1) / 2;
    Optional<Cell>[][] boardCells = model.getBoard();

    for (int i = 0; i < boardCells.length; i++) {
      for (int j = 0; j < boardCells[i].length; j++) {
        // Only testing cells that are part of the hexagonal board
        if (boardCells[i][j] != null) {
          Assert.assertEquals(Optional.empty(), boardCells[i][j]);
        }
      }
    }
  }

  //testing for correct place hex method
  @Test
  public void testPlaceHex() {
    Position pos = new Position(0, 0, 0);
    board.placeHex(pos, Cell.BLACK);
    Assert.assertEquals(Cell.BLACK, board.getCell(pos).get());
  }


  //testing correct board size
  @Test
  public void testBoardSize() {
    Assert.assertEquals(7, board.getBoardCells().length); // 3*2+1=7, based on your size logic
    Board b = new Board(5);
    Assert.assertEquals(11, b.getBoardCells().length); // 5*2+1=11
  }

  //testing invalid position place hex
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPlaceHex() {
    Position pos = new Position(-60, 60, 0);
    board.placeHex(pos, Cell.BLACK);
  }

  //testing a valid position adn invalid position
  @Test
  public void testIsValidPosition2() {
    Position validPos = new Position(0,0,0);
    Position invalidPos = new Position(60,60,30);

    Assert.assertTrue(board.isValidPosition(validPos));
    Assert.assertFalse(board.isValidPosition(invalidPos));
  }

  //testing getCell for empty and black
  @Test
  public void testGetCell2() {
    Position pos = new Position(0,0,0);
    Assert.assertEquals(Optional.empty(), board.getCell(pos));

    board.placeHex(pos, Cell.BLACK);
    Assert.assertEquals(Optional.of(Cell.BLACK), board.getCell(pos));
  }

  //testing illegal board size
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalBoardSize() {
    new Board(-5);
  }

  //testing illegal board size
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalBoardSize2() {
    new Board(0);
  }










}

