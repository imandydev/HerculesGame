package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import mvc.Control;
import mvc.View;

public class GameFrame extends JFrame {
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 600;

	private View panel;
	private DisplayStartGame viewIntro;

	public GameFrame() throws IOException {
		Toolkit toolKit = this.getToolkit();
		Dimension dimension = toolKit.getScreenSize();
		add(panel = new View(this));
		addKeyListener(new Control(panel.getGame()));
		this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WIDTH,
				SCREEN_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}
}
