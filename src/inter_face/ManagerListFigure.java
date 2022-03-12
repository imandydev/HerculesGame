package inter_face;

import java.awt.Graphics2D;

import gameManager.Figure;

public interface ManagerListFigure {
	void draw(Graphics2D g2);
	void addObject(Figure object);
	void removeObject(Figure object);

}
