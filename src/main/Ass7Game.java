package main;

import java.io.File;
import java.io.IOException;

import animations.AnimationRunner;
import animations.EndScreen;
import animations.HighScoresAnimation;
import biuoop.KeyboardSensor;
import gamelogic.HighScoresTable;
import levelbuilder.Background2;
import levelbuilder.LevelCreator;
import menu.Menu;
import menu.MenuAnimation;
import menu.QuitGameTask;
import menu.RunGameTask;
import menu.ShowHiScoreTask;
import menu.Task;

/**.
 * @author David Geda
 * ID: 313237182
 * Ass6Game class
 * the class ontaining the main method
 */
public class Ass7Game {

    /**.
     * the main method for running this game
   * @param args , arguments from user will define the levels played
   * and their order
   */
    public static void main(String[] args) {
        File file = new File("highscores");
        HighScoresTable scores = HighScoresTable.loadFromFile(file);
        if (scores.getHighScores().isEmpty()) {
        	scores = new HighScoresTable(10);
            try {
                scores.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AnimationRunner runner = new AnimationRunner();
        KeyboardSensor keySense = runner.getGui().getKeyboardSensor();
        HighScoresAnimation hiS = new HighScoresAnimation(scores, new EndScreen());
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Space Invaders", new Background2(), keySense);
        LevelCreator levels = new LevelCreator(100, 10, 5);
        menu.addSelection("q", "quit", new QuitGameTask(runner));
        menu.addSelection("h", "highscore table", new ShowHiScoreTask(runner, hiS, keySense));
        menu.addSelection("s", "start new game", new RunGameTask(levels, runner, keySense, scores, file, 3));
        while (true) {
            runner.run(menu);
            Task<Void> t = menu.getStatus();
            try {
                scores.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            t.run();
        }
    }
}
