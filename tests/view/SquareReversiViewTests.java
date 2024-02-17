package view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import model.IReversiModel;
import model.SquarePosition;
import model.SquareReversiModel;

/**
 * Tests the methods in the SquareReversiView class for correct
 * behavior.
 */
public class SquareReversiViewTests {
  IReversiModel model;
  IView view;
  PrintStream outMessage;
  ByteArrayOutputStream outputStreamCaptor;

  @Before
  public void setUp() {
    model = new SquareReversiModel(3);
    view = new SquareReversiView(model);
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
            "_ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ \n",
            outputStreamCaptor.toString());
  }

  //testing the output of the line spacing larger board
  @Test
  public void testPrintBoardSize5() {
    //testing the look at newlines and spacing
    IReversiModel modelSize5 = new SquareReversiModel(5);
    IView viewSize5 = new SquareReversiView(modelSize5);
    viewSize5.renderTextualView();
    Assert.assertEquals(
            "_ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ \n",
            outputStreamCaptor.toString());
  }

  //testing the board at its initial state
  @Test
  public void testInitialBoardState() {
    IReversiModel model2 = new SquareReversiModel(2);
    IView view = new SquareReversiView(model2);
    model2.startGame();
    String view1 = view.toString();
    String expected =
            "_ _ _ _ \n" +
            "_ X O _ \n" +
            "_ O X _ \n" +
            "_ _ _ _ \n";
    Assert.assertEquals(expected, view1);
  }

  //testing the board after Black makes its first move
  @Test
  public void testBoardStateAfterOneMove() {
    IReversiModel model2 = new SquareReversiModel(2);
    IView view = new SquareReversiView(model2);
    model2.startGame();
    model2.executeMove(new SquarePosition(3,1));
    String view1 = view.toString();
    String expected = "_ _ _ _ \n" +
                    "_ X X X \n" +
                    "_ O X _ \n" +
                    "_ _ _ _ \n";
    Assert.assertEquals(expected, view1);
  }
}
