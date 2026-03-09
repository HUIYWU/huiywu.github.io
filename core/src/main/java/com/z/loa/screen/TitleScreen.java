package com.z.loa.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.*;
import com.z.loa.*;

public class TitleScreen implements Screen {
	private MyGdxGame game;
	private GameScreen gameScreen;
	private GameInfoScreen gameInfoScreen;
	private Stage stage;
	private SpriteBatch batch;
	private TextButton startGameBtn, resumeGameBtn;
	private TextButton gameInfoBtn, exitGameBtn;
	private static boolean titleMusicPlayed = false;
	private float drop_time = 0;
	private Texture title, gold;
	private TextureRegion[] goldFrame;
	private Array<Sprite> goldSprites;
	public static final Music TITLEMUSIC;
	public static BitmapFont font;
	
	//private ShapeRenderer debugRenderer;

	static {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zhengti.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 45;
		parameter.color = Color.WHITE;
		parameter.borderWidth = 0.5f;
		parameter.characters += "苍神录游戏信息开始冒险继续退出起始返回";
		font = generator.generateFont(parameter);
		TITLEMUSIC = Gdx.audio.newMusic(Gdx.files.internal("title.mid"));
		TITLEMUSIC.setLooping(true);
	}

	public TitleScreen(MyGdxGame game) {
		this.game = game;
	}
	
	public void cGameS() {
		gameScreen = new GameScreen(game,this);
	}

	public void init() {
		this.gameScreen = new GameScreen(game, this);
		this.gameInfoScreen = new GameInfoScreen(game, this);
		this.stage = new Stage(new ScreenViewport());
		this.batch = new SpriteBatch();
		TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("frame320.png")));
		this.title = new Texture(Gdx.files.internal("title.png"));
		this.gold = new Texture(Gdx.files.internal("gold.png"));
		Image fi = new Image(tr);
		fi.setScaling(Scaling.fill);
		fi.setFillParent(true);
		this.game.getTransStage().addActor(fi);
		TextureRegion[][] temp = TextureRegion.split(gold, gold.getWidth() / 5, gold.getHeight());
		this.goldFrame = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			goldFrame[i] = temp[0][i];
		}
		createButton();
	}
	

	public void createButton() {
		TextButton.TextButtonStyle style_t_btn = new TextButton.TextButtonStyle();
		style_t_btn.font = font;
		style_t_btn.fontColor = Color.BLACK;
		startGameBtn = new TextButton("开始冒险", style_t_btn);
		startGameBtn.setPosition(Constants.WIDTH * 2 / 15, Constants.HEIGHT * 100 / 305 + 150);
		startGameBtn.setSize(250, 50);
		startGameBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				cGameS();
				if (!gameScreen.isInitializad()) {
					gameScreen.init();
				}
				
				game.changeScreenWithFade(gameScreen, 0.5f);
				return true;

			}
		});
		resumeGameBtn = new TextButton("继续冒险", style_t_btn);
		resumeGameBtn.setPosition(Constants.WIDTH * 2 / 15, Constants.HEIGHT * 100 / 305 + 100);
		resumeGameBtn.setSize(250, 50);
		resumeGameBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;

			}

		});
		gameInfoBtn = new TextButton("游戏信息", style_t_btn);
		gameInfoBtn.setPosition(Constants.WIDTH * 2 / 15, Constants.HEIGHT * 100 / 305 + 50);
		gameInfoBtn.setSize(250, 50);
		gameInfoBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (!gameInfoScreen.isInitialized()) {
					gameInfoScreen.init();
				}
				game.setScreen(gameInfoScreen);
				return true;

			}
		});
		exitGameBtn = new TextButton("退出游戏", style_t_btn);
		exitGameBtn.setPosition(Constants.WIDTH * 2 / 15, Constants.HEIGHT * 45 / 152);
		exitGameBtn.setSize(250, 50);
		exitGameBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.exit();
				return true;

			}
		});

		stage.addActor(startGameBtn);
		stage.addActor(resumeGameBtn);
		stage.addActor(gameInfoBtn);
		//stage.addActor(exitGameBtn);
		goldSprites = new Array<>();
		createGold();

	}

	public void createGold() {
		Sprite gold_sprite = new Sprite(goldFrame[MathUtils.random(4)]);
		gold_sprite.setSize(60, 60);
		if (MathUtils.random(2) == 1) {
			gold_sprite.setX(MathUtils.random(0f, Constants.WIDTH));
			gold_sprite.setY(Constants.HEIGHT * 111 / 152);
		} else {
			gold_sprite.setX(Constants.WIDTH * 2 / 15);
			gold_sprite.setY(MathUtils.random(Constants.HEIGHT * 45 / 152, Constants.HEIGHT * 111 / 152));
		}

		goldSprites.add(gold_sprite);

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	//	private void drawDebugBounds() {
	//		if (debugRenderer == null) {
	//			debugRenderer = new ShapeRenderer();
	//		}
	//
	//		
	//		debugRenderer.begin(ShapeRenderer.ShapeType.Line);
	//		debugRenderer.setColor(Color.BLACK);
	//		debugRenderer.rect(Gdx.graphics.getWidth() * 2 / 15, Gdx.graphics.getHeight() * 45 / 152,
	//				Gdx.graphics.getWidth() * 79 / 108, Gdx.graphics.getHeight() * 33 / 76);
	//
	//		debugRenderer.end();
	//	}

	@Override
	public void render(float p) {
		//drawDebugBounds();
		
		batch.begin();
		batch.draw(title, Gdx.graphics.getWidth() * 2 / 15, Gdx.graphics.getHeight() * 45 / 152,
				Gdx.graphics.getWidth() * 79 / 108, Gdx.graphics.getHeight() * 33 / 76);

		if (!titleMusicPlayed) {
			TITLEMUSIC.play();
			titleMusicPlayed = true;
		}

		float delta = Gdx.graphics.getDeltaTime();
		try {
			for (int i = goldSprites.size - 1; i >= 0; i--) {
				Sprite gold_sprite = goldSprites.get(i);
				gold_sprite.translateY(MathUtils.random(-80, -60) * delta);
				gold_sprite.translateX(MathUtils.random(60, 80) * delta);
				if (gold_sprite.getY() < Constants.HEIGHT * 2 / 9 || gold_sprite.getX() > Constants.WIDTH) {
					goldSprites.removeIndex(i);
				}
			}
		} catch (NullPointerException n) {
			createGold();
		}
		drop_time += delta;
		if (drop_time > 0.5f) {
			drop_time = 0f;
			createGold();
		}
		for (Sprite gold_sprite : goldSprites) {
			gold_sprite.draw(batch);
		}

		batch.end();
		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int p, int p1) {
		// TODO: Implement this method
	}

	@Override
	public void pause() {
		// TODO: Implement this method
	}

	@Override
	public void hide() {
		// TODO: Implement this method
	}
	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void resume() {
		// TODO: Implement this method
	}

}

