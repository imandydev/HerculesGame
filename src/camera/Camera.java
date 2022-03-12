package camera;

import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import gameManager.GameObject;

import mvc.Model;

public class Camera extends GameObject {

	public Camera(int x, int y, Model game, Observable obs) {
		super(x, y, game);
		obs.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		if (o instanceof Model) {
			/*
			 * nếu hercules di chuyển đến tạo độ x = 450 (giữa màn hình) thì set tọa độ
			 * camera bằng tọa độ hiện tại của camera +4 (getX()+4), do hercules hercules di
			 * chuyển mỗi bước chạy +4 nên muốn khóa camera lại thì camera cũng +4 sau mỗi
			 * bước chạy của hercules.
			 */
			if (getGame().getHercules().getX() > 450 + getX()) {
				setX(getX() + 4);
			}
			// Ngược lại với ở trên
			if (getGame().getHercules().getX() < 450 + getX()) {
				setX(getX() - 4);
			}
			/*
			 * giữ cho x của camera không vượt ra khỏi map do background map được vẽ bắt đầu
			 * ở tọa độ x = 60 nên x tối thiểu của camera = 60
			 */
			if (getX() < 60) {
				setX(60);
				/*
				 * giữ cho x của camera không vượt ra khỏi map do map có chiều rộng = 3125
				 * nên x tối đa của camera = 3125
				 */
			} else if (getX() > 3125) {
				setX(3125);
			}
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub

	}

}
