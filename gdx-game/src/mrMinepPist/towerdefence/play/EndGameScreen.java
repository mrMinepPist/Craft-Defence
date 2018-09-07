package org.mrMinepPist.towerdefence.play;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import org.mrMinepPist.towerdefence.font.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import org.mrMinepPist.towerdefence.*;

public class EndGameScreen implements Screen {
	public enum EndGameState {
		SUCCESS,
		GAME_OVER
	}
	EndGameState s;
	OrthographicCamera camera;
	SpriteBatch btch;
	BitmapFont font;
	Stage stage;
	public EndGameScreen(EndGameState s) {
		this.s = s;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 320);
		btch = new SpriteBatch();
		font = Font.getBitmapFont();
		//виджеты
		ImageTextButton.ImageTextButtonStyle ss1 = new ImageTextButton.ImageTextButtonStyle();
		ss1.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		ss1.over = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		ss1.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		ss1.font = font;
		ImageTextButton b = new ImageTextButton("In Main Menu", ss1);
		b.setPosition(180, 180);
		b.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent eev, float x, float y) {
				DefenceGame.getDefenceGame().setScreen(new StartScreen());
			}
		});
		stage = new Stage(new ExtendViewport(480, 320));
		stage.addActor(b);
		InputMultiplexer mlp = new InputMultiplexer();
		mlp.addProcessor(stage);
		Gdx.input.setInputProcessor(mlp);
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 1f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_ALPHA_BITS);
		camera.update();
		stage.act(delta);
		stage.draw();
        btch.setProjectionMatrix(camera.combined);
		btch.enableBlending();
		btch.begin();
		if(s == EndGameState.SUCCESS) {
			font.draw(btch, "Ended successfully", 200, 240);
		} else if (s == EndGameState.GAME_OVER) {
			font.draw(btch, "Game Over", 200, 240);
		}
		btch.end();
	}
	@Override
	public void resize(int p1, int p2) {
	}
	@Override
	public void show() {

	}
	@Override
	public void hide() {

	}
	@Override
	public void pause() {

	}
	@Override
	public void resume() {
	}
	@Override
	public void dispose() {
	}
}
