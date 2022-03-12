package mvc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import figure_Main.Hercules;
import gui.DisplayEndGame;
import gui.GameFrame;

public class View extends JPanel implements Runnable {

	private BufferedImage mainImg;
	private Model game;
	private Thread thread;
	private JLabel hp;
	private JButton bt;

	GameFrame gameF;

	public View(GameFrame gamef2) throws IOException {
		mainImg = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		game = new Model();
		thread = new Thread(this);
		thread.start();
		gameF = gamef2;

	}

	@Override
	public void paint(Graphics arg0) {
		arg0.drawImage(mainImg, 0, 0, this);

		if ((int) game.getHercules().getHp() < 0) {
			arg0.setColor(Color.WHITE);
			arg0.setFont(new Font("Tahoma", Font.BOLD, 15));
			arg0.drawString("HP" + " " + 0 + "", 70, 50);
			arg0.drawString("Boss " + (int) game.getBossSiege().getHp() + " HP", 800, 50);
		} else {
			arg0.setColor(Color.WHITE);
			arg0.setFont(new Font("Tahoma", Font.BOLD, 15));
			arg0.drawString("Hercules " + (int) game.getHercules().getHp() + " HP", 70, 50);
			arg0.drawString("Boss " + (int) game.getBossSiege().getHp() + " HP", 800, 50);
		}
	}

	public void draw() {
		Graphics2D g2 = (Graphics2D) mainImg.getGraphics();
		game.draw(g2);

	}

	public void update() {
		game.update();
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (game.getHercules().isEndGame() || game.getBossSiege().isEndGame()) {
					new DisplayEndGame(this);
					thread.stop();
				}
				update();
				draw();
				repaint();
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Model getGame() {
		return game;
	}

	public void setGame(Model game) {
		this.game = game;
	}

	public GameFrame getGameF() {
		return gameF;
	}

	public void setGameF(GameFrame gameF) {
		this.gameF = gameF;
	}

}
