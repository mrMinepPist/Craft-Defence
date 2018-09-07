package org.mrMinepPist.towerdefence.play;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.Array;
import org.mrMinepPist.towerdefence.DefenceGame;
import org.mrMinepPist.towerdefence.font.Font;
import org.mrMinepPist.towerdefence.files.Files;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.*;
import org.mrMinepPist.towerdefence.*;
//выбор уровня
public class LevelChooseScreen implements Screen {
	BitmapFont font; 
	private Stage stage;
	private Array<ImageTextButton> levels;
	private SpriteBatch btch;
	public LevelChooseScreen() {
		font = Font.getBitmapFont();
		btch = new SpriteBatch();
		int length = Files.files.length;
		
		levels = new Array<ImageTextButton>(length);
		int o = 1;
		int y = 150;
		for(int i = 0; i < length; i++) {
			if(!Files.files[i].isDirectory()) {
				ImageTextButton.ImageTextButtonStyle iStyle = new ImageTextButton.ImageTextButtonStyle(); 
				iStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
				iStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
				iStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
				iStyle.font = font;
				ImageTextButton btn = new ImageTextButton(new Integer(o).toString(o), iStyle);
				btn.setPosition(160, y);
				btn.setSize(240, 40.5f);
				final int r = o;
				btn.addListener(new ClickListener(){
					@Override 
					public void clicked(InputEvent event, float x, float y) {
						DefenceGame.getDefenceGame().setScreen(new PlayScreen(r, null));
					}; 
				});
				levels.add(btn);
				o = o + 1;
				y = y - 60;
			}
		}
		stage = new Stage(new ExtendViewport(480, 320));
		for(int i = 0; i < levels.size; i++) {
			stage.addActor(levels.get(i));
		}
		InputMultiplexer mlp = new InputMultiplexer();
		mlp.addProcessor(stage);
		mlp.addProcessor(new CommonInputProcessor(CommonInputProcessor.CommonState.LEVELS));
		Gdx.input.setInputProcessor(mlp);
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 1f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		btch.begin();
		btch.draw(new Texture(Gdx.files.internal("resources/images/back/background.jpeg")), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
}
