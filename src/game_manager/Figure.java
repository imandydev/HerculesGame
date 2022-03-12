package gameManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Observer;

import inter_face.Game;

import map.PhysicalMap;
import mvc.Model;
// nhân vật trong game
public abstract class Figure extends GameObject {
	private int width;
	private int height;
	private float speedX;
	private float speedY;
	private int dir;
	private float mass;
	private boolean touch;
	private double life;
	public static final int DEATH = 5;
	private long timeStartDeath;

	public Figure(int x, int y, Model game, int width, int height, int speedX, int speedY, int dir, float mass) {
		super(x, y, game);
		this.width = width;
		this.height = height;
		this.speedX = speedX;
		this.speedY = speedY;
		this.dir = dir;
		this.mass = mass;

	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public Rectangle getRectangleMain() {
		return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
	}

	public boolean isTouch() {
		return touch;
	}

	public void setTouch(boolean touch) {
		this.touch = touch;
	}

	public double getLife() {
		return life;
	}

	public void setLife(double life) {
		this.life = life;
	}

	public long getTimeStartDeath() {
		return timeStartDeath;
	}

	public void setTimeStartDeath(long timeStartDeath) {
		this.timeStartDeath = timeStartDeath;
	}
	// xử lý va chạm tường
	public void update() {
		setX(getX() + getSpeedX());
		setY(getY() + getSpeedY());

		if (getGame().getMapPhysic().getRectangleRight(getRectangleMain()) != null) {
			setX(getGame().getMapPhysic().getRectangleRight(getRectangleMain()).x - getWidth() - 1);
			setSpeedX(0);
			touch = true;
		} else if (getGame().getMapPhysic().getRectangleLeft(getRectangleMain()) != null) {
			setX(getGame().getMapPhysic().getRectangleLeft(getRectangleMain()).x + 31);
			setSpeedX(0);
			touch = true;
		} else if (getGame().getMapPhysic().getRectangleUp(getRectangleMain()) != null) {
			setY(getGame().getMapPhysic().getRectangleUp(getRectangleMain()).y
					+ getGame().getMapPhysic().getRectangleUp(getRectangleMain()).height);
			setSpeedY(getSpeedY() + mass);
		} else if (getGame().getMapPhysic().getRectangleBottom(getRectangleMain()) == null) {
			setSpeedY(getSpeedY() + mass);
		} else {
			setY(getGame().getMapPhysic().getRectangleBottom(getRectangleMain()).y + 1 - getHeight());
			setSpeedY(0);
		}
		if (getRectangleMain().getX() < 0) {
			setX(0);
		}
	}

}
