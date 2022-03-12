package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Observable;

import gameManager.GameObject;
import inter_face.Game;
import mvc.Model;

public class PhysicalMap extends GameObject {

	private int[][] array;
	private int size = 30;

	public PhysicalMap(int x, int y, Model game) throws IOException {
		super(x, y, game);
		array = game.getInputData().getPhysicalMap();

	}

	// xử lý va chạm bên dưới
	public Rectangle getRectangleBottom(Rectangle rect) {

		int xStart = (rect.x / size) - 2;
		int xEnd = ((rect.x + rect.width) / size) + 2;
		int yStart = (rect.y / size) - 2;
		int yEnd = ((rect.y + rect.height) / size) + 2;
		if (xStart < 0)
			xStart = 0;
		if (xEnd > array[0].length - 1)
			xEnd = array[0].length - 1;
		if (yStart < 0)
			yStart = 0;
		if (yStart > array.length - 1)
			yEnd = array.length - 1;
		for (int i = yStart; i <= yEnd; i++) {
			for (int j = xStart; j <= xEnd; j++) {
				if (array[i][j] > 0) {
					Rectangle r = new Rectangle(j * size, i * size, size, size);
					if (r.intersects(rect))
						return r;
				}
			}
		}

		return null;
	}

	// xử lý va chạm bên trái
	public Rectangle getRectangleLeft(Rectangle rect) {

		int xStart = (rect.x / size);
		int xEnd = xStart - 2;
		int yStart = (rect.y / size) - 2;
		int yEnd = ((rect.y + rect.height) / size) + 2;
		if (xEnd < 0)
			xEnd = 0;
		if (yStart < 0) {
			yStart = 0;
		}
		if (xEnd > array[0].length - 1) {
			xEnd = array[0].length - 1;
		}
		while (yStart * size <= rect.y)
			yStart++;
		while (yEnd * size >= rect.y + rect.height)
			yEnd--;
		for (int i = xStart; i > xEnd; i--) {
			for (int j = yStart; j < yEnd; j++) {
				if (array[j][i] > 0) {
					Rectangle r = new Rectangle(i * size, j * size, size, size);
					if (r.intersects(rect))
						return r;
				}
			}
		}

		return null;
	}

	// xử lý va chạm bên phải
	public Rectangle getRectangleRight(Rectangle rect) {

		int xStart = (rect.x + rect.width) / size;
		int xEnd = xStart + 2;
		int yStart = (rect.y / size) - 2;
		int yEnd = ((rect.y + rect.height) / size) + 2;
		if (xEnd < 0)
			xEnd = 0;
		if (yStart < 0) {
			yStart = 0;
		}

		if (xEnd > array[0].length - 1) {
			xEnd = array[0].length - 1;
		}
		while (yStart * size <= rect.y)
			yStart++;
		while (yEnd * size >= rect.y + rect.height)
			yEnd--;
		for (int i = xStart; i < xEnd; i++) {
			for (int j = yStart; j < yEnd; j++) {
				if (array[j][i] > 0) {
					Rectangle r = new Rectangle(i * size, j * size, size, size);
					if (r.intersects(rect))
						return r;
				}
			}
		}

		return null;
	}

	// xử lý va chạm bên trên
	public Rectangle getRectangleUp(Rectangle rect) {

		int xStart = rect.x / size;
		int xEnd = (rect.x + rect.width) / size;
		int yStart = (rect.y / size) - 2;
		int yEnd = rect.y / size;
		if (xEnd < 0)
			xEnd = 0;
		if (yStart < 0)
			yStart = 0;
		if (xEnd > array[0].length - 1) {
			xEnd = array[0].length - 1;
		}
		for (int i = xStart; i < xEnd; i++) {
			for (int j = yEnd; j >= yStart; j--) {
				if (array[j][i] == 1) {
					Rectangle r = new Rectangle(i * size, j * size, size, size);
					if (r.intersects(rect))
						return r;
				}
			}
		}

		return null;
	}

	// vẽ

	public void draw(Graphics2D g2) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] == 1) {
					g2.setColor(Color.BLACK);
					g2.fillRect((int) (j * 30 - getGame().getCamera().getX()),
							(int) (i * 30 - getGame().getCamera().getY()), 30, 30);
				}
			}
		}
	}

	public int[][] getArray() {
		return array;
	}

	public void setArray(int[][] array) {
		this.array = array;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
