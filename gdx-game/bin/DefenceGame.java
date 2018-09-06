package org.mrMinepPist.towerdefence;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.*;

public class DefenceGame extends Game {
	public Music main;
	public int moneys = 0;
	private static DefenceGame game;
	@Override
	public void create() {
		game = this;
		//открываем screen
		this.setScreen(new StartScreen());
	}
	public static DefenceGame getDefenceGame() {
		return game;
	}
	@Override
	public void render() {        
		super.render();
	}
	@Override
	public void dispose() {
	}
	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
}
