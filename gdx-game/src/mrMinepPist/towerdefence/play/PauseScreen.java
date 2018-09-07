package org.mrMinepPist.towerdefence.play;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.*;
import org.mrMinepPist.towerdefence.*;
import org.mrMinepPist.towerdefence.font.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import org.mrMinepPist.towerdefence.play.common.*;
import java.util.logging.*;
import org.mrMinepPist.towerdefence.files.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.*;
//экран паузы
public class PauseScreen implements Screen {
	private SpriteBatch btch;
	private Stage stage;
	private BitmapFont font;
	private ImageTextButton unpause, close;
	private final Music sound = Gdx.audio.newMusic(Gdx.files.internal("resources/sound/amy.mp3"));
	public PauseScreen(final int level) {
		btch = new SpriteBatch();
		font = Font.getBitmapFont();
		//виджеты
		ImageTextButton.ImageTextButtonStyle shopGameStyle = new ImageTextButton.ImageTextButtonStyle(); 
		shopGameStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		shopGameStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		shopGameStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		shopGameStyle.font = font;
		unpause = new ImageTextButton(Property.getDefaultValueString("org.mrMinepPist.towerdefence.translation.Play.unpause.name"), shopGameStyle);
		unpause.setSize(200, 20);
		unpause.setPosition(160, 60);
		unpause.addListener(new ClickListener() { 
			@Override 
			public void clicked(InputEvent event, float x, float y) {
				DefenceGame.getDefenceGame().setScreen(new PlayScreen(level, PlaySave.INSTANCE));
			}; 
		});
		Pixmap m = new Pixmap(40, 40, Pixmap.Format.RGBA8888);
		m.setColor(Color.WHITE);
		m.fill();
		
		Window.WindowStyle wndst = new Window.WindowStyle(font, Color.BLACK, new TextureRegionDrawable(new TextureRegion(new Texture(m))));
		final Dialog dlg = new Dialog("Close??", wndst) { 
			public void result(Object obj) { 
				DefenceGame.getDefenceGame().setScreen(new StartScreen());
			} 
		};
		dlg.button("true");
		dlg.button("false");
		close = new ImageTextButton(Property.getDefaultValueString("org.mrMinepPist.towerdefence.translation.Play.unpause.name"), shopGameStyle);
		close.setSize(200, 20);
		close.setPosition(160, 20);
		close.addListener(new ClickListener() { 
				@Override 
				public void clicked(InputEvent event, float x, float y) {
					dlg.show(stage);
				}; 
			});
		stage = new Stage(new ExtendViewport(480, 320));
		double i = Math.random();
		if (i < 0.018) {
			//Эми (***з??)
			DefenceGame.getDefenceGame().main.stop();
			DefenceGame.getDefenceGame().main.dispose();
			final Music sound = Gdx.audio.newMusic(Gdx.files.internal("resources/sound/amy.mp3"));
			sound.setLooping(true); 
			sound.play();
			Image amy = new Image(new Texture(Gdx.files.internal("resources/images/gui/amy/amy.png")));
			amy.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			stage.addActor(amy);
		}
		stage.addActor(unpause);
		stage.addActor(close);
		InputMultiplexer mlp = new InputMultiplexer();
		mlp.addProcessor(stage);
		mlp.addProcessor(new CommonInputProcessor(CommonInputProcessor.CommonState.PAUSE, level, PlaySave.INSTANCE, sound));
		Gdx.input.setInputProcessor(mlp);
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 1f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		btch.begin();
		btch.end();
		stage.act(delta);
		stage.draw();
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
}//ttr43eg
//c4gAQ4x3OwM1%Qsu(QvE
