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
import com.badlogic.gdx.utils.Array;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.mrMinepPist.towerdefence.font.Font;
import org.mrMinepPist.towerdefence.files.Files;
import org.mrMinepPist.towerdefence.files.XML;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;
import org.mrMinepPist.towerdefence.*;
import org.mrMinepPist.towerdefence.play.common.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import org.mrMinepPist.towerdefence.files.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import org.w3c.dom.*;
import com.badlogic.gdx.utils.*;

public class PlayScreen implements Screen, InputProcessor {
	private boolean isVaweEnded = false;
	//перечисления
	public static final enum PlayStage {
		DIG_PATH,
		MOB_VAWES
	}
	public static final enum DayNigthCycle {
		DAY,
		NIGHT
	}
	BitmapFont font, font1;
	private Stage stage;
	private int level;
	private Node XMLMap;
	private Array<Node> XMLElem;
	private SpriteBatch btch;
	private Array<GameObject> elems;
	private Array<GameObject> elemsGrass;
	private int playHealth = 20;
	private int points;
	private int moneys;
	private PlaySave save;
	private Array<Image> chars;
	OrthographicCamera camera;
	Image nr;
	private EntityObject player;
	private Array<EntityObject> ents = new Array<EntityObject>();
	private Array<DispenserObject> dispensers = new Array<DispenserObject>();
	private Array<ArrowObject> arrows = new Array<ArrowObject>();
	private DispanserAddGui dispanserAddGui = new DispanserAddGui();
	private PlayStage p;
	private DayNigthCycle c;
	private PlayerHouse h;
	private int time;
	public Sprite clock;
	public Sprite transNIGHT1, transNIGHT2, transNIGHT3, transNIGHT4,
		transNIGHT5, transNIGHT6;
	private Array<Node> waves;
	private ArrayMap<Node, Array<Node>> nodes;
	private boolean isVaweExec = false;
	private boolean isVawePartExec = true, isVaweCarried = false;
	private int[] wave = new int[]{0, 0};
	private Node n = null;
	public PlayScreen(int level, PlaySave save) {
		//присваивание
		this.level = level;
		points = 0;
		moneys = 0;
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 320);
		font = Font.getBitmapFont();
		font1 = Font.getBitmapFont();
		font1.setColor(Color.WHITE);
		setupWaves();
		Pixmap px1 = new Pixmap(480, 320, Pixmap.Format.RGBA8888);
		px1.setColor(0, 0, 0, 0.1f);
		px1.fill();
		transNIGHT1 = new Sprite(new Texture(px1));
		Pixmap px2 = new Pixmap(480, 320, Pixmap.Format.RGBA8888);
		px2.setColor(0, 0, 0, 0.2f);
		px2.fill();
		transNIGHT2 = new Sprite(new Texture(px2));
		Pixmap px3 = new Pixmap(480, 320, Pixmap.Format.RGBA8888);
		px3.setColor(0, 0, 0, 0.3f);
		px3.fill();
		transNIGHT3 = new Sprite(new Texture(px3));
		Pixmap px4 = new Pixmap(480, 320, Pixmap.Format.RGBA8888);
		px4.setColor(0, 0, 0, 0.4f);
		px4.fill();
		transNIGHT4 = new Sprite(new Texture(px4));
		Pixmap px5 = new Pixmap(480, 320, Pixmap.Format.RGBA8888);
		px5.setColor(0, 0, 0, 0.5f);
		px5.fill();
		transNIGHT5 = new Sprite(new Texture(px5));
		Pixmap px6 = new Pixmap(480, 320, Pixmap.Format.RGBA8888);
		px6.setColor(0, 0, 0, 0.6f);
		px6.fill();
		transNIGHT6 = new Sprite(new Texture(px6));
		elems = new Array<GameObject>();
		elemsGrass = new Array<GameObject>();
		btch = new SpriteBatch();
		chars = new Array<Image>();
		XMLMap = XML.getgameMap(XML.getLevelNode(XML.getLevelDocument(level)));
		XMLElem = new Array<Node>();
		time = 0;
		for(int i = 0; i < XMLMap.getChildNodes().getLength(); i++) {
			if(XMLMap.getChildNodes().item(i).getNodeName().equals("gameMapElement")) {
				XMLElem.add(XMLMap.getChildNodes().item(i));
			}
		}
		clock = new Sprite(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_00.png")));
		clock.setSize(48, 48);
		clock.setPosition(240, 252);
		player = new EntityObject(new Texture(Gdx.files.internal("resources/images/player.jpeg")));
		nr = new Image(new Texture(Gdx.files.internal("resources/images/gui/pause.jpeg")));
		nr.setPosition(480, 0);
		final int levell = this.level;
		nr.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent ev, float x, float y) {
					PlaySave.level = levell;
					DefenceGame.getDefenceGame().setScreen(new PauseScreen(levell));
				}
			});
		ImageTextButton.ImageTextButtonStyle iStyle = new ImageTextButton.ImageTextButtonStyle(); 
		iStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		iStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		iStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resources/images/gui/btnPack.jpeg")), 0, 20, 200, 20));
		iStyle.font = font;
		Pixmap m = new Pixmap(40, 40, Pixmap.Format.RGBA8888);
		m.setColor(Color.WHITE);
		m.fill();
		/*Skin tileSkin = new Skin(Gdx.files.internal("uiskin.json")); 
		final Dialog dlg = new Dialog("Close??", tileSkin) { 
			public void result(Object obj) { 
				DefenceGame.getDefenceGame().setScreen(new StartScreen());
			} 
		};
		dlg.button("true", true);
		dlg.button("false", false);*/
		ImageTextButton btn = new ImageTextButton("close", iStyle);
		btn.setPosition(410, 0);
		btn.setSize(60, 20.25f);
		btn.addListener(new ClickListener(){
			@Override 
			public void clicked(InputEvent event, float x, float y) {
			}; 
		});
		Label.LabelStyle levelIntStyle = new Label.LabelStyle();
		levelIntStyle.font = font;
		Label levelInt = new Label(Property.getDefaultValueString("org.mrMinepPist.towerdefence.translation.Play.level.name") + " " + Integer.toString(level), levelIntStyle);
		levelInt.setPosition(20, 300);
		levelInt.setSize(20, 20);
		Label.LabelStyle Style = new Label.LabelStyle();
		Style.font = font;
		Label points = new Label(Integer.toString(this.points) + " " + Property.getDefaultValueString("org.mrMinepPist.towerdefence.translation.Play.point.name"), Style);
		points.setPosition(140, 300);
		points.setSize(20, 20);
		Label moneys = new Label(Integer.toString(this.moneys), Style);
		moneys.setPosition(260, 300);
		moneys.setSize(20, 20);
		Image moneysIm = new Image(new Texture(Gdx.files.internal("resources/images/gui/coin.jpeg")));
		moneysIm.setPosition(280, 300);
		moneysIm.setSize(16, 16);
		Pixmap pxm = new Pixmap(Gdx.graphics.getWidth(), 60, Pixmap.Format.RGBA8888);
		pxm.setColor(Color.GREEN);
		pxm.fill();
		Image im = new Image(new Texture(pxm));
		im.setPosition(0, 260);
		im.setSize(Gdx.graphics.getWidth(), 60);
		Pixmap pxmli = new Pixmap(Gdx.graphics.getWidth(), 60, Pixmap.Format.RGBA8888);
		pxmli.setColor(Color.GREEN);
		pxmli.fill();
		Image iml = new Image(new Texture(pxmli));
		iml.setSize(Gdx.graphics.getWidth(), 60);
		stage = new Stage(new ExtendViewport(480, 320));
		stage.addActor(im);
		stage.addActor(iml);
		stage.addActor(btn);
		stage.addActor(nr);
		stage.addActor(levelInt);
		stage.addActor(points);
		stage.addActor(moneys);
		stage.addActor(moneysIm);
		
	    player = new EntityObject(new Texture(Gdx.files.internal("resources/images/player.jpeg")));
		player.setPosition(0, 136);
		player.setSize(48, 48);
		this.save = PlaySave.INSTANCE;
		this.save.addEntity(player);
		p = PlayStage.DIG_PATH;
		c = DayNigthCycle.DAY;
		
		for(int i = 0; i < XMLElem.size; i++) {
			if(XMLElem.get(i).getAttributes().getNamedItem("texture").getNodeValue().equals("grass")) {
				final GameObject img = new GameObject(new Texture(Gdx.files.internal("resources/images/blocks/grass.png")));
				img.setPosition(Integer.parseInt(XMLElem.get(i).getAttributes().getNamedItem("x").getNodeValue()), Integer.parseInt(XMLElem.get(i).getAttributes().getNamedItem("y").getNodeValue()) + 40);
				img.setSize(48, 48);
				elems.add(img);
				elemsGrass.add(img);
			}
			if(XMLElem.get(i).getAttributes().getNamedItem("texture").getNodeValue().equals("dirt")) {
				final GameObject img = new GameObject(new Texture(Gdx.files.internal("resources/images/blocks/dirt.png")));
				img.setPosition(Integer.parseInt(XMLElem.get(i).getAttributes().getNamedItem("x").getNodeValue()), Integer.parseInt(XMLElem.get(i).getAttributes().getNamedItem("y").getNodeValue()) + 40);
				img.setSize(48, 48);
				elems.add(img);
			}
		}
		h = new PlayerHouse(level);
		InputMultiplexer mlp = new InputMultiplexer();
		mlp.addProcessor(this);
		mlp.addProcessor(stage);
		mlp.addProcessor(new CommonInputProcessor(CommonInputProcessor.CommonState.GAME, level));
		Gdx.input.setInputProcessor(mlp);
	}
	int t = 0;
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 1f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_ALPHA_BITS);
		stage.act(delta);
		stage.draw();
        camera.update();
        btch.setProjectionMatrix(camera.combined);
		btch.enableBlending();
		//события
		if(p == PlayStage.DIG_PATH) {
			executeDig();
			for(int i = 0; i < h.blocks.size; i++) {
				//if(i == h.indexForPlayer) {
				if(h.blocks.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
					p = PlayStage.MOB_VAWES;
				}
				//}
			}
		} else if(p == PlayStage.MOB_VAWES) {
			if(ents.size == 1 && !isVaweCarried) {
				isVaweCarried = true;
			}
			setupWave();
			executeEntities();	
			executeArrow();
			executeCollisions();
			//ents.add();
		}
		//начало рисования
		btch.begin();
		for(int i = 0; i < elems.size; i++) {
			elems.get(i).draw(btch);
		}
		h.draw(btch);
		if(!(save == null)) {
			for(int i = 0; i < save.getDispensers().size; i++) {
				save.getDispenser(i).draw(btch);
			}
			for(int i = 0; i < save.getEntities().size; i++) {
				save.getEntity(i).draw(btch);
			}
		} else {
			player.draw(btch);
		}
		if(p == PlayStage.MOB_VAWES) {
			executeClock();
			executeWave();
			if(!isVaweExec && time > (680 * 2)) {
				executeTextWave(wave[0]);
			}
			executeDispensers();
			if(playHealth < 1) {
				font1.draw(btch, "Game Over", 180, 300);
				DefenceGame.getDefenceGame().setScreen(new EndGameScreen(EndGameScreen.EndGameState.GAME_OVER));
			}
			if(bl) {
				dispanserAddGui.setSize(220, 120);
				dispanserAddGui.setPosition(12, 0);
				dispanserAddGui.draw(btch);
			}
		}
		//конец рисования
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
		save.clearEntities();
		save.clearDispensers();
	}
	//выполнение копания
	public void executeDig() {
			player.moveByMotionState();
	}
	public boolean isMoved = false;
	//выполнение сущностей
	public void executeEntities() {
		for(int i = 0; i < ents.size; i++) {
			if(ents.get(i).moveState.motionSteps == 0) {
				for(int j = 0; j < elemsGrass.size; j++) {
					if(!elemsGrass.get(j).getBoundingRectangle().contains(ents.get(i).getPosition()[0] + 48, ents.get(i).getPosition()[1])) {
						EntityObject.motionState s = EntityObject.motionState.MOTION;
						s.motionSteps = 10;
						s.stepSize = 4.8f;
						EntityObject.motionSide t = EntityObject.motionSide.EAST;
						ents.get(i).setMotionState(s, t);
						isMoved = true;
					}
					if(!elemsGrass.get(j).getBoundingRectangle().contains(ents.get(i).getPosition()[0] + 48, ents.get(i).getPosition()[1]) && !isMoved) {
						EntityObject.motionState s = EntityObject.motionState.MOTION;
						s.motionSteps = 10;
						s.stepSize = 4.8f;
						EntityObject.motionSide t = EntityObject.motionSide.WEST;
						ents.get(i).setMotionState(s, t);
						isMoved = true;
					}
					if(!elemsGrass.get(j).getBoundingRectangle().contains(ents.get(i).getPosition()[0], ents.get(i).getPosition()[1] + 48) && !isMoved) {
						EntityObject.motionState s = EntityObject.motionState.MOTION;
						s.motionSteps = 10;
						s.stepSize = 4.8f;
						EntityObject.motionSide t = EntityObject.motionSide.NORD;
						ents.get(i).setMotionState(s, t);
						isMoved = true;
					}
					if(!elemsGrass.get(j).getBoundingRectangle().contains(ents.get(i).getPosition()[0], ents.get(i).getPosition()[1] - 48) && !isMoved) {
						EntityObject.motionState s = EntityObject.motionState.MOTION;
						s.motionSteps = 10;
						s.stepSize = 4.8f;
						EntityObject.motionSide t = EntityObject.motionSide.SOUTH;
						ents.get(i).setMotionState(s, t);
						isMoved = true;
					}
				}
			}
			ents.get(i).moveByMotionState();
			isMoved = false;
		}
	}
	public void setupWaves() {
		NodeList waves = XML.getWaveSchema(XML.getLevelNode(XML.getLevelDocument(level))).getChildNodes();
		this.waves = new Array<Node>();
		for(int i = 0; i < waves.getLength(); i++) {
			if(waves.item(i).getNodeName().equals("wave")) {
				this.waves.add(waves.item(i));
			}
		}
	}
	int y = 1;
	int z = 0;
	int w = 0;
	int g = 0;
	int j = 0;
	int i = 0;
	int l = 0;
	public void setupWave() {
		if(time > (680 * 2)) {
			if(!isVawePartExec) {
				if (l < 40) {
					l += 1;
				} else {
					isVawePartExec = true;
					isVaweEnded = false;
					g = 0;
					y = 0;
					z = 0;
					int v = 0;
					for(int i = 0; i < waves.get(wave[0] - 1).getChildNodes().getLength(); i++) {
					if(waves.get(wave[0] - 1).getChildNodes().item(i).getNodeName().equals("wavePart")) {
						v += 1;
					}
				}
				if(wave[1] < v) {
					wave[1] += 1;
					ents.clear();
				} else {
					isVaweExec = false;
				}
				l = 0;
			}
		}
		if(!isVaweExec) {
			if(l < 24) {
				l += 1;
			} else {
				isVaweExec = true;
				if(wave[0] < waves.size) {
					wave[1] = 0;
					wave[0] += 1;
				}
				isVawePartExec = false;
				ents.clear();
				l = 0;
			}
		}
		if(!isVaweEnded && time > (680 * 2) && isVaweExec && isVawePartExec) {
			if(wave[0] < waves.size) {
				if(i < waves.get(wave[0] - 1).getChildNodes().getLength()) {
					if(waves.get(wave[0] - 1).getChildNodes().item(i).getNodeName().equals("wavePart")) {
						if(j < waves.get(wave[0] - 1).getChildNodes().item(i).getChildNodes().getLength()) {
							if(waves.get(wave[0] - 1).getChildNodes().item(i).getChildNodes().item(j).getNodeName().equals("mobsList")) {
								if(g % 25 == 0) {
									if(y <= Integer.parseInt(waves.get(wave[0] - 1).getChildNodes().item(i).getChildNodes().item(j).getAttributes().getNamedItem("zombieNumber").getNodeValue())) {
										EntityObject o = new EntityObject(new Texture(Gdx.files.internal("resources/images/player.jpeg")));
										o.setSize(48, 48);
										o.setPosition(0, 136);
										ents.add(o);
										y += 1;
									}
								}
								g += 1;
								z += 1;
							} else {
								j += 1;
							}
						}
					} else {
						i += 1;
					}
					
				}
			}
		}
	} else {
		l = 0;
	}
	}
	int er = 0;
	public void executeWave() {
		for(int i = 0; i < ents.size; i++) {
			ents.get(i).draw(btch);
		}
		if(ents.size == 0 && isVaweCarried) {
			isVawePartExec = false;
		}
		for(int i = 0; i < arrows.size; i++) {
			arrows.get(i).draw(btch);
		}
	}
	//обработка коллизий
	public void executeCollisions() {
		for(int i = 0; i < ents.size; i++) {
			if(player.getBoundingRectangle().overlaps(ents.get(i).getBoundingRectangle())) {
				ents.removeValue(ents.get(i), false);
				playHealth -= 2;
			}
		}
		boolean y = false;
		if(ents.size != 0) {
			for(int i = 0; ; i++) {
				if(arrows.size != 0) {
					for(int j = 0; ; j++) {
						if(j < arrows.size && i < ents.size) {
							if(ents.get(i).getBoundingRectangle().contains(arrows.get(j).getBoundingRectangle())) {
								android.util.Log.e("re", "ui");
								arrows.removeValue(arrows.get(j), false);
								ents.removeValue(ents.get(i), false);
							}
						} else {
							y = true;
						}
						if(y) {
							break;
						}
					}
				}
				if(y) {
					break;
				}
			}
		}
	}
	//текст волны
	public void executeTextWave(int waveNumber) {
		font1.draw(btch, "Wave " + Integer.toString(waveNumber), 180, 300);
	}
	//часы
	public void executeClock() {
		if(time < (20 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_00.png")));
		} else if(time > (20 * 2) && time < (40 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_01.png")));
		} else if(time > (40 * 2) && time < (60 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_02.png")));
		} else if(time > (60 * 2) && time < (80 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_03.png")));
		} else if(time > (80 * 2) && time < (100 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_04.png")));
		} else if(time > (100 * 2) && time < (120 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_05.png")));
		} else if(time > (120 * 2) && time < (140 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_06.png")));
		} else if(time > (140 * 2) && time < (160 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_07.png")));
		} else if(time > (160 * 2) && time < (180 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_08.png")));
		} else if(time > (180 * 2) && time < (200 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_09.png")));
		} else if(time > (200 * 2) && time < (220 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_10.png")));
		} else if(time > (220 * 2) && time < (240 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_11.png")));
		} else if(time > (240 * 2) && time < (260 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_12.png")));
		} else if(time > (260 * 2) && time < (280 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_13.png")));
		} else if(time > (280 * 2) && time < (300 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_14.png")));
		} else if(time > (300 * 2) && time < (320 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_15.png")));
		} else if(time > (320 * 2) && time < (340 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_16.png")));
		} else if(time > (340 * 2) && time < (360 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_17.png")));
		} else if(time > (360 * 2) && time < (380 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_18.png")));
		} else if(time > (380 * 2) && time < (400 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_19.png")));
		} else if(time > (400 * 2) && time < (420 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_20.png")));
		} else if(time > (420 * 2) && time < (440 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_21.png")));
		} else if(time > (440 * 2) && time < (460 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_22.png")));
		} else if(time > (460 * 2) && time < (480 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_23.png")));
		} else if(time > (480 * 2) && time < (500 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_24.png")));
		} else if(time > (500 * 2) && time < (520 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_25.png")));
		} else if(time > (520 * 2) && time < (540 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_26.png")));
		} else if(time > (540 * 2) && time < (560 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_27.png")));
		} else if(time > (560 * 2) && time < (580 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_28.png")));
		} else if(time > (580 * 2) && time < (600 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_29.png")));
		} else if(time > (600 * 2) && time < (620 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_30.png")));
		} else if(time > (620 * 2) && time < (640 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_31.png")));
		} else if(time > (640 * 2) && time < (660 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_32.png")));
		} else if(time > (660 * 2) && time < (680 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_33.png")));
		} else if(time > (680 * 2) && time < (700 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_34.png")));
		} else if(time > (700 * 2) && time < (720 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_35.png")));
		} else if(time > (720 * 2) && time < (740 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_36.png")));
		} else if(time > (740 * 2) && time < (760 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_37.png")));
		} else if(time > (760 * 2) && time < (780 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_38.png")));
		} else if(time > (780 * 2) && time < (800 *2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_39.png")));
		} else if(time > (800 * 2) && time < (820 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_40.png")));
		} else if(time > (820 * 2) && time < (840 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_41.png")));
		} else if(time > (840 * 2) && time < (860  * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_42.png")));
		} else if(time > (860 * 2) && time < (880 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_43.png")));
		} else if(time > (880 * 2) && time < (900 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_44.png")));
		} else if(time > (900 * 2) && time < (920 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_45.png")));
		} else if(time > (920 * 2) && time < (940 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_46.png")));
		} else if(time > (940 * 2) && time < (960 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_47.png")));
		} else if(time > (960 * 2) && time < (980 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_48.png")));
		} else if(time > (980 * 2) && time < (1000 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_49.png")));
		} else if(time > (1000 * 2) && time < (1020 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_50.png")));
		} else if(time > (1020 * 2) && time < (1040 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_51.png")));
		} else if(time > (1040 * 2) && time < (1060 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_52.png")));
		} else if(time > (1060 * 2) && time < (1080 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_53.png")));
		} else if(time > (1080 * 2) && time < (1100 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_54.png")));
		} else if(time > (1100 * 2) && time < (1120 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_55.png")));
		} else if(time > (1120 * 2) && time < 1140) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_56.png")));
		} else if(time > (1140 * 2) && time < (1160 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_57.png")));
		} else if(time > (1160 * 2) && time < (1180 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_58.png")));
		} else if(time > (1180 * 2) && time < (1200 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_59.png")));
		} else if(time > (1200 * 2) && time < (1220 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_60.png")));
		} else if(time > (1220 * 2) && time < (1240 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_61.png")));
		} else if(time > (1240 * 2) && time < (1260 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_62.png")));
		} else if(time > (1260 * 2) && time < (1280 * 2)) {
			clock.setTexture(new Texture(Gdx.files.internal("resources/images/gui/clock/clock_63.png")));
		}
		clock.draw(btch);
		if(time > (640 * 2) && time < (660 * 2)) {
			transNIGHT1.draw(btch);
		} else if(time > (660 * 2) && time < (680 *2)) {
			transNIGHT2.draw(btch);
		} else if(time > (680 * 2) && time < (700 * 2)) {
			transNIGHT3.draw(btch);
		} else if(time > (700 * 2) && time < (720 * 2)) {
			transNIGHT4.draw(btch);
		} else if(time > (720 * 2) && time < (740 * 2)) {
			transNIGHT5.draw(btch);
		} else if(time > (740 * 2)) {
			transNIGHT6.draw(btch);
		} else if(time < 40) {
			transNIGHT5.draw(btch);
		} else if(time < 80) {
			transNIGHT4.draw(btch);
		} else if(time < 120) {
			transNIGHT3.draw(btch);
		} else if(time < 160) {
			transNIGHT2.draw(btch);
		} else if(time < 200) {
			transNIGHT1.draw(btch);
		}
		if(time >= (1280 * 2)) {
			time = 0;
		}
		time += 1;
	}
	int[][] coords = {{0, -10}, {48, -10}, {96, -10}, {144, -10}, {192, -10}, {240, -10}, {288, -10}, {336, -10}, {384, -10}, {432, -10},
		{0, 38}, {48, 38}, {96, 38}, {144, 38}, {192, 38}, {240, 38}, {288, 38}, {336, 38}, {384, 38}, {432, 38},
		{0, 86}, {48, 86}, {96, 86}, {144, 86}, {192, 86}, {240, 86}, {288, 86}, {336, 86}, {384, 86}, {432, 86},
		{0, 134}, {48, 134}, {96, 134}, {144, 134}, {192, 134}, {240, 134}, {288, 134}, {336, 134}, {384, 134}, {432, 134},
		{0, 182}, {48, 182}, {96, 182}, {144, 182}, {192, 182}, {240, 182}, {288, 182}, {336, 182}, {384, 182}, {432, 182},
		{0, 230}, {48, 230}, {96, 230}, {144, 230}, {192, 230}, {240, 230}, {288, 230}, {336, 230}, {384, 230}, {432, 230},
		{0, 278}, {48, 278}, {96, 278}, {144, 278}, {192, 278}, {240, 278}, {288, 278}, {336, 278}, {384, 278}, {432, 278},
		
		};
	public void setDispenser(int pointerX, int pointerY) {
		float tmpPointerX = 0, tmpPointerY = 0;
		boolean e = false;
		for(int i = 0; i < coords.length; i++) {
			for(int l = 0; l < 48; l++) {
				for(int f = 0; f < 48; f++) {
					if(pointerX - l == coords[i][0] && pointerY - f == coords[i][1]) {
						tmpPointerX = coords[i][0];
						tmpPointerY = coords[i][1];
						e = true;
					}
					if(e) break;
				}
				if(e) break;
			}
			if(e) break;
		}
		for(int i = 0; i < elemsGrass.size; i++) {
			if(elemsGrass.get(i).getBoundingRectangle().contains(tmpPointerX, tmpPointerY)) {
				DispenserObject obj = new DispenserObject(DispenserObject.DispenserTypes.SLAB_WOODEN_DISPENSER);
				obj.setPosition(tmpPointerX, tmpPointerY);
				obj.setSize(48, 48);
				dispensers.add(obj);
			}
		}
	}
	public void executeDispensers() {
		for(int i = 0; i < dispensers.size; i++) {
			dispensers.get(i).draw(btch);
		}
	}
	public void addArrow() {
		for(int i = 0; i < dispensers.size; i++) {
			for(int j = 0; j < ents.size; j++) {
				if(ents.get(j).getBoundingRectangle().contains(dispensers.get(i).getPosition()[0] + 48, dispensers.get(i).getPosition()[1])) {
					ArrowObject obj = new ArrowObject(new Texture(Gdx.files.internal("resources/images/items/arrow.jpeg")));
					obj.setPosition(dispensers.get(i).getPosition()[0] + 1, dispensers.get(i).getPosition()[1]);
					obj.setSize(48, 48);
					arrows.add(obj);
				}
				if(ents.get(j).getBoundingRectangle().contains(dispensers.get(i).getPosition()[0] - 48, dispensers.get(i).getPosition()[1])) {
					ArrowObject obj = new ArrowObject(new Texture(Gdx.files.internal("resources/images/items/arrow.jpeg")));
					obj.setPosition(dispensers.get(i).getPosition()[0] - 1, dispensers.get(i).getPosition()[1]);
					obj.setSize(48, 48);
					arrows.add(obj);
				}
				if(ents.get(j).getBoundingRectangle().contains(dispensers.get(i).getPosition()[0], dispensers.get(i).getPosition()[1] + 68)) {
					ArrowObject obj = new ArrowObject(new Texture(Gdx.files.internal("resources/images/items/arrow.jpeg")));
					obj.setPosition(dispensers.get(i).getPosition()[0], dispensers.get(i).getPosition()[1] + 1);
					obj.setSize(48, 48);
					arrows.add(obj);
				}
				if(ents.get(j).getBoundingRectangle().contains(dispensers.get(i).getPosition()[0], dispensers.get(i).getPosition()[1] - 68)) {
					ArrowObject obj = new ArrowObject(new Texture(Gdx.files.internal("resources/images/items/arrow.jpeg")));
					obj.setPosition(dispensers.get(i).getPosition()[0], dispensers.get(i).getPosition()[1] - 1);
					obj.setSize(48, 48);
					arrows.add(obj);
				}
			}
		}
	}
	int m = 0;
	public void executeArrow() {
		if(m >= 6) {
			addArrow();
			m = 0;
		} else {
			m += 1;
		}
		for(int i = 0; i < arrows.size; i++) {
			arrows.get(i).setPosition(arrows.get(i).getPosition()[0], arrows.get(i).getPosition()[1] + 2);
		}
	}
	@Override
	public boolean keyDown(int p1) {
		// TODO: Implement this method
		return false;
	}
	@Override
	public boolean keyUp(int p1) {
		// TODO: Implement this method
		return false;
	}
	@Override
	public boolean keyTyped(char p1) {
		// TODO: Implement this method
		return false;
	}
	boolean bl = false;
	boolean blraw = false;
	@Override
	public boolean touchDown(int p1, int p2, int p3, int p4) {
		float pointerX = InputTransform.getCursorToModelX(Gdx.graphics.getWidth(), p1); 
		float pointerY = InputTransform.getCursorToModelY(Gdx.graphics.getHeight(), p2);
		if(p4 == Input.Buttons.LEFT) {
			if(player.getBoundingRectangle().contains(pointerX - 48, pointerY)) {
				android.util.Log.e("movp!!!", Float.toString(InputTransform.getCursorToModelX(Gdx.graphics.getWidth(), (int)player.getPosition()[0])));
				EntityObject.motionState s = EntityObject.motionState.MOTION;
				s.motionSteps = 5;
				s.stepSize = 9.6f;
				EntityObject.motionSide t = EntityObject.motionSide.EAST;
				player.setMotionState(s, t);
				for(int i = 0; i < elems.size; i++) {
					if(elems.get(i).getBoundingRectangle().contains(pointerX, pointerY)) {
						android.util.Log.e("movp!!!", Float.toString(InputTransform.getCursorToModelX(Gdx.graphics.getWidth(), (int)player.getPosition()[0])));
						elems.get(i).setTexture(new Texture(Gdx.files.internal("resources/images/blocks/dirt.png")));
					}
				}
			} else if(player.getBoundingRectangle().contains(pointerX, pointerY + 48)) {
				EntityObject.motionState s = EntityObject.motionState.MOTION;
				s.motionSteps = 5;
				s.stepSize = 9.6f;
				EntityObject.motionSide t = EntityObject.motionSide.SOUTH;
				player.setMotionState(s, t);
				for(int i = 0; i < elems.size; i++) {
					if(elems.get(i).getBoundingRectangle().contains(pointerX, pointerY)) {
						android.util.Log.e("movp!!!", Float.toString(InputTransform.getCursorToModelX(Gdx.graphics.getWidth(), (int)player.getPosition()[0])));
						elems.get(i).setTexture(new Texture(Gdx.files.internal("resources/images/blocks/dirt.png")));
					}
				}
			} else if(player.getBoundingRectangle().contains(pointerX, pointerY - 48)) {
				EntityObject.motionState s = EntityObject.motionState.MOTION;
				s.motionSteps = 5;
				s.stepSize = 9.6f;
				EntityObject.motionSide t = EntityObject.motionSide.NORD;
				player.setMotionState(s, t);
				for(int i = 0; i < elems.size; i++) {
					if(elems.get(i).getBoundingRectangle().contains(pointerX, pointerY)) {
						android.util.Log.e("movp!!!", Float.toString(InputTransform.getCursorToModelX(Gdx.graphics.getWidth(), (int)player.getPosition()[0])));
						elems.get(i).setTexture(new Texture(Gdx.files.internal("resources/images/blocks/dirt.png")));
					}
				}
			} else if(player.getBoundingRectangle().contains(pointerX + 48, pointerY)) {
				EntityObject.motionState s = EntityObject.motionState.MOTION;
				s.motionSteps = 5;
				s.stepSize = 9.6f;
				EntityObject.motionSide t = EntityObject.motionSide.WEST;
				player.setMotionState(s, t);
				for(int i = 0; i < elems.size; i++) {
					if(elems.get(i).getBoundingRectangle().contains(pointerX, pointerY)) {
						android.util.Log.e("movp!!!", Float.toString(InputTransform.getCursorToModelX(Gdx.graphics.getWidth(), (int)player.getPosition()[0])));
						elems.get(i).setTexture(new Texture(Gdx.files.internal("resources/images/blocks/dirt.png")));
					}
				}
			}
			if(p == PlayStage.MOB_VAWES) {
				for(int i = 0; i < elemsGrass.size; i++) {
					if(elemsGrass.get(i).getBoundingRectangle().contains(pointerX, pointerY)) {
						if(bl) {
							bl = false;
						} else {
							dispanserAddGui.setXY(pointerX, pointerY);
							bl = true;
						}
					}
				}
				dispanserAddGui.clickSlabWoodenDisp(pointerX, pointerY);
			}
		}
		return false;
	}
	@Override
	public boolean touchUp(int p1, int p2, int p3, int p4) {
		// TODO: Implement this method
		return false;
	}
	@Override
	public boolean touchDragged(int p1, int p2, int p3) {
		// TODO: Implement this method
		return false;
	}
	@Override
	public boolean mouseMoved(int p1, int p2) {
		// TODO: Implement this method
		return false;
	}
	@Override
	public boolean scrolled(int p1) {
		// TODO: Implement this method
		return false;
	}
}
class InputStub {
	void toStub() {
		Gdx.graphics.getWidth();
	}
}
class InputTransform extends InputStub { 
	public static float getCursorToModelX(int screenX, int cursorX) { 
		return (((float)cursorX) * 480) / ((float)screenX); 
	} 
	public static float getCursorToModelY(int screenY, int cursorY) { 
		return ((float)(screenY - cursorY)) * 320 / ((float)screenY); 
	} 
}
