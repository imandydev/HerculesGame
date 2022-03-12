package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class DisplayStartGame extends JFrame {
	private JButton btnStart, btnExit, btnHelp;
	private JLabel piclb;
	public static final int SCREEN_WIDTH = 1015;
	public static final int SCREEN_HEIGHT = 600;

	public DisplayStartGame() throws IOException {
		setLayout(null);

		add(btnStart = new JButton("Chơi"));
		btnStart.setFont(new Font("Time new roman", Font.BOLD, 30));
		btnStart.setBounds(400, 210, 200, 60);
		btnStart.setContentAreaFilled(false);
		btnStart.setFocusPainted(false);

		btnStart.setForeground(Color.WHITE);

		add(btnExit = new JButton("Thoát"));
		btnExit.setFont(new Font("Time new roman", Font.BOLD, 30));
		btnExit.setBounds(400, 300, 200, 60);
		btnExit.setContentAreaFilled(false);
		btnExit.setFocusPainted(false);
		btnExit.setForeground(Color.WHITE);

		ImageIcon img = new ImageIcon("data/viewintro1.jpg");
		piclb = new JLabel("", img, JLabel.CENTER);
		piclb.setBounds(0, 0, 1000, 700);
		add(piclb);

		Toolkit toolKit = this.getToolkit();
		Dimension dimension = toolKit.getScreenSize();
		this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WIDTH,
				SCREEN_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new GameFrame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});

		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}

}
