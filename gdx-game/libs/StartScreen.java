package org.mrMinepPist.towerdefence;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import org.mrMinepPist.towerdefence.font.Font;
import org.mrMinepPist.towerdefence.play.LevelChooseScreen;
import com.badlogic.gdx.graphics.g2d.*;
import org.mrMinepPist.towerdefence.play.*;
import com.badlogic.gdx.*;
import java.util.*;
import org.mrMinepPist.towerdefence.files.*;
import java.io.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
//стартовый экран
public class StartScreen implements Screen {
	private BitmapFont font; 
	private Stage stage;
	private SpriteBatch btch;
	private ImageTextButton startGame, shopGame;
	public StartScreen() {
		font = Font.getBitmapFont();
		Gdx.input.setCatchBackKey(true);
		btch = new SpriteBatch();
		android.util.Log.e("tag", Locale.getDefault().toString());
		DefenceGame.getDefenceGame().main = Gdx.audio.newMusic(Gdx.files.internal("resources/sound/main.m4a"));
		DefenceGame.getDefenceGame().main.setLooping(true);
		DefenceGame.getDefenceGame().main.play();
		try {Property.load();
		} catch (IOException e) {}
		//виджеты
		Image img = new Image(new Texture(Gdx.files.internal("resources/images/gui/logo.png")));
		img.setSize(img.getWidth() / 2, img.getHeight() / 2);
		img.setPosition(100, 270);
		ImageTextButton.ImageTextButtonStyle startGameStyle = new ImageTextButton.ImageTextButtonStyle(); 
		startGameStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		startGameStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		startGameStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		startGameStyle.font = font;
		startGame = new ImageTextButton(Property.getDefaultValueString("org.mrMinepPist.towerdefence.translation.Play.name"), startGameStyle);
		startGame.setSize(240, 40.5f);
		startGame.setPosition(160, 150);
		startGame.addListener(new ClickListener() { 
			@Override 
			public void clicked(InputEvent event, float x, float y) { 
				DefenceGame.getDefenceGame().setScreen(new LevelChooseScreen()); 
			}; 
		});
		
		ImageTextButton.ImageTextButtonStyle shopGameStyle = new ImageTextButton.ImageTextButtonStyle(); 
		shopGameStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		shopGameStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		shopGameStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		shopGameStyle.font = font;
		shopGame = new ImageTextButton(Property.getDefaultValueString("org.mrMinepPist.towerdefence.translation.Shop.name"), shopGameStyle);
		shopGame.setSize(240, 40.5f);
		shopGame.setPosition(160, 90);
		shopGame.addListener(new ClickListener() { 
			@Override 
			public void clicked(InputEvent event, float x, float y) { 
				DefenceGame.getDefenceGame().setScreen(new ShopScreen()); 
			}; 
		});
		Pixmap pxmli = new Pixmap(130, 40, Pixmap.Format.RGBA8888);
		pxmli.setColor(Color.GREEN);
		pxmli.fill();
		Image iml = new Image(new Texture(pxmli));
		iml.setSize(130, 40);
		iml.setPosition(220, 0);
		stage = new Stage(new ExtendViewport(480, 320));
		Label.LabelStyle Style = new Label.LabelStyle();
		Style.font = font;
		Label moneys = new Label(Integer.toString(DefenceGame.getDefenceGame().moneys), Style);
		moneys.setPosition(240, 0);
		moneys.setSize(40, 40);
		Image moneysIm = new Image(new Texture(Gdx.files.internal("resources/images/gui/coin.jpeg")));
		moneysIm.setPosition(260, 0);
		moneysIm.setSize(32, 32);
		Label sapphires = new Label(Integer.toString(DefenceGame.getDefenceGame().moneys), Style);
		sapphires.setPosition(300, 0);
		sapphires.setSize(40, 40);
		Image sapphiresIm = new Image(new Texture(Gdx.files.internal("resources/images/gui/sapphire.png")));
		sapphiresIm.setPosition(310, 0);
		sapphiresIm.setSize(32, 32);
		stage.addActor(img);
		stage.addActor(iml);
		stage.addActor(moneys);
		stage.addActor(moneysIm);
		stage.addActor(sapphires);
		stage.addActor(sapphiresIm);
		stage.addActor(startGame);
		stage.addActor(shopGame);
		//просто Multiplexer
		InputMultiplexer mlp = new InputMultiplexer();
		mlp.addProcessor(stage);
		mlp.addProcessor(new CommonInputProcessor(CommonInputProcessor.CommonState.MAIN));
		Gdx.input.setInputProcessor(mlp);
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 1f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		btch.begin();
		//фон
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
