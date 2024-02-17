package view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import model.Cell;
import model.IReversiModel;
import model.Position;
import model.ReversiModel;

/**
 * Tests the methods in the ReversiView class for correct
 * behavior.
 */
public class ReversiViewTests {
  IReversiModel model;
  IView view;
  PrintStream outMessage;
  ByteArrayOutputStream outputStreamCaptor;

  @Before
  public void setUp() {
    model = new ReversiModel(3);
    view = new ReversiView(model);
    outMessage = System.out;
    outputStreamCaptor = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  //testing the output of the line spacing
  @Test
  public void testPrintBoardSize3() {
    //testing the look at newlines and spacing
    view.renderTextualView();
    Assert.assertEquals(
            "   _ _ _ _ \n" +
                    "  _ _ _ _ _ \n" +
                    " _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ \n" +
                    " _ _ _ _ _ _ \n" +
                    "  _ _ _ _ _ \n" +
                    "   _ _ _ _ \n",
            outputStreamCaptor.toString());
  }

  //testing the output of the line spacing larger board
  @Test
  public void testPrintBoardSize5() {
    //testing the look at newlines and spacing
    IReversiModel modelSize5 = new ReversiModel(5);
    IView viewSize5 = new ReversiView(modelSize5);
    viewSize5.renderTextualView();
    Assert.assertEquals(
            "     _ _ _ _ _ _ \n" +
                    "    _ _ _ _ _ _ _ \n" +
                    "   _ _ _ _ _ _ _ _ \n" +
                    "  _ _ _ _ _ _ _ _ _ \n" +
                    " _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ \n" +
                    " _ _ _ _ _ _ _ _ _ _ \n" +
                    "  _ _ _ _ _ _ _ _ _ \n" +
                    "   _ _ _ _ _ _ _ _ \n" +
                    "    _ _ _ _ _ _ _ \n" +
                    "     _ _ _ _ _ _ \n",
            outputStreamCaptor.toString());
  }

  //testing the board at its initial state
  @Test
  public void testInitialBoardState() {
    IReversiModel model2 = new ReversiModel(2);
    IView view = new ReversiView(model2);
    model2.startGame();
    String view1 = view.toString();
    String expected =
            "  _ _ _ \n" +
                    " _ X O _ \n" +
                    "_ O _ X _ \n" +
                    " _ X O _ \n" +
                    "  _ _ _ \n";
    Assert.assertEquals(expected, view1);


  }

  //testing the board when white wins
  @Test
  public void testWhiteWinsBoard() {
    IReversiModel model2 = new ReversiModel(2);
    IView view = new ReversiView(model2);
    model2.startGame();
    view.renderTextualView();
    Assert.assertEquals(Cell.BLACK, model2.getCurrentTurn());
    model2.passTurn(); //b
    view.renderTextualView();
    Assert.assertEquals(Cell.WHITE, model2.getCurrentTurn());
    model2.executeMove(new Position(-2, 1, 1)); //w
    view.renderTextualView();
    Assert.assertEquals(Cell.BLACK, model2.getCurrentTurn());
    model2.passTurn(); //b
    view.renderTextualView();
    Assert.assertEquals(Cell.WHITE, model2.getCurrentTurn());
    model2.executeMove(new Position(1, -2, 1)); //w
    view.renderTextualView();
    Assert.assertEquals(Cell.BLACK, model2.getCurrentTurn());
    model2.passTurn(); //b
    view.renderTextualView();
    Assert.assertEquals(Cell.WHITE, model2.getCurrentTurn());
    model2.executeMove(new Position(1, 1, -2)); //w
    view.renderTextualView();
    String expected = "" +
            "  _ O _ \n" +
            " _ O O _ \n" +
            "_ O _ O _ \n" +
            " O O O O \n" +
            "  _ _ _ \n";
    Assert.assertEquals(expected, view.toString());
  }

  //testing board for tie game
  @Test
  public void testTieGameBoard() {
    IReversiModel model2 = new ReversiModel(2);
    model2.startGame();
    IView view = new ReversiView(model2);
    Assert.assertEquals(3, model2.getScore(Cell.BLACK));
    Assert.assertEquals(3, model2.getScore(Cell.WHITE));
    view.renderTextualView();
    model2.executeMove(new Position(2, -1, -1)); //b
    view.renderTextualView();
    model2.executeMove(new Position(-2, 1, 1)); //w
    view.renderTextualView();
    model2.passTurn(); //b
    view.renderTextualView();
    model2.executeMove(new Position(1, -2, 1)); //w
    view.renderTextualView();
    model2.executeMove(new Position(-1, -1, 2)); //b
    view.renderTextualView();
    Assert.assertEquals(5, model2.getScore(Cell.BLACK));
    Assert.assertEquals(5, model2.getScore(Cell.WHITE));
    model2.passTurn(); //w
    view.renderTextualView();
    model2.passTurn(); //b
    String expected = "" +
            "  _ O _ \n" +
            " X X X X \n" +
            "_ O _ X _ \n" +
            " O O O _ \n" +
            "  _ _ _ \n";
    Assert.assertEquals(expected, view.toString());

  }
}

