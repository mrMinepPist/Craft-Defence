package org.mrMinepPist.towerdefence.play;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import org.mrMinepPist.towerdefence.font.Font;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import org.mrMinepPist.towerdefence.*;
import com.badlogic.gdx.*;
import org.mrMinepPist.towerdefence.files.*;
import com.badlogic.gdx.graphics.*;
//магазин
public class ShopScreen implements Screen {
	BitmapFont font; 
	private Stage stage;
	private SpriteBatch btch;
	public ShopScreen() {
		font = Font.getBitmapFont();
		btch = new SpriteBatch();
		//виджеты
		Label.LabelStyle shopStyle = new Label.LabelStyle();
		shopStyle.font = font;
		Label shop = new Label(Property.getDefaultValueString("org.mrMinepPist.towerdefence.translation.Shop.name"), shopStyle);
		shop.setPosition(200, 300);
		shop.setSize(20, 20);
		Image money_get = new Image(new Texture(Gdx.files.internal("resources/images/blocks/grass.png")));
		money_get.setPosition(20, 60);
		money_get.addListener(new ClickListener(){
				public void clicked(InputEvent event, float x, float y) { 
					DefenceGame.getDefenceGame().setScreen(new ItemShopScreen()); 
				}; 
			});
		Image dispenser_get = new Image(new Texture(Gdx.files.internal("resources/images/blocks/grass.png")));
		dispenser_get.setPosition(168, 60);
		dispenser_get.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) { 
				DefenceGame.getDefenceGame().setScreen(new DispenserShopScreen()); 
			}; 
		});
		Image item_get = new Image(new Texture(Gdx.files.internal("resources/images/blocks/grass.png")));
		item_get.setPosition(316, 60);
		item_get.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) { 
				DefenceGame.getDefenceGame().setScreen(new MoneyShopScreen()); 
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
		stage = new Stage(new ExtendViewport(480, 320));
		stage.addActor(shop);
		stage.addActor(money_get);
		stage.addActor(dispenser_get);
		stage.addActor(item_get);
		stage.addActor(iml);
		stage.addActor(moneys);
		stage.addActor(moneysIm);
		stage.addActor(sapphires);
		stage.addActor(sapphiresIm);
		InputMultiplexer mlp = new InputMultiplexer();
		mlp.addProcessor(stage);
		mlp.addProcessor(new CommonInputProcessor(CommonInputProcessor.CommonState.MSHOP));
		Gdx.input.setInputProcessor(mlp);
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 1f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		btch.begin();
		//задний фон
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
