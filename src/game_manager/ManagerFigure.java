package gameManager;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import inter_face.ManagerListFigure;
import inter_face.Game;
import mvc.Model;

// tạo 1 danh sách quái trong game, để tạo số lượng nhiều mà không cần phải tạo nhiều class
public class ManagerFigure implements Observer, ManagerListFigure {
	private List<Figure> listObject;
	private Observable obs;

	public ManagerFigure(Observable obs) {
		super();
		listObject = Collections.synchronizedList(new LinkedList<Figure>());
		obs.addObserver(this);
	}

	// thêm nhân vật vào list
	public void addObject(Figure object) {
		synchronized (listObject) {
			listObject.add(object);
		}
	}

	// xóa
	public void removeObject(Figure object) {
		listObject.remove(object);
	}

	// vẽ
	public void draw(Graphics2D g2) {
		synchronized (listObject) {
			for (int i = 0; i < listObject.size(); i++) {
				listObject.get(i).draw(g2);
			}
		}
	}

	public void update(Observable o, Object arg) {
		if (o instanceof Model) {
			for (int i = 0; i < listObject.size(); i++) {
				listObject.get(i).update();
				// nếu có nhân vật chết thì sau 7/giây sẽ bị biến mất
				if (listObject.get(i).getLife() == Figure.DEATH) {
					if (System.nanoTime() - listObject.get(i).getTimeStartDeath() > 700000000) {
						listObject.remove(i);
					}

				}
			}
		}
	}

	public int getSize() {
		return listObject.size();
	}

	public Figure get(int i) {
		return listObject.get(i);
	}

	
}
