package loadData;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import animation_Image.Animation;
import animation_Image.FrameImage;
import inter_face.ManagerInput;
import inter_face.ManagerListFigure;
import inter_face.Game;

public class InputData implements ManagerInput{

	private HashMap<String, Animation> animationHashMap;
	private HashMap<String, FrameImage> listImage;

	public InputData() {
		animationHashMap = new HashMap<String, Animation>();
		listImage = new HashMap<String, FrameImage>();
	}

	// đọc ảnh vào bằng
	public void loadImage() throws IOException {
		BufferedImage hercules = ImageIO.read(new File("data/hercules.png"));
		BufferedImage broozer = ImageIO.read(new File("data/broozer.png"));
		BufferedImage effectAttack = ImageIO.read(new File("data/effectattack.png"));
		BufferedImage background = ImageIO.read(new File("data/Map1.png"));
		BufferedImage bossSiege = ImageIO.read(new File("data/BossMap1.png"));
		BufferedReader read = new BufferedReader(new FileReader(new File("data/figurehercules.txt")));
		String line = null;

		while (true) {
			line = read.readLine();
			if (line == null)
				break;
			line = line.trim();
			String[] arrayLine = line.split(" ");
			if (arrayLine[0].equals("hercules")) {
				BufferedImage img = hercules.getSubimage(Integer.parseInt(arrayLine[2]), Integer.parseInt(arrayLine[3]),
						Integer.parseInt(arrayLine[4]), Integer.parseInt(arrayLine[5]));
				listImage.put(arrayLine[1], new FrameImage(img, arrayLine[1]));
			} else if (arrayLine[0].equals("broozer")) {
				BufferedImage img = broozer.getSubimage(Integer.parseInt(arrayLine[2]), Integer.parseInt(arrayLine[3]),
						Integer.parseInt(arrayLine[4]), Integer.parseInt(arrayLine[5]));
				listImage.put(arrayLine[1], new FrameImage(img, arrayLine[1]));
			} else if (arrayLine[0].equals("effect")) {
				BufferedImage img = effectAttack.getSubimage(Integer.parseInt(arrayLine[2]),
						Integer.parseInt(arrayLine[3]), Integer.parseInt(arrayLine[4]), Integer.parseInt(arrayLine[5]));
				listImage.put(arrayLine[1], new FrameImage(img, arrayLine[1]));
			} else if (arrayLine[0].equals("background")) {
				BufferedImage img = background.getSubimage(Integer.parseInt(arrayLine[2]),
						Integer.parseInt(arrayLine[3]), Integer.parseInt(arrayLine[4]), Integer.parseInt(arrayLine[5]));
				listImage.put(arrayLine[1], new FrameImage(img, arrayLine[1]));
			} else if (arrayLine[0].equals("bossmap1")) {
				BufferedImage img = bossSiege.getSubimage(Integer.parseInt(arrayLine[2]),
						Integer.parseInt(arrayLine[3]), Integer.parseInt(arrayLine[4]), Integer.parseInt(arrayLine[5]));
				listImage.put(arrayLine[1], new FrameImage(img, arrayLine[1]));
			}
		}
		read.close();

	}

	// đọc animation vào
	public void loadAnimation() throws IOException {
		BufferedReader read = new BufferedReader(new FileReader(new File("data/animation.txt")));
		String line = null;
		while (true) {
			line = read.readLine();
			if (line == null)
				break;
			String[] arrayLine = line.split(" ");
			String nameAnimation = arrayLine[0];
			Animation animation = new Animation(nameAnimation);
			for (int i = 1; i < arrayLine.length - 1; i += 2) {
				animation.add(listImage.get(arrayLine[i]), Double.parseDouble(arrayLine[i + 1]));
			}
			animationHashMap.put(nameAnimation, animation);
		}
		read.close();

	}

	// đọc vào map bằng file text
	public int[][] getPhysicalMap() throws IOException {
		int[][] array = new int[25][139];
		BufferedReader r = new BufferedReader(new FileReader(new File("data/physicalMap.txt")));
		int row = 0;
		int c = 0;

		String line = null;

		while (true) {
			line = r.readLine();
			if (line == null)
				break;

			StringTokenizer s = new StringTokenizer(line);
			while (s.hasMoreTokens()) {
				array[row][c] = Integer.parseInt(s.nextToken());
				c++;
			}
			c = 0;
			row++;
		}

		r.close();

		return array;
	}

	public HashMap<String, Animation> getListAnimation() {
		return animationHashMap;
	}

	public void setListAnimation(HashMap<String, Animation> listAnimtion) {
		this.animationHashMap = listAnimtion;
	}

	public HashMap<String, FrameImage> getListImage() {
		return listImage;
	}

	public void setListImage(HashMap<String, FrameImage> listImage) {
		this.listImage = listImage;
	}

}
