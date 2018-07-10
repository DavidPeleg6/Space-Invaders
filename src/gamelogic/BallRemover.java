package gamelogic;
import shapes.Ball;
import shapes.Block;

/**.
 * @author David Geda
 * ID: 313237182
 * BallRemover class
 * a listener
 */
public class BallRemover implements HitListener {

    private GameLevel game;

    /**.
   * constructor method of the class
   * @param game , a game to listen to
   * @param removedBalls , the number of balls to be removed
   */
    public BallRemover(GameLevel game) {
        this.game = game;
    }

        /**.
        * a method responsible for marking a hit event
        * @param beingHit , the block that is currently hit
        * @param hitter , the violent ball
        */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeBallFromGame(this.game);
    }

}
