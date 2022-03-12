package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mvc.View;

public class DisplayEndGame extends JFrame {
	public static final int SCREEN_WIDTH = 300;
	public static final int SCREEN_HEIGHT = 300;
	private JButton jbtChoiLai, jbtExit;
	private JLabel jlbTitle, piclb;

	public DisplayEndGame(View view) {
		setLayout(null);
		if (view.getGame().getBossSiege().isEndGame()) {
			add(jlbTitle = new JLabel("Chúc mừng bạn đã chiến thắng!"));
			jlbTitle.setBounds(30, 10, 250, 30);
			jlbTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
			jlbTitle.setForeground(Color.WHITE);
		} else if (view.getGame().getHercules().isEndGame()) {
			add(jlbTitle = new JLabel("Bạn đã thất bại!"));
			jlbTitle.setBounds(90, 10, 150, 30);
			jlbTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
			jlbTitle.setForeground(Color.WHITE);
		}
		add(jbtChoiLai = new JButton("Chơi lại"));
		jbtChoiLai.setBounds(85, 70, 130, 70);
		jbtChoiLai.setContentAreaFilled(false);
		jbtChoiLai.setFocusPainted(false);
		jbtChoiLai.setForeground(Color.WHITE);
		jbtChoiLai.setFont(new Font("Tahoma", Font.BOLD, 20));

		add(jbtExit = new JButton("Thoát"));
		jbtExit.setBounds(85, 170, 130, 70);
		jbtExit.setContentAreaFilled(false);
		jbtExit.setFocusPainted(false);
		jbtExit.setForeground(Color.WHITE);
		jbtExit.setFont(new Font("Tahoma", Font.BOLD, 20));

		ImageIcon img = new ImageIcon("data/viewEnd.jpg");
		piclb = new JLabel("", img, JLabel.CENTER);
		piclb.setBounds(0, 0, 300, 300);
		add(piclb);

		Toolkit toolKit = this.getToolkit();
		Dimension dimension = toolKit.getScreenSize();
		this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WIDTH,
				SCREEN_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		jbtChoiLai.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					new GameFrame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
				view.getGameF().dispose();
			}
		});
		jbtExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
}
