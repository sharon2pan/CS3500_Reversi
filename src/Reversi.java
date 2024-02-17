import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import controller.AiController;
import controller.HumanController;
import controller.IPlayer;
import model.Cell;
import model.IReversiModel;
import model.ReversiModel;
import view.IReversiFrameView;
import view.ReversiFrameView;

/**
 * The Reversi class is the main class of the Reversi game application.
 * This class is responsible for initializing and starting the Reversi game.
 * It sets up the game model, creates the view components (such as the game frame),
 * and orchestrates the overall game flow. The class's main method serves as the entry point
 * for the application.
 */
public class Reversi {
  /**
   * The main entry point of the Reversi game application. It creates the game model, wraps it
   * with a read-only interface, initializes the main frame of the game, and makes it visible.
   * Calls upon the reversi creator class to make players based on the commandline arguments.
   *
   * @param args The command-line arguments passed to the application (not used).
   */
  public static void main(String[] args) {

    IArgParsar ap = new ArgParser(args);
    ap.parseArguments();
    ReversiCreator creator = new ReversiCreator();
    IPlayer player1 = creator.createPlayer(ap.getPlayer1(), Cell.BLACK, ap.getStrategiesPlayer1());
    IPlayer player2 = creator.createPlayer(ap.getPlayer2(), Cell.WHITE, ap.getStrategiesPlayer2());

    //String filePath = "src/MC.wav";
    //playMusic(filePath);
    IReversiModel model = new ReversiModel(3); // Assuming '6' is the board size

    // Create views for each player
    IReversiFrameView viewHumanPlayer = new ReversiFrameView(model);
    IReversiFrameView viewHuman2Player = new ReversiFrameView(model);
    IReversiFrameView viewAIPlayer = new ReversiFrameView(model);
    IReversiFrameView viewAI2Player = new ReversiFrameView(model);

    if (!player1.isAI()) {
      HumanController controllerHuman = new HumanController(model, player1, viewHumanPlayer);
    } else {
      AiController controllerAI = new AiController(model, player1, viewAIPlayer);
    }
    if (!player2.isAI()) {
      HumanController controllerHuman = new HumanController(model, player2, viewHuman2Player);
    } else {
      AiController controllerAI = new AiController(model, player2, viewAI2Player);
    }
    model.startGame();

    // Display the game views
    //viewHumanPlayer.display();
    //viewHuman2Player.display();
    viewAIPlayer.display();
    viewAI2Player.display();
  }

  /**
   * Plays music.
   * @param filePath string for the music file.
   */
  public static void playMusic(String filePath) {
    File musicPath = new File(filePath);
    try {
      if (musicPath.exists()) {
        AudioInputStream audio = AudioSystem.getAudioInputStream(musicPath);
        Clip clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();
      } else {
        System.out.println("Can't find file");
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error");
    }
  }
}
