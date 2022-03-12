package mvc;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import Enemies.Broozer;
import bosses.SiegeBosses;
import camera.Camera;
import effectAttack.HerculesAttackPunch;
import figure_Main.Hercules;
import gameManager.Figure;
import gameManager.ManagerFigure;
import inter_face.ManagerInput;
import inter_face.ManagerListFigure;
import inter_face.Game;

import loadData.InputData;
import map.BackGroundMap;

import map.ListFloorImage;
import map.PhysicalMap;

public class Model extends Observable {

	private ManagerInput inputData;
	private Game mapPhysic, camera, backGroundMap, frontMap, hercules, broozer, broozer1, broozer2, broozer3, broozer4,
			broozer5, broozer6, broozer7, broozer8, broozer9, broozer10, broozer11, effectAttack, bossSiege;
	private ManagerListFigure listObjectFigure;

	public Model() throws IOException {
		inputData = new InputData();
		inputData.loadImage();
		inputData.loadAnimation();
		camera = new Camera(60, 0, this, this);
		mapPhysic = new PhysicalMap(0, 0, this);
		backGroundMap = new BackGroundMap(60, 0, this);
		frontMap = new ListFloorImage(0, 0, this);
		listObjectFigure = new ManagerFigure(this);
		createObjectFigure();
		bossSiege = new SiegeBosses(3700, 300, this, 350, 140, 0, 0, 0, 0.1f, this);
		hercules = new Hercules(511, 50, this, 100, 140, 0, 0, 1, 0.1f, this);
		effectAttack = new HerculesAttackPunch((int) hercules.getX(), (int) hercules.getY(), this, 100, 140, 0, 0, 1,
				0.1f, this);
	}

	public void createObjectFigure() {

		broozer1 = new Broozer(1500, 300, this, 160, 140, 0, 0, 1, 0.1f, this);
		broozer2 = new Broozer(2200, 300, this, 160, 140, 0, 0, 1, 0.1f, this);
		broozer3 = new Broozer(3000, 300, this, 160, 140, 0, 0, 1, 0.1f, this);
		broozer4 = new Broozer(3500, 300, this, 160, 140, 0, 0, 1, 0.1f, this);

		((ManagerFigure) listObjectFigure).addObject((Figure) broozer1);
		listObjectFigure.addObject((Figure) broozer2);
		listObjectFigure.addObject((Figure) broozer3);
		listObjectFigure.addObject((Figure) broozer4);

	}

	public void update() {
		setChanged();
		notifyObservers();

	}

	public void draw(Graphics2D g2) {
		mapPhysic.draw(g2);
		backGroundMap.draw(g2);
		frontMap.draw(g2);
		bossSiege.draw(g2);
		listObjectFigure.draw(g2);
		hercules.draw(g2);
		effectAttack.draw(g2);
	}

	public InputData getInputData() {
		return (InputData) inputData;
	}

	public void setInputData(InputData inputData) {
		this.inputData = inputData;
	}

	public PhysicalMap getMapPhysic() {
		return (PhysicalMap) mapPhysic;
	}

	public void setMapPhysic(PhysicalMap mapPhysic) {
		this.mapPhysic = mapPhysic;
	}

	public Camera getCamera() {
		return (Camera) camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Hercules getHercules() {
		return (Hercules) hercules;
	}

	public void setHercules(Hercules hercules) {
		this.hercules = hercules;
	}

	public Broozer getBroozer() {
		return (Broozer) broozer;
	}

	public void setBroozer(Broozer broozer) {
		this.broozer = broozer;
	}

	public HerculesAttackPunch getEffectAttack() {
		return (HerculesAttackPunch) effectAttack;
	}

	public void setEffectAttack(HerculesAttackPunch effectAttack) {
		this.effectAttack = effectAttack;
	}

	public BackGroundMap getBackGroundMap() {
		return (BackGroundMap) backGroundMap;
	}

	public void setBackGroundMap(BackGroundMap backGroundMap) {
		this.backGroundMap = backGroundMap;
	}

	public ListFloorImage getFrontMap() {
		return (ListFloorImage) frontMap;
	}

	public void setFrontMap(ListFloorImage frontMap) {
		this.frontMap = frontMap;
	}

	public SiegeBosses getBossSiege() {
		return (SiegeBosses) bossSiege;
	}

	public void setBossSiege(SiegeBosses bossSiege) {
		this.bossSiege = bossSiege;
	}

	public ManagerFigure getListObjectFigure() {
		return (ManagerFigure) listObjectFigure;
	}

	public void setListObjectFigure(ManagerFigure listObjectFigure) {
		this.listObjectFigure = listObjectFigure;
	}

}
