package gamelogic;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import animations.Animation;
import animations.AnimationRunner;
import animations.CountdownAnimation;
import animations.Counter;
import animations.EndScreen;
import animations.KeyPressStoppableAnimation;
import animations.PauseScreen;
import animations.Sprite;
import animations.SpriteCollection;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gameadditions.Alien;
import gameadditions.Army;
import gameadditions.Spaceship;
import levelbuilder.ColorBackground;
import levelbuilder.Header;
import levelbuilder.LevelInformation;
import shapes.Block;
import shapes.Point;
import shapes.Rectangle;

/**.
 * @author David Geda
 * GameLevel class
 * an object managing the gameLevel.
 * also responsible for innitializing and moving all the game objects.
 */
public class GameLevel implements Animation {

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private AlienRemover alienRemov;
    private BlockRemover shieldRemov;
    private BallRemover ballRemov;
    private ScoreTrackingListener scoreStat;
    private ScoreIndicator scoreInd;
    private Spaceship ship;
    private LivesIndicator lives;
    private AnimationRunner runner;
    private boolean running;
    private LevelInformation lvlInfo;
    private KeyboardSensor keySense;
    private Army army;

    /**.
   * constructor method of the class.
   * @param runner , an animation runner
   * @param lvlInfo , the level information
   * @param keySense , a keyboard sensor
   * @param scoreInd , a score indicator
   * @param lives , the current amount of lives
   */
    public GameLevel(AnimationRunner runner, LevelInformation lvlInfo, KeyboardSensor keySense,
            ScoreIndicator scoreInd, LivesIndicator lives) {
        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
        lvlInfo.getBackground().addToGame(this);
        this.army = lvlInfo.getArmy();
        this.army.updateGameLevel(this);
        this.alienRemov = new AlienRemover(this, new Counter(this.army.getArmySize()));
        this.shieldRemov = new BlockRemover(this, new Counter(lvlInfo.getShields().size()));
        this.ballRemov = new BallRemover(this);
        this.keySense = keySense;
        this.scoreInd = scoreInd;
        this.scoreStat = new ScoreTrackingListener(new Counter(scoreInd.getScore()));
        this.lives = lives;
        this.runner = runner;
        this.running  = true;
        this.lvlInfo = lvlInfo;
    }
    
    public GameEnvironment getEnvironment() {
    	return this.environment;
    }

    /**.
   * a method add another Collidable to the Game
   * @param c , the new Collidable to be added.
   */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**.
   * a method add another Sprite to the Game
   * @param s , the new Sprite to be added.
   */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**.
    * a method to innitialize all the Game objects required
    */
    public void initialize() {
        this.army.addToGame(this);
		List<Alien> blocks = new LinkedList<Alien>();
		for (List<Alien> collumn : this.army.getAliens()) {
			blocks.addAll(collumn);
		}
        //add aliens to game
        for (Alien alien : blocks) {
            alien.getAlienBlock().addHitListener(this.scoreStat);
            alien.getAlienBlock().addHitListener(this.alienRemov);
            alien.getAlienBlock().addHitListener(this.ballRemov);
            alien.getAlienBlock().addToGame(this);
        }
        List<Block> shields = this.lvlInfo.getShields();
        for (Block b : shields) {
        	b.addHitListener(this.shieldRemov);
        	b.addHitListener(this.ballRemov);
        	b.addToGame(this);
        }
        this.createBorders(this.lvlInfo.getWidth(), this.lvlInfo.getHeight());
        //add score counter
        this.scoreInd.addToGame(this);
        //add life counter
        this.lives.addToGame(this);
        Header head = new Header(this.scoreInd, this.lives, this.lvlInfo.getLevelName());
        head.addToGame(this);
    }

    /**.
    * a method to to run all the innitialized objects in the game
    */
    public void playOneTurn() {
        this.createSpaceship(this.lvlInfo.getWidth(), this.lvlInfo.getHeight());
        this.runner.run(new CountdownAnimation(3, 3, this.sprites));
        this.runner.run(this);
    }

    /**.
    * a method to to create the ship and the balls in the game
    * @param width , the border of this game
    * @param height , the border of this game
    */
    private void createSpaceship(int width, int height) {
        //create Spaceship
        this.ship = new Spaceship(this.keySense, width, height, this.lvlInfo.getSpaceshipWidth(), this);
        this.ship.setSpaceshipSpeed(this.lvlInfo.getSpaceshipSpeed());
        this.ship.addToGame(this);
    }

    /**.
    * a method to create the borders of the Game
    * @param width , the borders of the game
    * @param height , the borders of the game
    */
    public void createBorders(int width, int height) {
        Block[] blocks = new Block[2];
        //borders
        blocks[0] = new Block(new Rectangle(new Point((-1) * 100, (-1) * 20), width + 100, 50));
        blocks[1] = new Block(new Rectangle(new Point((-1) * 100, height + 50), width + 100, 40));
        for (int i = 0; i < 2; i++) {
            blocks[i].setBackground(new ColorBackground(Color.BLACK));
            blocks[i].addHitListener(this.ballRemov);
            blocks[i].addToGame(this);
        }
    }

    /**.
   * a method for removing a sprite fro mthe game
   * @param c , a collidable to remove from the game
   */
    public void removeCollidable(Collidable c) {
        this.environment.removeFromEnv(c);
    }

    /**.
   * a method for removing a sprite fro mthe game
   * @param s , a sprite to remove
   */
    public void removeSprite(Sprite s) {
        this.sprites.removeFromSprites(s);
    }

       /**.
       * a method for running a frame
       * @param d , a draw surface to draw on
       * @param dt , the rate of frames
       */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        biuoop.KeyboardSensor keyboard = this.runner.getGui().getKeyboardSensor();
        if (keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(keyboard,
                    KeyboardSensor.SPACE_KEY, new PauseScreen(new EndScreen())));
         }
        //draw all Sprites
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        this.scoreInd.setScore(scoreStat.getCurScore());
        if (this.alienRemov.getRemainingBlocks() == 0) {
              this.scoreStat.setScoreListen(this.scoreStat.getCurScore());
              this.scoreInd.setScore(this.scoreStat.getCurScore());
              this.sprites.drawAllOn(d);
              this.running = false;
        } else if ((this.ship.checkHit()) 
        		|| (this.army.shouldStop())) {
              this.ship.removeSpaceshipFromGame(this);
              this.lives.setLives(this.lives.getLives() - 1);
              if (this.lives.getLives() == 0) {
                  this.sprites.drawAllOn(d);
                  this.running = false;
              } else {
            	  this.army.resetArmy();
                  this.createSpaceship(this.lvlInfo.getWidth(), this.lvlInfo.getHeight());
                  this.runner.run(new CountdownAnimation(3, 3, this.sprites));
              }
        }
    }

    /**.
   * a method for deciding whether the loop should stop
   * @return boolean , should or shouldnt
   */
    @Override
    public boolean shouldStop() {
        return this.running;
    }

}
