package figure_Main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import animation_Image.Animation;
import gameManager.Figure;
import inter_face.Game;

import mvc.Model;

public class Hercules extends Figure implements  Game {

	public static final int DIRECTION_LEFT = 0;
	public static final int DIRECTION_RIGHT = 1;
	private Animation runLeft, runRight, jumLeft, jumRight, tkLeft, tkRight, punchRight, punchLeft, swordLeft,
			swordRight, idleRight, idleLeft, sitDownLeft, sitDownRight, deathLeft, deathRight;
	private boolean isJump, isPunch, isCheckPunch, isPunchBreak, isSword, isReadyStandUp, isSitDown, isidle,
			isBearAttack, isDeath, isEndGame;
	private double hp = 200;
	private Rectangle rec = rec = new Rectangle((int) (0), (int) (0), 0, 0);
	private Observable obs;

	public Hercules(int x, int y, Model game, int width, int height, int speedX, int speedY, int dir, float mass,
			Observable obs) {
		super(x, y, game, width, height, speedX, speedY, dir, mass);
		AnimationHercules();
		mass = 0.1f;
		obs.addObserver(this);
	}
	// đọc animation từ file txt
	public void AnimationHercules() {
		runLeft = new Animation(getGame().getInputData().getListAnimation().get("herculesRun"));
		runLeft.daoNguoc();
		runRight = new Animation(getGame().getInputData().getListAnimation().get("herculesRun"));
		jumLeft = new Animation(getGame().getInputData().getListAnimation().get("herculesJump"));
		jumLeft.daoNguoc();
		jumRight = new Animation(getGame().getInputData().getListAnimation().get("herculesJump"));
		tkLeft = new Animation(getGame().getInputData().getListAnimation().get("herculesJump"));
		tkLeft.daoNguoc();
		tkRight = new Animation(getGame().getInputData().getListAnimation().get("herculesJump"));
		punchLeft = new Animation(getGame().getInputData().getListAnimation().get("herculesPunch"));
		punchLeft.daoNguoc();
		punchRight = new Animation(getGame().getInputData().getListAnimation().get("herculesPunch"));
		swordLeft = new Animation(getGame().getInputData().getListAnimation().get("herculesSword"));
		swordLeft.daoNguoc();
		swordRight = new Animation(getGame().getInputData().getListAnimation().get("herculesSword"));
		idleLeft = new Animation(getGame().getInputData().getListAnimation().get("herculesIdle"));
		idleLeft.daoNguoc();
		idleRight = new Animation(getGame().getInputData().getListAnimation().get("herculesIdle"));
		sitDownLeft = new Animation(getGame().getInputData().getListAnimation().get("herculesSitdown"));
		sitDownLeft.daoNguoc();
		sitDownRight = new Animation(getGame().getInputData().getListAnimation().get("herculesSitdown"));
		deathLeft = new Animation(getGame().getInputData().getListAnimation().get("herculesDeath"));
		deathLeft.daoNguoc();
		deathRight = new Animation(getGame().getInputData().getListAnimation().get("herculesDeath"));
		tkLeft.getIgnoreImage().set(0, true);
		tkLeft.getIgnoreImage().set(1, true);
		tkLeft.getIgnoreImage().set(2, true);
		tkLeft.getIgnoreImage().set(3, true);
		tkLeft.getIgnoreImage().set(4, true);
		tkLeft.getIgnoreImage().set(5, true);
		tkRight.getIgnoreImage().set(0, true);
		tkRight.getIgnoreImage().set(1, true);
		tkRight.getIgnoreImage().set(2, true);
		tkRight.getIgnoreImage().set(3, true);
		tkRight.getIgnoreImage().set(4, true);
		tkRight.getIgnoreImage().set(5, true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Model) {
			super.update();
			// hercules chạm đất thì cho isJum = false
			if (getSpeedY() == 0) {
				if (getDir() == DIRECTION_LEFT) {
					if (jumLeft.isLastFrame())
						isJump = false;
				} else {
					if (jumRight.isLastFrame())
						isJump = false;
				}
			}
			// chạm đất thì isReadyStandUp = true
			if (!isReadyStandUp) {
				if (getDir() == DIRECTION_LEFT) {
					if (tkLeft.isLastFrame())
						isReadyStandUp = true;
				} else {
					if (tkRight.isLastFrame())
						isReadyStandUp = true;
				}
			}
			// xử lý chém
			if (isSword) {
				
				if (getDir() == DIRECTION_LEFT) {
					// tạo rectangle để sử lý va chạm
					rec = new Rectangle((int) getX() - 59 * 2, (int) getY(), getWidth(), getHeight());
					if (swordLeft.isLastFrame()) {
						isSword = false;
						// chém đến frame cuối thì cho rec mất để k va chạm
						rec = new Rectangle((int) (0), (int) (0), 0, 0);
					}
					if (!isSword)
						swordLeft.setCurrentFrame(0);

				} else {
					rec = new Rectangle((int) getX() + 49, (int) getY(), getWidth(), getHeight());
					if (swordRight.isLastFrame()) {

						isSword = false;
						rec = new Rectangle((int) (0), (int) (0), 0, 0);
					}
					if (!isSword)
						swordRight.setCurrentFrame(0);
				}

			}
			// xử lý đấm
			if (isCheckPunch) {
				if (getDir() == DIRECTION_LEFT) {
					punchLeft.setCurrentFrame(0);
				} else {
					punchRight.setCurrentFrame(0);
				}
			}
			// xử lý va chạm boss
			if (getDir() == 1) {
				if (getRectangleMain().intersects(getGame().getBossSiege().getRectangleXuLiVaCham())) {
					setX((int) getGame().getBossSiege().getRectangleXuLiVaCham().getX() - getWidth() + 1);
					setSpeedX(0);
				}
			}

			// khi bi boss danh mat 100 - 1.5 mau
			if (getRectangleMain().intersects(getGame().getBossSiege().getVaChamVuKhi()))
				hp = hp - 1.5;
			// khi va cham creep mat 100 - 0.3 mau
			for (int i = 0; i < getGame().getListObjectFigure().getSize(); i++) {
				if (getRectangleVaChamEnemies().intersects(getGame().getListObjectFigure().get(i).getRectangleMain())
						&& getGame().getListObjectFigure().get(i).getLife() != 5)
					hp = hp - 0.3;

			}

			// mau = 0 thi chet
			if (hp <= 0) {
				isDeath = true;
			}
			// xử lý khi bị giết
			if (isDeath) {
				if (getDir() == DIRECTION_LEFT) {
					if (deathLeft.isLastFrame()) {
						isEndGame = true;
						deathLeft.setCurrentFrame(18);
					}
				} else if (deathRight.isLastFrame()) {
					isEndGame = true;
					deathRight.setCurrentFrame(18);
				}
			}
		}
	}

