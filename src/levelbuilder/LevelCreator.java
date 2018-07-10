/**
 * 
 */
package levelbuilder;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import gameadditions.Army;
import gamelogic.Velocity;
import shapes.Block;
import shapes.Point;
import shapes.Rectangle;
/**
 * @author dudug
 *
 */
public class LevelCreator {
	
	private final static double SHIPSPEED = 300;
	private final static int SHIPWIDTH = 80;
	private final static int SHIELDSHEIGHT = 500;

	private Velocity innitialVelocity;
	private int collumns, rows, round;
	
	public LevelCreator(double inniVelocity, int collumn, int rows1) {
		this.round = 1;
		this.innitialVelocity = new Velocity(inniVelocity, 0);
		this.collumns = collumn;
		this.rows = rows1;
	}
	
	public LevelInformation CreateLevel() {
		String levelName = "Battle no: " + this.round;
		List<Block> shields = new LinkedList<Block>();
		for (int i = 0; i <= 2; i++) {
			shields.addAll(this.createShields(100 + (200 * i)));
		}
		ColorBackground background = new ColorBackground(Color.BLACK);
		background.setX(800);
		background.setY(600);
		Point start = new Point(20, 60);
		Velocity armySpeed = new Velocity(this.innitialVelocity.getDX() 
				+ ((this.round - 1) * 10), 0);
		Army army = new Army(this.collumns, this.rows, start,
			armySpeed, SHIELDSHEIGHT);
		Level level = new Level(levelName, SHIPWIDTH,
            SHIPSPEED, shields, background, army);
		this.round++;
		return level;
	}
	
	private List<Block> createShields(int x) {
		List<Block> shields = new LinkedList<Block>();
		for (int i = SHIELDSHEIGHT; i < SHIELDSHEIGHT + 15; i += 5) {
			for (int j = x; j < x + 150; j += 15) {
				Rectangle rect = new Rectangle(new Point(j, i), 15, 5);
				Block shield = new Block(rect);
				ColorBackground back = new ColorBackground(Color.CYAN);
				back.setX(15);
				back.setY(5);
				shield.setBackground(back);
				shields.add(shield);
			}
		}
		return shields;
	}
	
}
