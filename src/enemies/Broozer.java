package Enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import animation_Image.Animation;
import gameManager.Figure;
import inter_face.Game;
import mvc.Model;

public class Broozer extends Figure implements  Game {
	private Animation moveLeft, moveRight, attackLeft, attackRight, bearAttackLeft, bearAttackRight;
	private boolean isAttack, isMove, isBearDamage, isDeath;
	private double hp = 100;
	private Observable obs;

	public Broozer(int x, int y, Model game, int width, int height, int speedX, int speedY, int dir, float mass,
			Observable obs) {
		super(x, y, game, width, height, speedX, speedY, dir, mass);
		animationBroozer();
		mass = 0.1f;
		obs.addObserver(this);
	}

	// đọc animation từ file txt
	public void animationBroozer() {
		moveRight = new Animation(getGame().getInputData().getListAnimation().get("broozerMove"));
		moveLeft = new Animation(getGame().getInputData().getListAnimation().get("broozerMove"));
		moveRight.daoNguoc();
		attackRight = new Animation(getGame().getInputData().getListAnimation().get("broozerAttack"));
		attackLeft = new Animation(getGame().getInputData().getListAnimation().get("broozerAttack"));
		attackRight.daoNguoc();
		bearAttackLeft = new Animation(getGame().getInputData().getListAnimation().get("broozerBear"));
		bearAttackRight = new Animation(getGame().getInputData().getListAnimation().get("broozerBear"));
		bearAttackRight.daoNguoc();
	}

	public void update(Observable o, Object arg) {
		if (o instanceof Model) {
			super.update();
			// chưa va chạm và ở bên phải hercules thì tiếp tục di chuyển
			if (getX() > getGame().getHercules().getX()
					&& !getRectangleMain().intersects(getGame().getHercules().getRectangleVaChamEnemies())) {
				if (!isDeath) {
					setDir(0);
					run();
					isMove = true;
					isAttack = false;
					// chết thì k di chuyển
				} else
					setSpeedX(0);
				// chưa va chạm và ở bên trái hercules thì tiếp tục di chuyển
			} else if (getX() < getGame().getHercules().getX()
					&& !getRectangleMain().intersects(getGame().getHercules().getRectangleVaChamEnemies())) {
				if (!isDeath) {
					setDir(1);
					run();
					isMove = true;
					isAttack = false;
					// chết thì k di chuyển
				} else
					setSpeedX(0);

			}
			// va chạm với hercules thì đứng lại đánh nhau
			else if (getRectangleMain().intersects(getGame().getHercules().getRectangleVaChamEnemies())) {
				if (!isDeath) {
					isAttack = true;
					isMove = false;
					setSpeedX(0);
				}

			}
			// trung skill mat 5 hp
			if (getRectangleMain().intersects(getGame().getEffectAttack().getRectangleMain())
					&& !getRectangleMain().intersects(getGame().getHercules().getRectangleMain())) {
				hp = hp - 5;
				// bi chem mat  hp
			}
			if (getRectangleMain().intersects(getGame().getHercules().getRectangleXuLiVaChamSword())) {
				hp = hp - 0.5;
				// chon thoi gian ngay luc chet
			}
			if (hp >= -2 && hp <= 0) {
				setTimeStartDeath(System.nanoTime());
			}
			// 0 mau la chet
			if (hp <= 0) {
				isDeath = true;
				isMove = false;
				isAttack = false;
				setLife(5);
				hp = hp - 3;
			}

		}
	}
	// vẽ
	public void draw(Graphics2D g) {
		// vẽ thanh máu
		if (hp < 0) {
			g.setColor(Color.WHITE);
			g.drawString("HP" + " " + 0, (int) (getX() - getGame().getCamera().getX() + 60),
					(int) (getY() - getGame().getCamera().getY()));

		} else {
			g.setColor(Color.WHITE);
			g.drawString("HP" + " " + (int) hp, (int) (getX() - getGame().getCamera().getX() + 60),
					(int) (getY() - getGame().getCamera().getY()));

		}
		// bên trái
		if (getDir() == 0) {
			if (isMove) {
				moveLeft.upload(System.nanoTime());
				moveLeft.drawBroozer(g, (int) (getX() - getGame().getCamera().getX()),
						(int) (getY() - getGame().getCamera().getY()));
				// đánh nhau
			} else if (!isMove && isAttack) {
				attackLeft.upload(System.nanoTime());
				attackLeft.drawBroozer(g, (int) (getX() - getGame().getCamera().getX()),
						(int) (getY() - getGame().getCamera().getY()));
				if (attackLeft.getCurrentFrame() == 3) {
					attackLeft.getIgnoreImage().set(0, true);
					attackLeft.getIgnoreImage().set(1, true);
					attackLeft.getIgnoreImage().set(2, true);

				}
				// chết
			} else if (isDeath) {
				bearAttackLeft.upload(System.nanoTime());
				bearAttackLeft.drawBroozer(g, (int) (getX() - getGame().getCamera().getX()),
						(int) (getY() - getGame().getCamera().getY()));
			}
			// bên phải
		} else if (getDir() == 1) {
			if (isMove) {
				moveRight.upload(System.nanoTime());
				moveRight.drawBroozer(g, (int) (getX() - getGame().getCamera().getX()),
						(int) (getY() - getGame().getCamera().getY()));
				// đánh nhau 
			} else if (!isMove && isAttack) {
				attackRight.upload(System.nanoTime());
				attackRight.drawBroozer(g, (int) (getX() - getGame().getCamera().getX()),
						(int) (getY() - getGame().getCamera().getY()));
				if (attackRight.getCurrentFrame() == 3) {
					attackRight.getIgnoreImage().set(0, true);
					attackRight.getIgnoreImage().set(1, true);
					attackRight.getIgnoreImage().set(2, true);

				}
				// chết
			} else if (isDeath) {
				bearAttackRight.upload(System.nanoTime());
				bearAttackRight.drawBroozer(g, (int) (getX() - getGame().getCamera().getX()),
						(int) (getY() - getGame().getCamera().getY()));
			}
		}

	}
	// di chuyển
	public void run() {
		if (getDir() == 0) {
			setSpeedX(-1);
		} else
			setSpeedX(+1);
	}

	public boolean isAttack() {
		return isAttack;
	}

	public void setAttack(boolean isAttack) {
		this.isAttack = isAttack;
	}

	public boolean isMove() {
		return isMove;
	}

	public void setMove(boolean isMove) {
		this.isMove = isMove;
	}

	public boolean isDeath() {
		return isDeath;
	}

	public void setDeath(boolean isDeath) {
		this.isDeath = isDeath;
	}

}