	public void draw(Graphics2D g2) {
		// xu ly nhay
		if (isJump) {
			if (getDir() == 0) {
				jumRight.upload(System.nanoTime());
				jumLeft.setCurrentFrame(jumRight.getCurrentFrame());
				jumLeft.draw(g2, (int) (getX() - getGame().getCamera().getX()),
						(int) (getY() - 70 - getGame().getCamera().getY()));
			} else if (getDir() == 1) {
				jumRight.upload(System.nanoTime());
				jumRight.draw(g2, (int) (getX() - getGame().getCamera().getX()),
						(int) (getY() - 70 - getGame().getCamera().getY()));
			}
			// xu ly roi tu do khi moi bat dau game
		} else if (!isJump && !isReadyStandUp) {
			if (getDir() == 0) {
				tkRight.upload(System.nanoTime());
				tkLeft.setCurrentFrame(tkRight.getCurrentFrame());
				tkLeft.draw(g2, (int) (getX() - getGame().getCamera().getX()),
						(int) (getY() - 70 - getGame().getCamera().getY()));
			} else if (getDir() == 1) {
				tkRight.upload(System.nanoTime());
				tkRight.draw(g2, (int) (getX() - getGame().getCamera().getX()),
						(int) (getY() - 70 - getGame().getCamera().getY()));
			}
			// xu ly chay
		} else if (isReadyStandUp) {
			// khi dung yen
			if (getSpeedX() == 0 && getSpeedY() == 0) {
				// chay ben trai
				if (getDir() == 0) {
					// xu ly chuyen dong khi dung yen ben trai
					if (!isidle && !isSword) {
						idleLeft.upload(System.nanoTime());
						idleLeft.draw(g2, (int) (getX() - getGame().getCamera().getX()),
								(int) (getY() + 10 - getGame().getCamera().getY()));
					}

					// xu ly dam tay ben trai
					if (isPunch) {

						punchLeft.upload(System.nanoTime());
						punchLeft.draw(g2, (int) (getX() - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));

						if (punchLeft.getCurrentFrame() == 2) {
							punchLeft.getIgnoreImage().set(0, true);
							punchLeft.getIgnoreImage().set(1, true);

						}
					}
					// xu ly sword ben trai
					if (isSword) {
						swordLeft.upload(System.nanoTime());
						if (swordLeft.getCurrentFrame() == 1) {
							swordLeft.draw(g2, (int) (getX() - 118 - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));
						} else if (swordLeft.getCurrentFrame() == 2) {
							swordLeft.draw(g2, (int) (getX() - 60 - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));
						} else if (swordLeft.getCurrentFrame() == 3) {
							swordLeft.draw(g2, (int) (getX() + 2 - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));
						} else if (swordLeft.getCurrentFrame() == 4) {
							swordLeft.draw(g2, (int) (getX() + 6 - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));
						} else if (swordLeft.getCurrentFrame() == 5) {
							swordLeft.draw(g2, (int) (getX() + 5 - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));
						} else if (swordLeft.getCurrentFrame() == 6) {
							swordLeft.draw(g2, (int) (getX() + 4 - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));
						} else if (swordLeft.getCurrentFrame() == 7) {
							swordLeft.draw(g2, (int) (getX() + 14 - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));
						} else if (swordLeft.getCurrentFrame() == 8) {
							swordLeft.draw(g2, (int) (getX() + 14 - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));
						} else if (swordLeft.getCurrentFrame() == 9) {
							swordLeft.draw(g2, (int) (getX() + 6 - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));
						} else {
							swordLeft.draw(g2, (int) (getX() - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));

						}
					}
					// ngoi
					if (isSitDown) {
						sitDownLeft.upload(System.nanoTime());
						sitDownLeft.draw(g2, (int) (getX() - getGame().getCamera().getX()),
								(int) (getY() + 50 - getGame().getCamera().getY()));

					}
					// chet
					if (isDeath) {
						deathLeft.upload(System.nanoTime());
						if (deathLeft.getCurrentFrame() == 1) {
							deathLeft.draw(g2, (int) (getX() + 20 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 2) {
							deathLeft.draw(g2, (int) (getX() + 32 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 3) {
							deathLeft.draw(g2, (int) (getX() + 34 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 4) {
							deathLeft.draw(g2, (int) (getX() + 36 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 5) {
							deathLeft.draw(g2, (int) (getX() + 30 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 6) {
							deathLeft.draw(g2, (int) (getX() + 30 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 7) {
							deathLeft.draw(g2, (int) (getX() + 30 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 8) {
							deathLeft.draw(g2, (int) (getX() + 34 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 9) {
							deathLeft.draw(g2, (int) (getX() + 34 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 10) {
							deathLeft.draw(g2, (int) (getX() + 34 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 11) {
							deathLeft.draw(g2, (int) (getX() + 34 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 12) {
							deathLeft.draw(g2, (int) (getX() + 32 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 13) {
							deathLeft.draw(g2, (int) (getX() + 36 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 14) {
							deathLeft.draw(g2, (int) (getX() + 30 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 15) {
							deathLeft.draw(g2, (int) (getX() - 8 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 16) {
							deathLeft.draw(g2, (int) (getX() - 28 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 17) {
							deathLeft.draw(g2, (int) (getX() - 64 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else if (deathLeft.getCurrentFrame() == 18) {
							deathLeft.draw(g2, (int) (getX() - 38 - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						} else
							deathLeft.draw(g2, (int) (getX() - getGame().getCamera().getX()),
									(int) (getY() - getGame().getCamera().getY()));
						isidle = true;
						isPunch = false;
					}
					// chay ben phai
				} else if (getDir() == 1) {
					// xu ly chuyen dong khi dung yen ben phai
					if (!isidle && !isSword) {
						idleRight.upload(System.nanoTime());
						idleRight.draw(g2, (int) (getX() - getGame().getCamera().getX()),
								(int) (getY() + 10 - getGame().getCamera().getY()));
					}
					// danh ly dam tay ben phai
					if (isPunch) {

						punchRight.upload(System.nanoTime());
						punchRight.draw(g2, (int) (getX() - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
						if (punchRight.getCurrentFrame() == 2) {
							punchRight.getIgnoreImage().set(0, true);
							punchRight.getIgnoreImage().set(1, true);
						}

					}
					// xu ly sword ben phai
					if (isSword) {
						swordRight.upload(System.nanoTime());

						if (swordRight.getCurrentFrame() == 4) {
							swordRight.draw(g2, (int) (getX() - 38 - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));
						} else if (swordRight.getCurrentFrame() == 5) {
							swordRight.draw(g2, (int) (getX() - 39 - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));
						} else
							swordRight.draw(g2, (int) (getX() - getGame().getCamera().getX()),
									(int) (getY() - 40 - getGame().getCamera().getY()));

					}
					// ngoi
					if (isSitDown) {
						sitDownRight.upload(System.nanoTime());
						sitDownRight.draw(g2, (int) (getX() - getGame().getCamera().getX()),
								(int) (getY() + 50 - getGame().getCamera().getY()));
					}
					// chet
					if (isDeath) {
						deathRight.upload(System.nanoTime());
						deathRight.draw(g2, (int) (getX() - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
						isidle = true;
						isPunch = false;
					}
				}

				// khi khong dung yen
			} else {
				if (getDir() == 0) {
					runLeft.upload(System.nanoTime());
					if (runLeft.getCurrentFrame() == 2) {
						runLeft.getIgnoreImage().set(0, true);
						runLeft.getIgnoreImage().set(1, true);
					}
					if (runLeft.getCurrentFrame() == 13) {
						runLeft.getIgnoreImage().set(14, true);
						runLeft.getIgnoreImage().set(15, true);
						runLeft.getIgnoreImage().set(16, true);
						runLeft.getIgnoreImage().set(17, true);
						runLeft.getIgnoreImage().set(18, true);

					}
					if (runLeft.getCurrentFrame() == 0) {
						runLeft.draw(g2, (int) (getX() - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 1) {
						runLeft.draw(g2, (int) (getX() + 6 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 2) {
						runLeft.draw(g2, (int) (getX() - 12 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 3) {
						runLeft.draw(g2, (int) (getX() - 26 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 4) {
						runLeft.draw(g2, (int) (getX() - 20 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 5) {
						runLeft.draw(g2, (int) (getX() - 24 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 6) {
						runLeft.draw(g2, (int) (getX() - 24 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 7) {
						runLeft.draw(g2, (int) (getX() - 6 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 8) {
						runLeft.draw(g2, (int) (getX() - 2 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 9) {
						runLeft.draw(g2, (int) (getX() - 28 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 10) {
						runLeft.draw(g2, (int) (getX() - 20 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 11) {
						runLeft.draw(g2, (int) (getX() - 22 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 12) {
						runLeft.draw(g2, (int) (getX() - 36 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 13) {
						runLeft.draw(g2, (int) (getX() - 20 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 14) {
						runLeft.draw(g2, (int) (getX() + 18 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 15) {
						runLeft.draw(g2, (int) (getX() - 2 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 16) {
						runLeft.draw(g2, (int) (getX() - 4 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 17) {
						runLeft.draw(g2, (int) (getX() - 4 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					} else if (runLeft.getCurrentFrame() == 18) {
						runLeft.draw(g2, (int) (getX() - 6 - getGame().getCamera().getX()),
								(int) (getY() - getGame().getCamera().getY()));
					}

				} else if (getDir() == 1) {
					runRight.upload(System.nanoTime());
					runRight.draw(g2, (int) (getX() - getGame().getCamera().getX()),
							(int) (getY() - getGame().getCamera().getY()));
					if (runRight.getCurrentFrame() == 2) {
						runRight.getIgnoreImage().set(0, true);
						runRight.getIgnoreImage().set(1, true);
					}
					if (runLeft.getCurrentFrame() == 13) {
						runLeft.getIgnoreImage().set(14, true);
						runLeft.getIgnoreImage().set(15, true);
						runLeft.getIgnoreImage().set(16, true);
						runLeft.getIgnoreImage().set(17, true);
						runLeft.getIgnoreImage().set(18, true);

					}

				}

			}
		}

	}

	// nhay
	public void jump() {
		if (!isJump) {
			setSpeedY(-4);
			isJump = true;
		}
	}

	// dam
	public void punch() {
		if (!isPunch) {
			isPunch = true;
		}

	}

	// ngoi xuong
	public void sitDown() {
		if (!isSitDown) {
			isSitDown = true;
		}
	}

	// chem
	public void sword() {
		if (!isSword) {
			isSword = true;
		}

	}

	public boolean isJump() {
		return isJump;
	}

	public void setJump(boolean isJump) {
		this.isJump = isJump;
	}

	public boolean isReadyStandUp() {
		return isReadyStandUp;
	}

	public void setReadyStandUp(boolean isReadyStandUp) {
		this.isReadyStandUp = isReadyStandUp;
	}

	public boolean isPunch() {
		return isPunch;
	}

	public void setPunch(boolean isPunch) {
		this.isPunch = isPunch;
	}

	public boolean isSword() {
		return isSword;
	}

	public void setSword(boolean isSword) {
		this.isSword = isSword;
	}

	public boolean isPunchBreak() {
		return isPunchBreak;
	}

	public void setPunchBreak(boolean isPunchBreak) {
		this.isPunchBreak = isPunchBreak;
	}

	public boolean isSitDown() {
		return isSitDown;
	}

	public void setSitDown(boolean isSitDown) {
		this.isSitDown = isSitDown;
	}

	public boolean isIsidle() {
		return isidle;
	}

	public void setIsidle(boolean isidle) {
		this.isidle = isidle;
	}

	public boolean isCheckPunch() {
		return isCheckPunch;
	}

	public void setCheckPunch(boolean isCheckPunch) {
		this.isCheckPunch = isCheckPunch;
	}

	public Rectangle getRectangleXuLiVaChamSword() {
		return rec;
	}

	public Rectangle getRectangleVaChamEnemies() {
		return new Rectangle((int) (getX()), (int) (getY()), (int) (getWidth() - 20), getHeight());
	}

	public boolean isBearAttack() {
		return isBearAttack;
	}

	public void setBearAttack(boolean isBearAttack) {
		this.isBearAttack = isBearAttack;
	}

	public boolean isDeath() {
		return isDeath;
	}

	public void setDeath(boolean isDeath) {
		this.isDeath = isDeath;
	}

	public boolean isEndGame() {
		return isEndGame;
	}

	public void setEndGame(boolean isEndGame) {
		this.isEndGame = isEndGame;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

}
