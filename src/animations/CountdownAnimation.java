package animations;
import java.awt.Color;

import biuoop.DrawSurface;

/**.
 * @author David Geda
 * ID: 313237182
 * CountdownAnimation class
 * an object for counting down before round starts
 */
public class CountdownAnimation implements Animation {

    private double numOfSeconds;
    private int countFrom;
    private int numToPrint;
    private double startTime;
    private SpriteCollection gameScreen;
    private boolean stop;

    /**.
   * constructor method of the class
   * @param numOfSeconds , num of seconds to pause between each count
   * @param countFrom , the innitial number to start counting
   * @param gameScreen , the screen to keep as background
   */
    public CountdownAnimation(double numOfSeconds, int countFrom1, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom1 + 1;
        this.gameScreen = gameScreen;
        this.numToPrint = countFrom1;
        this.startTime = numOfSeconds;
        this.stop = true;
    }

    /**.
   * a method for running a frame
   * @param d , a draw surface to draw on
   * @param dt , the rate of frames
   */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.WHITE);
        double current = (this.startTime / this.numOfSeconds) * this.countFrom;
        if (current < 1) {
        	d.drawText(d.getWidth() / 2, d.getHeight() / 2, "GO!", 30);
        } else {
        	this.numToPrint = ((int) (current - (current % 1)));
        	String s = Integer.toString(this.numToPrint);
            d.drawText(d.getWidth() / 2, d.getHeight() / 2, s, 30);
        }
        if (current < 0) {
        	this.stop = false;
        }
        this.startTime -= dt;
    }

    /**.
   * a method for deciding whether the loop should stop
   * @return boolean , should or shouldnt
   */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
