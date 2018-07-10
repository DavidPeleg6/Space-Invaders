package shapes;
import java.util.ArrayList;
import java.util.List;

import animations.Sprite;
import biuoop.DrawSurface;
import gamelogic.Collidable;
import gamelogic.GameLevel;
import gamelogic.HitListener;
import gamelogic.HitNotifier;
import levelbuilder.Background;

/**.
 * @author David Geda
 * ID: 313237182
 * Block class
 * an object representing a Block.
 * also responsible for drawing itself.
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private List<HitListener> hitListeners;
    private Background fill;
    private Rectangle rect;
    private boolean isAlive;

    /**.
   * constructor method of the class
   * @param rect , the Rectangle the Block represents
   */
    public Block(Rectangle rect) {
        this.rect = rect;
        this.hitListeners = new ArrayList<HitListener>();
        this.isAlive = true;
    }

    /**.
   * @param fills1 ,
   */
    public void setBackground(Background fill1) {
        this.fill = fill1;
    }
    
    public boolean checkIfAlive() {
    	return this.isAlive;
    }
    
    public Point getLeft() {
    	return this.rect.getUpperLeft();
    }
    
    public void setRect(Point newLeft) {
        this.rect = new Rectangle(newLeft, this.rect.getWidth(), this.rect.getHeight());
    }
    
    /**.
   * the accesor method to get the Rectangle this Block represents
   * @return Rectangle , the Rectangle this Block represents.
   */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**.
   * a method to to notify the Block that it has been hit
   * @param hitter , the ball involved in the hit
   */
    public void hit(Ball hitter) {
    	this.isAlive = false;
        this.notifyHit(hitter);
    }

    /**.
   * a method to make the Block draw itself on the DrawSurface
   * @param d , the DrawSurface to draw on
   */
    public void drawOn(DrawSurface d) {
        this.fill.drawBack(d,
                (int) this.rect.getUpperLeft().getX(),
                (int) this.rect.getUpperLeft().getY());
    }

    /**.
   * a mysterious method to notify the Block time has passed. it has great potential yet to be revealed
   * @param dt , the rate of frames
   */
    public void timePassed(double dt) {

    }

    /**.
   * a method to make the Block add itself to the Game
   * @param g , a Game to add the Block to
   */
    public void addToGame(GameLevel g) {
        Sprite s = this;
        Collidable c = this;
        g.addCollidable(c);
        g.addSprite(s);
    }

    /**.
     * a method for removing this block from the game
     * @param game , a game to remove from
    */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**.
     * a method for adding a hit listener to this block
     * @param hl , a hit listener to add
    */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**.
     * a method for removing a hit listener from this block
     * @param hl , a hit listener to remove
    */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**.
     * a method for notifying an object its been hit through the hit listener
     * @param hitter , the ball which interacted with this block
    */
     private void notifyHit(Ball hitter) {
          // Make a copy of the hitListeners before iterating over them.
          List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
          // Notify all listeners about a hit event:
          for (HitListener hl : listeners) {
             hl.hitEvent(this, hitter);
          }
       }
     
     public void reviveBlock() {
    	 this.isAlive = true;
     }

}
