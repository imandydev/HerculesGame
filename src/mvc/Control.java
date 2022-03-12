package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import figure_Main.Hercules;

public class Control implements KeyListener {
	private boolean isCheckRun;
	private Model game;

	public Control(Model gameM) {
		game = gameM;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			if (!game.getHercules().isJump() && !game.getHercules().isSword() && !game.getHercules().isDeath()) {
				game.getHercules().setSpeedX(-4);
				game.getHercules().setDir(Hercules.DIRECTION_LEFT);
				isCheckRun = true;
			} else {
				if (game.getHercules().getSpeedY() != 0) {
					game.getHercules().setSpeedX(-3);
					game.getHercules().setDir(Hercules.DIRECTION_LEFT);
				} else {
					game.getHercules().setSpeedX(0);
				}
			}

		} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (!game.getHercules().isJump() && !game.getHercules().isSword() && !game.getHercules().isDeath()) {
				game.getHercules().setSpeedX(+4);
				game.getHercules().setDir(Hercules.DIRECTION_RIGHT);
				isCheckRun = true;
			} else {
				if (game.getHercules().getSpeedY() != 0) {
					game.getHercules().setSpeedX(+3);
					game.getHercules().setDir(Hercules.DIRECTION_RIGHT);

				} else {
					game.getHercules().setSpeedX(0);
				}
			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			if (!game.getHercules().isPunch() && !game.getHercules().isSword() && !game.getHercules().isDeath()) {
				game.getHercules().jump();
			}

		} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			if (!game.getHercules().isPunch() && !game.getHercules().isSword() && !game.getHercules().isDeath()) {
				game.getHercules().sitDown();
				game.getHercules().setIsidle(true);
			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		} else if (arg0.getKeyCode() == KeyEvent.VK_D) {
			if (game.getHercules().isReadyStandUp() && !game.getHercules().isJump() && !game.getHercules().isSword()
					&& !game.getHercules().isSitDown() && !game.getHercules().isDeath()) {
				game.getHercules().punch();
				game.getHercules().setCheckPunch(false);
				game.getHercules().setIsidle(true);
				if (!isCheckRun) {
					game.getEffectAttack().setAttack(true);
				}

			}

		} else if (arg0.getKeyCode() == KeyEvent.VK_X) {
			if (game.getHercules().isReadyStandUp() && !game.getHercules().isJump() && !game.getHercules().isSword()
					&& !game.getHercules().isSitDown() && !game.getHercules().isDeath()) {
				game.getHercules().sword();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			game.getHercules().setSpeedX(0);
			isCheckRun = false;
		} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			game.getHercules().setSpeedX(0);
			isCheckRun = false;
		} else if (arg0.getKeyCode() == KeyEvent.VK_UP) {

		} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			game.getHercules().setSitDown(false);
			game.getHercules().setIsidle(false);
		} else if (arg0.getKeyCode() == KeyEvent.VK_D) {
			game.getHercules().setPunch(false);
			game.getHercules().setCheckPunch(true);
			game.getHercules().setIsidle(false);

		}

	}

}
