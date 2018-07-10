package levelbuilder;

import java.util.List;

import animations.Sprite;
import gameadditions.Army;
import shapes.Block;

/**.
 * @author David Geda
 * ID: 313237182
 * Level class
 * the class ontaining the main logic the levels
 */
public class Level implements LevelInformation {


    private int shipWidth, width, height;
    private double shipSpeed;
    private List<Block> shields;
    private Sprite background;
    private String levelName;
    private Army army;

  /**.
     * the constructor of the class
     * @param numOfBalls1 ,
     * @param shipWidth1 ,
     * @param numBlocksRemove1 ,
     * @param shipSpeed1 ,
     * @param intitialBallVel1 ,
     * @param blocks1 ,
     * @param background1 ,
     */
    public Level(String levelName1, int shipWidth1,
            double shipSpeed1, List<Block> shields1, Sprite background1,
            Army army1) {
    	this.levelName = levelName1;
        this.shipWidth = shipWidth1;
        this.shipSpeed = shipSpeed1;
        this.shields = shields1;
        this.background = background1;
        this.army = army1;
        this.height = 600;
        this.width = 800;
    }

  /**.
     * @return double ,
     */
    @Override
    public double getSpaceshipSpeed() {
        // TODO Auto-generated method stub
        return this.shipSpeed;
    }

  /**.
     * @return int ,
     */
    @Override
    public int getSpaceshipWidth() {
        // TODO Auto-generated method stub
        return this.shipWidth;
    }

  /**.
     * @return String ,
     */
    @Override
    public String getLevelName() {
        // TODO Auto-generated method stub
        return this.levelName;
    }

  /**.
     * @return Sprite ,
     */
    @Override
    public Sprite getBackground() {
        // TODO Auto-generated method stub
        return this.background;
    }

  /**.
     * @return int ,
     */
    @Override
    public int getHeight() {
        // TODO Auto-generated method stub
        return this.height;
    }

    /**.
     * @return int ,
     */
    @Override
    public int getWidth() {
        // TODO Auto-generated method stub
        return this.width;
    }

    @Override
    public Army getArmy() {
    	return this.army;
    }
    
    @Override
    public List<Block> getShields() {
    	return this.shields;
    }
}
