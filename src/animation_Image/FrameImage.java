package animation_Image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FrameImage {

	private BufferedImage img;
	private String name;

	public FrameImage(BufferedImage img, String names) {
		this.img = img;
		this.name = names;
	}

	public FrameImage(FrameImage frameImage) {
		this.name = frameImage.getName();
		this.img = frameImage.getImg();
	}

	public void draw(Graphics2D g2, int x, int y) {
		g2.drawImage(img, x, y, null);
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
