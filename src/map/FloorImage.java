package map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Observable;

import gameManager.GameObject;
import inter_face.Game;
import mvc.Model;

public class FloorImage extends GameObject {
		private BufferedImage image;
	
		public FloorImage(int x, int y, Model game, BufferedImage image) {
			super(x, y, game);
			this.image = image;
		}

	public void draw(Graphics2D g2) {
		g2.drawImage(image, (int) (getX() - getGame().getCamera().getX()),
				(int) (getY() - getGame().getCamera().getY()), getWidth() * 3, getHeight() * 3, null);
	}

	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}

}
