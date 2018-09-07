package org.mrMinepPist.towerdefence.play.common;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
//игровой обьект
public class GameObject extends Sprite {
	private float[] position = new float[2];
	private float[] size = new float[2];
	public GameObject() {
		super();
	}
	public GameObject(Texture texture) {
		super(texture);
	}
	public GameObject(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
		super(texture, srcX, srcY, srcWidth, srcHeight);
	}
	//позиция
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		position[0] = x;
		position[1] = y;
	}
	public float[] getPosition() {
		return position;
	}
}
