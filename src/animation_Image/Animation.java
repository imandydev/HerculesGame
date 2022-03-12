package animation_Image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
	private String nameAnimation;
	private List<FrameImage> listImage;
	private List<Double> timeForAction;
	private List<Boolean> ignoreImage;
	private int currentFrame;
	private long beginTime;
	private boolean isRepeat;

	public Animation(String nameAnimation) {
		this.nameAnimation = nameAnimation;
		listImage = new ArrayList<FrameImage>();
		timeForAction = new ArrayList<Double>();
		ignoreImage = new ArrayList<Boolean>();
		isRepeat = true;
		currentFrame = 0;

	}

	public Animation(Animation animation) {
		this.nameAnimation = animation.getNameAnimation();
		this.listImage = new ArrayList<FrameImage>();
		this.ignoreImage = new ArrayList<Boolean>();
		this.timeForAction = new ArrayList<Double>();
		for (int i = 0; i < animation.getListImage().size(); i++) {

			this.listImage.add(new FrameImage(animation.getListImage().get(i)));
		}

		for (int i = 0; i < animation.getIgnoreImage().size(); i++) {
			this.getIgnoreImage().add(animation.getIgnoreImage().get(i));
		}

		for (int i = 0; i < animation.getTimeForAction().size(); i++) {
			this.getTimeForAction().add(animation.getTimeForAction().get(i));
		}
		this.isRepeat = animation.isRepeat();
		this.currentFrame = animation.getCurrentFrame();

	}

	public List<FrameImage> getListImage() {
		return listImage;
	}

	public void setListImage(List<FrameImage> listImage) {
		this.listImage = listImage;
	}

	public void add(FrameImage frameImage, double time) {
		listImage.add(frameImage);
		timeForAction.add(time);
		ignoreImage.add(false);

	}

	public void draw(Graphics2D g2, int posX, int posY) {

		if (currentFrame < listImage.size()) {
			g2.drawImage(listImage.get(currentFrame).getImg(), posX, posY,
					listImage.get(currentFrame).getImg().getWidth() * 2,
					listImage.get(currentFrame).getImg().getHeight() * 2, null);
		}
	}

	public void drawBroozer(Graphics2D g2, int posX, int posY) {

		if (currentFrame < listImage.size()) {
			g2.drawImage(listImage.get(currentFrame).getImg(), posX, posY,
					listImage.get(currentFrame).getImg().getWidth() / 3 + 4,
					listImage.get(currentFrame).getImg().getHeight() / 3 + 4, null);
		}
	}

	public void drawAttackRed(Graphics2D g2, int posX, int posY) {

		if (currentFrame < listImage.size()) {
			g2.drawImage(listImage.get(currentFrame).getImg(), posX, posY,
					listImage.get(currentFrame).getImg().getWidth() * 2,
					listImage.get(currentFrame).getImg().getHeight() * 2, null);
		}
	}

	public void drawBossSiege(Graphics2D g2, int posX, int posY) {
		if (currentFrame < listImage.size()) {
			g2.drawImage(listImage.get(currentFrame).getImg(), posX, posY,
					listImage.get(currentFrame).getImg().getWidth() * 8,
					listImage.get(currentFrame).getImg().getHeight() * 8, null);
		}
	}

	public void upload(long dataTime) {
		if (beginTime == 0)
			beginTime = dataTime;
		else {
			if (dataTime - beginTime > timeForAction.get(currentFrame)) {
				nextFrame();
				beginTime = dataTime;
			}
		}
	}

	public void daoNguoc() {
		for (int i = 0; i < listImage.size(); i++) {
			BufferedImage image = listImage.get(i).getImg();
			AffineTransform af = AffineTransform.getScaleInstance(-1, 1);
			af.translate(-image.getWidth(), 0);
			AffineTransformOp op = new AffineTransformOp(af, AffineTransformOp.TYPE_BILINEAR);
			image = op.filter(image, null);
			listImage.get(i).setImg(image);
		}
	}

	public void nextFrame() {
		if (currentFrame >= listImage.size() - 1) {
			if (isRepeat)
				currentFrame = 0;
		} else
			currentFrame++;
		if (ignoreImage.get(currentFrame))
			nextFrame();
	}

	public boolean isLastFrame() {
		if (currentFrame == listImage.size() - 1)
			return true;
		else
			return false;
	}

	public String getNameAnimation() {
		return nameAnimation;
	}

	public void setNameAnimation(String nameAnimation) {
		this.nameAnimation = nameAnimation;
	}

	public List<Double> getTimeForAction() {
		return timeForAction;
	}

	public void setTimeForAction(List<Double> timeForAction) {
		this.timeForAction = timeForAction;
	}

	public List<Boolean> getIgnoreImage() {
		return ignoreImage;
	}

	public void setIgnoreImage(List<Boolean> ignoreImage) {
		this.ignoreImage = ignoreImage;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public boolean isRepeat() {
		return isRepeat;
	}

	public void setRepeat(boolean isRepeat) {
		this.isRepeat = isRepeat;
	}

}
