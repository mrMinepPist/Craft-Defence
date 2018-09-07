package org.mrMinepPist.towerdefence;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import org.mrMinepPist.towerdefence.play.*;
import org.mrMinepPist.towerdefence.play.common.*;
import com.badlogic.gdx.audio.*;
//Input Proccessor закрытия
public class CommonInputProcessor implements InputProcessor {
	public static enum CommonState {
		MAIN, 
		SHOP,
		GAME,
		PAUSE,
		MSHOP,
		LEVELS
	}
	private PlaySave save;
	private int level;
	private CommonState st;
	private Music sound;
	public CommonInputProcessor(CommonState st, int level, PlaySave save, Music sound) {
		this(st, level);
		this.save = save;
		this.sound = sound;
	}
	public CommonInputProcessor(CommonState st, int level) {
		this(st);
		this.level = level;
	}
	public CommonInputProcessor(CommonState st) {
		this.st = st;
	}
	@Override
	public boolean keyUp(int p1) {
		return false;
	}
	@Override
	public boolean keyTyped(char p1) {
		return false;
	}
	@Override
	public boolean touchDown(int p1, int p2, int p3, int p4) {
		return false;
	}
	@Override
	public boolean touchUp(int p1, int p2, int p3, int p4) {
		return false;
	}
	@Override
	public boolean touchDragged(int p1, int p2, int p3) {
		return false;
	}
	@Override
	public boolean mouseMoved(int p1, int p2) {
		return false;
	}
	@Override
	public boolean scrolled(int p1) {
		return false;
	}
	@Override 
	public boolean keyDown(int keycode) {
		if(st == CommonState.SHOP) {
			if(keycode == Keys.BACK){
				DefenceGame.getDefenceGame().setScreen(new ShopScreen());
				return true;
			}
		} else if(st == CommonState.MAIN) {
			if(keycode == Keys.BACK){
				Gdx.app.exit();
				return true;
			}
		} else if(st == CommonState.GAME) {
			if(keycode == Keys.BACK){
				//DefenceGame.getDefenceGame().setScreen(new PauseScreen(this.level));
				return true;
			}
		} else if(st == CommonState.PAUSE) {
			if(keycode == Keys.BACK){
				sound.stop();
				sound.dispose();
				DefenceGame.getDefenceGame().setScreen(new PlayScreen(this.level, save));
				return true;
			}
		} else if(st == CommonState.MSHOP) {
			if(keycode == Keys.BACK){
				DefenceGame.getDefenceGame().setScreen(new StartScreen());
				return true;
			}
		} else if(st == CommonState.LEVELS) {
			if(keycode == Keys.BACK){
				DefenceGame.getDefenceGame().setScreen(new StartScreen());
				return true;
			}
		}
		return false; 
	} 
}
