package gameadditions;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import animations.Sprite;
import biuoop.DrawSurface;
import gamelogic.GameLevel;
import gamelogic.Velocity;
import levelbuilder.Background;
import levelbuilder.ImageBackground;
import shapes.Block;
import shapes.Point;
import shapes.Rectangle;

public class Army implements Sprite {
	
    private static final int SHOTCOOLDOWN = 1000;
	
	private List<List<Alien>> army;
	private Velocity speed;
	private Velocity innitialSpeed;
	private GameLevel game;
	private double cooldown;
	private boolean stop;
	private int sheildsHeight;
	
	public Army(int collumns, int rows, Point start,
			Velocity innitialSpeed1, int shieldHeight) {
		this.speed = innitialSpeed1;
		this.innitialSpeed = innitialSpeed1;
		this.army = new LinkedList<List<Alien>>();
		this.createArmy(collumns, rows, start);
		this.stop = false;
		this.sheildsHeight = shieldHeight;
		this.cooldown = SHOTCOOLDOWN;
	}
	
	public boolean shouldStop() {
		return this.stop;
	}
	
	public List<List<Alien>> getAliens() {
		return this.army;
	}
	
	public int getArmySize() {
		int collumns = this.army.size();
		int size = collumns * this.army.get(0).size();
		return size;
	}
	
	@Override
	public void drawOn(DrawSurface d) {
		
	}
	@Override
	public void timePassed(double dt) {
		this.checkMissing();
		this.moveAllAliens(dt);
		this.shootRand(dt);
	}
	@Override
	public void addToGame(GameLevel g) {
		g.addSprite(this);
	}
	
	public void resetArmy() {
		this.speed = this.innitialSpeed;
		for (List<Alien> collumn : this.army) {
			for(Alien alien : collumn) {
				alien.backToOriginal();
			}
		}
	}
	
	private List<List<Alien>> createArmy(int collumns, int rows, Point start) {
		Point cur = start;
		Background back = null;
		try {
			back = new ImageBackground("resources/background_images/enemy.png");
		} catch (Exception e) {
			System.out.println("unnable to find enemy photo");
			System.exit(1);
		}
		List<List<Alien>> allArmy = new LinkedList<List<Alien>>();
		for(int i = 0; i < collumns; i++) {
			cur = new Point(cur.getX() + 40, cur.getY());
			List<Alien> temp = this.createCollumn(rows, cur, back);
			this.army.add(temp);
		}
		return allArmy;
	}
	
	private List<Alien> createCollumn(int rows, Point start,
			Background back) {
		List<Alien> row = new LinkedList<Alien>();
		for (int i = 0; i < rows; i++) {
			Point upperLeft = new Point(start.getX(), start.getY() + 40 * i);
			Rectangle rect = new Rectangle(upperLeft, 40, 30);
			Block block = new Block(rect);
			block.setBackground(back);
			Alien alien = new Alien(block, upperLeft);
			row.add(alien);
		}
		return row;
	}
	
	private void moveAllAliens(double dt) {
		//check width borders
		if (this.checkBorders(dt)) {
			this.moveAll(dt);
			return;
		} else {
			//check height borders
			for (List<Alien> collumn : this.army) {
				Alien alien = collumn.get(collumn.size() - 1);
				if(alien.getAlienBlock().getLeft().getY() + 40
						>= this.sheildsHeight) {
					this.stop = true;
					return;
				}
			}
			this.stepAllDown();
			this.speed = new Velocity((-1.1) * this.speed.getDX(), 0);
			this.moveAll(dt);
		}
	}
	
	private boolean checkBorders(double dt) {
		Alien alien = this.army.get(0).get(0);
		if (alien.getAlienBlock().getLeft().getX() <= 0) {
			return false;
		}
		alien = this.army.get(this.army.size() - 1).get(0);
		if (alien.getAlienBlock().getLeft().getX() + 50 >= 800) {
			return false;
		}

		return true;
	}
	
	private void moveAll(double dt) {
		for (List<Alien> row : this.army) {
			for (Alien alien : row) {
				alien.moveOneStep(this.speed, dt);
			}
		} 
	}
	
	private void stepAllDown() {
		for (List<Alien> collumn : this.army) {
			for (Alien alien : collumn) {
				alien.stepDown(10);
			}
		}
	}
	
	private void checkMissing() {
		for(int i = 0; i < this.army.size(); i++) {
			for (int j = 0; j < this.army.get(i).size(); j++) {
				if(!this.army.get(i).get(j).checkAlien()) {
					this.army.get(i).remove(j);
				}
			}
			if(this.army.get(i).isEmpty()) {
				this.army.remove(i);
			}
		}
	}
	
	private void shootRand(double dt) {
		this.cooldown -= (dt * 1000);
		if (this.cooldown <= 0) {
			Random rand = new Random();
			int  n = rand.nextInt(this.army.size());
			List<Alien> collumn = this.army.get(n);
			Alien shooter = collumn.get(collumn.size() - 1);
			shooter.shoot(this.game);
			this.cooldown = SHOTCOOLDOWN;
		}
	}
	
	public void updateGameLevel(GameLevel game1) {
		this.game = game1;
	}

}
