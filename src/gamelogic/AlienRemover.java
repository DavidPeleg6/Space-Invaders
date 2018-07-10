package gamelogic;

import animations.Counter;
import shapes.Ball;
import shapes.Block;

public class AlienRemover extends BlockRemover {

	public AlienRemover(GameLevel game, Counter removedBlocks) {
		super(game, removedBlocks);
	}
	
    /**.
   * a method responsible for marking a hit event
   * @param beingHit , the block that is currently hit
   * @param hitter , the violent ball
   */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
    	if (hitter.getVelocity().getDY() > 0) {
    		beingHit.reviveBlock();
    		return;
    	}
    	super.hitEvent(beingHit, hitter);
    }

}
