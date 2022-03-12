package map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;

import gameManager.GameObject;
import inter_face.Game;
import mvc.Model;
// tạo background bằng cách đọc file

public class BackGroundMap extends GameObject {
	private BufferedImage imageIntro, mapIntro;

	public BackGroundMap(int x, int y, Model game) throws IOException {
		super(x, y, game);
		imageIntro = ImageIO.read(new File("data/Map1.png"));
		mapIntro = imageIntro.getSubimage(0, 189, 1560, 168);

	}

	public void draw(Graphics2D g2) {
		g2.drawImage(mapIntro, (int) (getX() - getGame().getCamera().getX()),
				(int) (getY() - getGame().getCamera().getY()), (int) (mapIntro.getWidth() * 2.6),
				(int) (mapIntro.getHeight() * 2.6), null);

	}

	@Override
	public void update(Observable o, Object arg) {
		
	}

}
