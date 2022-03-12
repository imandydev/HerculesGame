package bosses;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import animation_Image.Animation;
import gameManager.Figure;
import inter_face.Game;
import mvc.Model;
// Boss
public class SiegeBosses extends Figure implements Game {
	private Animation moveLeft, moveRight, attackLeft, attackRight, bearDamageLeft, bearDamageRight, deathLeft,
			deathRight;
	private boolean isMove, isAttack, isDeath, isBearDamage, isLastDeath, isRun, isEndGame;
	private Rectangle rec = rec = new Rectangle((int) (0), (int) (0), 0, 0);
	private double hp = 200;
	private Observable obs;

	public SiegeBosses(int x, int y, Model game, int width, int height, int speedX, int speedY, int dir, float mass,
			Observable obs) {
		super(x, y, game, width, height, speedX, speedY, dir, mass);
		animationSiege();
		obs.addObserver(this);
	}
	// đọc animation trong file text
	public void animationSiege() {
		moveLeft = new Animation(getGame().getInputData().getListAnimation().get("siegeMove"));
		moveLeft.daoNguoc();
		attackLeft = new Animation(getGame().getInputData().getListAnimation().get("siegeAttack"));
		attackLeft.daoNguoc();

		deathLeft = new Animation(getGame().getInputData().getListAnimation().get("siegeDeath"));
		deathLeft.daoNguoc();

	}

	public void update(Observable o, Object arg) {
		if (o instanceof Model) {
			super.update();
			rec = new Rectangle((int) (0), (int) 0, 0, 0);
			// chạy theo hercules
			if (getX() > getGame().getHercules().getX()
					&& !getRectangleMain().intersects(getGame().getHercules().getRectangleMain())) {
				isMove = true;

			}
			// hercules cách boss dưới 500 thì boss di chuyển
			if (getX() - getGame().getHercules().getX() < 500) {
				run();
			}
			
			// va chạm thì dừng và thực hiện đánh
			if (getRectangleXuLiVaCham().intersects(getGame().getHercules().getRectangleMain())) {
				isAttack = true;
				isMove = false;
				setSpeedX(0);

			}
			
			// va chạm khi đánh, búa chạm hercules thì tạo rec để xét va chạm
			if (isAttack) {
				if (attackLeft.isLastFrame()) {
					rec = new Rectangle((int) (getX()), (int) getGame().getHercules().getY(), 104, 128);

				}
			}
			// khi boss chết thì end game
			if (isDeath) {
				if (deathLeft.isLastFrame()) {
					isEndGame = true;
				}
			}
			// trúng đạn -10 hp nếu chạm her thì dính đạn k bị sát thương
			if ((getRectangleMain().intersects(getGame().getEffectAttack().getRectangleMain())
					&& !getRectangleMain().intersects(getGame().getHercules().getRectangleMain()))) {
				hp = hp - 10;
				// bị chém  - hp
			} else if (getRectangleMain().intersects(getGame().getHercules().getRectangleXuLiVaChamSword())) {
				hp = hp - 0.5;
			}
			// hp < 0 thì chết và dừng di chuyển
			if (hp <= 0) {
				isDeath = true;
				isMove = false;
				isAttack = false;
				setSpeedX(0);
			}
		}
	}
	// vẽ
	public void draw(Graphics2D g2) {
		
		if (isMove) {
			moveLeft.upload(System.nanoTime());
			moveLeft.drawBossSiege(g2, (int) (getX() - getGame().getCamera().getX()),
					(int) (getY() - 180 - getGame().getCamera().getY()));
		} else if (!isMove && isAttack) {
			attackLeft.upload(System.nanoTime());
			attackLeft.drawBossSiege(g2, (int) (getX() - getGame().getCamera().getX()),
					(int) (getY() - 245 - getGame().getCamera().getY()));
		} else if (isDeath) {
			deathLeft.upload(System.nanoTime());
			deathLeft.drawBossSiege(g2, (int) (getX() - getGame().getCamera().getX()),
					(int) (getY() - 240 - getGame().getCamera().getY()));
		}

	}
	// di chuyển
	public void run() {
		setSpeedX(-3);

	}
	// xử lý va chạm với hercules
	public Rectangle getRectangleXuLiVaCham() {
		return new Rectangle((int) (getX() + 120), (int) (getY()), getWidth() - 200, getHeight());

	}

	public Rectangle getVaChamVuKhi() {
		return rec;
	}

	public boolean isEndGame() {
		return isEndGame;
	}

	public void setEndGame(boolean isEndGame) {
		this.isEndGame = isEndGame;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

}
