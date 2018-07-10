package gameadditions;
import java.awt.Color;

import animations.Sprite;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gamelogic.Collidable;
import gamelogic.GameLevel;
import gamelogic.Velocity;
import shapes.Ball;
import shapes.Point;
import shapes.Rectangle;

/**.
 * @author David Geda
 * Spaceship class
 * contains the rectangle responsible for the ship.
 */
public class Spaceship implements Collidable, Sprite {
	
	private static final int SHOTCOOLDOWN = 350;
	private static final int SHIPHEIGHT = 15;

    private biuoop.KeyboardSensor keyboard;
    private Rectangle ship;
    private int width, shipSize;
    private Velocity shipSpeed;
    private boolean isHit;
    private GameLevel game;
    private double cooldown;

    /**.
    * constructor of the class.
    * @param keyboard , the keyboard to recieve hits from the user.
    * @param width , the wanted width of the ship.
    * @param height , the wanted height of the ship.
    * @param shipSize , the wanted size of the ship
    */
    public Spaceship(biuoop.KeyboardSensor keyboard, int width, int height
    		, int shipSize, GameLevel game1) {
        this.keyboard = keyboard;
        this.width = width;
        this.shipSize = shipSize;
        this.shipSpeed = new Velocity(5.0, 0.0);
        this.createRectSpaceship(new Point((width / 2) - (shipSize / 2) - 40, height - SHIPHEIGHT - 10));
        this.isHit = false;
        this.game = game1;
        this.cooldown = SHOTCOOLDOWN;
    }

    /**.
    * a method to make the speed of the ship
    * @param shipSpeed1 , the wanted speed of the ship
    */
    public void setSpaceshipSpeed(Velocity shipSpeed1) {
        this.shipSpeed = shipSpeed1;
    }

    /**.
    * a method to make the speed of the ship
    * @param shipSpeedDx , the wanted speed of the ship
    */
    public void setSpaceshipSpeed(double shipSpeedDx) {
        this.shipSpeed = new Velocity(shipSpeedDx, 0.0);
    }
    /**.
   * method for moving the ship to the left by one step
   * @param dt , the rate of frames
   */
    public void moveLeft(double dt) {
        //check if it reached the border (the border is 20 for now)
        if (this.ship.getUpperLeft().getX() <= 0) {
            return;
        }
        if (this.shipSpeed.getDX() > 0) {
            this.setSpaceshipSpeed((-1) * this.shipSpeed.getDX());
        }
        this.createRectSpaceship(this.shipSpeed.applyToPoint(ship.getUpperLeft(), dt));
    }

    /**.
   * method for moving the ship to the right by one step
   * @param dt , the rate of frames
   */
    public void moveRight(double dt) {
        //check if it reached the border (the border is 20 for now)
        if (this.ship.getUpperLeft().getX() + this.shipSize >= this.width) {
            return;
        }
        if (this.shipSpeed.getDX() < 0) {
            this.setSpaceshipSpeed((-1) * this.shipSpeed.getDX());
        }
        this.createRectSpaceship(this.shipSpeed.applyToPoint(ship.getUpperLeft(), dt));
    }

    /**.
   * method for notifying the ship that time has passed
   * @param dt , the rate of frames
   */
    public void timePassed(double dt) {
    	this.cooldown -= (dt * 1000);
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft(dt);
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight(dt);
        }
        if (keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
        	this.shoot();
        }
    }

    /**.
   * method for adding this ship to the given Game
   * @param g , a Game to add this ship to
   */
    public void addToGame(GameLevel g) {
        Sprite s = this;
        Collidable c = this;
        g.addCollidable(c);
        g.addSprite(s);
    }
    
    public boolean checkHit() {
    	return this.isHit;
    }

    /**.
   * accessor method for getting the rectangle this ship represents
   * @return Rectangle , the ship's Rectangle
   */
    public Rectangle getCollisionRectangle() {
        return this.ship;
    }

    /**.
   * method for notifying the ship that an object has collided with it. and decide
   * the objects new Velocity.
   * @param hitter , the hitter ball
   */
    public void hit(Ball hitter) {
    	hitter.removeBallFromGame(this.game);
    	this.isHit = true;
    }

    /**.
   * method for drawing the ship on the given draw surface
   * @param d , a DrawSurface to draw on.
   */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.YELLOW);
        d.fillRectangle((int) this.ship.getUpperLeft().getX(),
                (int) this.ship.getUpperLeft().getY(),
                this.shipSize, SHIPHEIGHT);
        //draw the ship's borders
        this.ship.drawRectangle(d);
    }

    /**.
   * method for creating a new ship
   * @param upperLeft , the upperleft point of the ship
   */
    private void createRectSpaceship(Point upperLeft) {
        this.ship = new Rectangle(upperLeft, this.shipSize, SHIPHEIGHT);
        this.ship.setColor(Color.BLACK);
    }

    /**.
     * a method for removing a hit listener from this block
     * @param game , a game to remove from
    */
    public void removeSpaceshipFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }
    
    private void shoot() {
    	if (this.cooldown <= 0) {
        	int shootX = (int) this.ship.getUpperLeft().getX() + (this.shipSize / 2);
        	int shootY = (int) this.ship.getUpperLeft().getY() - 5;
    		Ball bullet = new Ball(shootX, shootY, 2, Color.WHITE);
    		bullet.setVelocity(0, (-1) * 300);
    		bullet.setGameEnvironment(this.game.getEnvironment());
    		bullet.addToGame(this.game);
    		this.cooldown = SHOTCOOLDOWN;
    	}
    }

}
