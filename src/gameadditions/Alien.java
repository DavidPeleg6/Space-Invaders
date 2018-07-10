package gameadditions;

import java.awt.Color;

import gamelogic.GameLevel;
import gamelogic.Velocity;
import shapes.Ball;
import shapes.Block;
import shapes.Point;

public class Alien implements Shooter {
	
	private Block alien;
	private Point originalLocation;
	
	public Alien(Block block, Point originalLocation1) {
		this.alien = block;
		this.originalLocation = originalLocation1;
	}

	@Override
	public void shoot(GameLevel game) {
		Point center = new Point(this.alien.getLeft().getX() + 20,
				this.alien.getLeft().getY() + 35);
		Ball bullet = new Ball(center, 3, Color.RED);
		bullet.setVelocity(0, 300);
		bullet.setGameEnvironment(game.getEnvironment());
		bullet.addToGame(game);
	}
	
	public void moveOneStep(Velocity v, double dt) {
		Point newLeft = v.applyToPoint(this.alien.getLeft(), dt);
		this.alien.setRect(newLeft);
	}
	
	public void stepDown(int step) {
		Point newLeft = new Point(this.alien.getLeft().getX(),
				this.alien.getLeft().getY() + step);
		this.alien.setRect(newLeft);
	}
	
	public void backToOriginal() {
		this.alien.setRect(this.originalLocation);;
	}
	
	public Block getAlienBlock() {
		return this.alien;
	}

	public boolean checkAlien() {
		return this.alien.checkIfAlive();
	}

}
