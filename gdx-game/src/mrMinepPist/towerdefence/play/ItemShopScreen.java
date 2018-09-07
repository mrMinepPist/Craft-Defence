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
import com.badlogic.gdx.*;
import org.mrMinepPist.towerdefence.*;
import org.mrMinepPist.towerdefence.files.*;
import com.badlogic.gdx.graphics.*;
//предметный магазин
public class ItemShopScreen implements Screen {
	BitmapFont font; 
	private Stage stage;
	private SpriteBatch btch;
	public ItemShopScreen() {
		font = Font.getBitmapFont();
		btch = new SpriteBatch();
		//виджеты
		Pixmap pxm = new Pixmap(460, 300, Pixmap.Format.RGBA8888);
		pxm.setColor(Color.GRAY);
		pxm.fill();
		Image img = new Image(new Texture(pxm));
		img.setPosition(10, 40);
		img.setSize(460, 300);
		Label.LabelStyle shopStyle = new Label.LabelStyle();
		shopStyle.font = font;
		Label shop = new Label(Property.getDefaultValueString("org.mrMinepPist.towerdefence.translation.Shop.item.name"), shopStyle);
		shop.setPosition(200, 300);
		shop.setSize(20, 20);
		Pixmap pxmls = new Pixmap(130, 40, Pixmap.Format.RGBA8888);
		pxmls.setColor(Color.WHITE);
		pxmls.fill();
		Image imls = new Image(new Texture(pxmls));
		imls.setSize(80, 96);
		imls.setPosition(40, 178);
		Image slabDispenser = new Image(new Texture(Gdx.files.internal("resources/images/blocks/plank.png")));
		slabDispenser.setPosition(56, 218);
		slabDispenser.setSize(48, 48);
		Label.LabelStyle kStyle = new Label.LabelStyle();
		kStyle.font = font;
		Label slabC = new Label("3", kStyle);
		slabC.setPosition(48, 184);
		slabC.setSize(40, 40);
		slabC.setFontScale(0.8f);
		Image slabM = new Image(new Texture(Gdx.files.internal("resources/images/gui/coin.jpeg")));
		slabM.setPosition(68, 195);
		slabM.setSize(16, 16);
		Label slabs = new Label("Arrow", kStyle);
		slabs.setPosition(44, 152);
		slabs.setWrap(true);
		slabs.setSize(72, 72);
		slabs.setFontScale(0.6f);
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
		stage.addActor(img);
		stage.addActor(shop);
		stage.addActor(imls);
		stage.addActor(slabDispenser);
		stage.addActor(slabC);
		stage.addActor(slabM);
		stage.addActor(slabs);
		stage.addActor(iml);
		stage.addActor(moneys);
		stage.addActor(moneysIm);
		stage.addActor(sapphires);
		stage.addActor(sapphiresIm);
		InputMultiplexer mlp = new InputMultiplexer();
		mlp.addProcessor(stage);
		mlp.addProcessor(new CommonInputProcessor(CommonInputProcessor.CommonState.SHOP));
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