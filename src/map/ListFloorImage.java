 package map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import gameManager.GameObject;
import inter_face.Game;
import mvc.Model;

public class ListFloorImage extends GameObject {
	private List<FloorImage> listItemFrontMap;

	public ListFloorImage(int x, int y, Model game) {
		super(x, y, game);
		listItemFrontMap = new ArrayList<FloorImage>();
		listItemFrontMap.add(
				new FloorImage((int) (120 - getGame().getCamera().getX()), (int) (436 - getGame().getCamera().getY()),
						getGame(), getGame().getInputData().getListImage().get("wall1").getImg()));
		listItemFrontMap.add(
				new FloorImage((int) (1175 - getGame().getCamera().getX()), (int) (436 - getGame().getCamera().getY()),
						getGame(), getGame().getInputData().getListImage().get("wall1").getImg()));
		listItemFrontMap.add(
				new FloorImage((int) (2230 - getGame().getCamera().getX()), (int) (436 - getGame().getCamera().getY()),
						getGame(), getGame().getInputData().getListImage().get("wall1").getImg()));
		listItemFrontMap.add(
				new FloorImage((int) (3285 - getGame().getCamera().getX()), (int) (436 - getGame().getCamera().getY()),
						getGame(), getGame().getInputData().getListImage().get("wall2").getImg()));

	}

	public void draw(Graphics2D g2) {
		for (int i = 0; i < listItemFrontMap.size(); i++) {
			listItemFrontMap.get(i).draw(g2);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		
		
	}

}
