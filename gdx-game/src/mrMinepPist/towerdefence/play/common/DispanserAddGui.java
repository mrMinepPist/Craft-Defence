package org.mrMinepPist.towerdefence.play.common;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;
public class DispanserAddGui extends Sprite {
	public static class DispanserElements {
		public static Sprite selector;
		public static Sprite slabDisp;
		public static Sprite disp;
		public static Texture back;
		public static TextureRegion buy;
	}
	public DispanserAddGui() {
		Pixmap p = new Pixmap(140, 60, Pixmap.Format.RGBA8888);
		p.setColor(Color.BROWN);
		p.fill();
		DispanserElements.back = new Texture(p);
		DispanserElements.disp = new Sprite(new Texture(Gdx.files.internal("resources/images/dispensers/wooden.jpeg")));
		DispanserElements.disp.setSize(32, 32);
		DispanserElements.disp.setPosition(40, 20);
		DispanserElements.slabDisp = new Sprite(new Texture(Gdx.files.internal("resources/images/dispensers/slab_wooden.jpeg")));
		DispanserElements.slabDisp.setSize(32, 32);
		DispanserElements.slabDisp.setPosition(80, 20);
		DispanserElements.buy = new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20);
		Pixmap pxm = new Pixmap(80, 32, Pixmap.Format.RGBA8888);
		DispanserElements.selector = new Sprite(new Texture(pxm));
	}
	@Override
	public void draw(Batch btch) {
		btch.draw(DispanserElements.back, 0, 0);
		DispanserElements.disp.draw(btch);
		DispanserElements.slabDisp.draw(btch);
		btch.draw(DispanserElements.buy, 20, 0, 100, 15);
	}
	public boolean clickSlabWoodenDisp(float clickX, float clickY) {
		return true;
	}
	public void setXY(float x, float y) {
	}
}
