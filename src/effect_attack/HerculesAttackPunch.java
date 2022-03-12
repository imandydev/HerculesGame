package effectAttack;

import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import animation_Image.Animation;
import gameManager.Figure;
import inter_face.Game;

import mvc.Model;

public class HerculesAttackPunch extends Figure implements Game {
	private Animation attackLeft, attackRight;
	private boolean isAttack, isLocation, isStopAttack;
	private float timeStartShooting;
	private Observable obs;

	public HerculesAttackPunch(int x, int y, Model game, int width, int height, int speedX, int speedY, int dir,
			float mass, Observable obs) {
		super(x, y, game, width, height, speedX, speedY, dir, mass);
		animationAttack();
		obs.addObserver(this);
	}

	public void animationAttack() {
		attackLeft = new Animation(getGame().getInputData().getListAnimation().get("herculesAttackGreen"));
		attackLeft.daoNguoc();
		attackRight = new Animation(getGame().getInputData().getListAnimation().get("herculesAttackGreen"));

	}

	public void update(Observable o, Object arg) {
		if (o instanceof Model) {
			super.update();
			if (getGame().getHercules().getDir() == 0) {
				if (!isAttack) {
					setDir(0);
				}
			} else if (!isAttack) {
				setDir(1);
			}

			if (isAttack) {
				run();

			}

			if (!isLocation) {
				setX((int) getGame().getHercules().getX());
				setY((int) getGame().getHercules().getY());
			}
			
			for (int i = 0; i < getGame().getListObjectFigure().getSize(); i++) {
				if (getRectangleMain().intersects(getGame().getListObjectFigure().get(i).getRectangleMain())
						|| isTouch()) {
					isAttack = false;
					isLocation = false;
					setTouch(false);
				}

			}

			if (getRectangleMain().intersects(getGame().getBossSiege().getRectangleMain())) {
				isAttack = false;
				isLocation = false;

			}

			if (isAttack) {
				if (getDir() == 0) {
					if (getGame().getHercules().getX() - getX() > 300) {
						isAttack = false;
						isLocation = false;
					}
				} else {
					if (getX() - getGame().getHercules().getX() > 300) {
						isAttack = false;
						isLocation = false;
					}
				}
			}
		}
	
	}

	public void draw(Graphics2D g2) {

		if (isAttack) {

			if (getDir() == 0) {

				attackLeft.upload(System.nanoTime());
				attackLeft.drawAttackRed(g2, (int) (getX() - getGame().getCamera().getX() - 60),
						(int) (getY() - getGame().getCamera().getY() + 30));
				if (attackLeft.getCurrentFrame() == 2) {
					attackLeft.getIgnoreImage().set(0, true);
					attackLeft.getIgnoreImage().set(1, true);

				}

				isLocation = true;

			} else if (getDir() == 1) {

				attackRight.upload(System.nanoTime());
				attackRight.drawAttackRed(g2, (int) (getX() - getGame().getCamera().getX()) + 100,
						(int) (getY() - getGame().getCamera().getY() + 30));
				if (attackRight.getCurrentFrame() == 2) {
					attackRight.getIgnoreImage().set(0, true);
					attackRight.getIgnoreImage().set(1, true);

				}

				isLocation = true;

			}
		}

	}

	public void run() {
		if (getDir() == 0) {
			setSpeedX(-7);
		} else
			setSpeedX(+7);
	}

	public boolean isAttack() {
		return isAttack;
	}

	public void setAttack(boolean isAttack) {
		this.isAttack = isAttack;
	}

	public boolean isStopAttack() {
		return isStopAttack;
	}

	public void setStopAttack(boolean isStopAttack) {
		this.isStopAttack = isStopAttack;
	}

}
