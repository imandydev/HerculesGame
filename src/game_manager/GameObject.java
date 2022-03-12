package gameManager;


import java.util.Observer;

import inter_face.Game;
import mvc.Model;
public abstract class GameObject implements Game,Observer{
	private float x;
	private float y;
	private Model game;

	public GameObject(int x, int y, Model game) {
		this.x = x;
		this.y = y;
		this.game = game;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Model getGame() {
		return game;
	}

	public void setGame(Model game) {
		this.game = game;
	}

}
