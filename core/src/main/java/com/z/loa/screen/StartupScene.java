package com.z.loa.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.z.loa.*;

public class StartupScene implements Screen {
	private Texture libGDX, e7play;
	private Music logoMusic;
	private TextureRegion[] startupFrame;
	private Animation<TextureRegion> startupAnimation;
	private SpriteBatch batch;
	private boolean showFirst = true;
	private boolean animationStarted = false;
	private boolean logoMusicPlayed = false;
	private float stateTime;
	private MyGdxGame game;
	private TitleScreen screen;

	public StartupScene(MyGdxGame game) {
		this.game = game;
		this.screen = new TitleScreen(game);
		batch = new SpriteBatch();
		libGDX = new Texture(Gdx.files.internal("libGDX.png"));
		e7play = new Texture(Gdx.files.internal("e7play.png"));
		logoMusic = Gdx.audio.newMusic(Gdx.files.internal("logo.mid"));
		TextureRegion[][] temp = TextureRegion.split(e7play, e7play.getWidth() / 19, e7play.getHeight());
		startupFrame = new TextureRegion[19];
		for (int i = 0; i < 19; i++) {
			startupFrame[i] = temp[0][i];
		}
		startupAnimation = new Animation<TextureRegion>(0.2f, startupFrame);
		startupAnimation.setPlayMode(Animation.PlayMode.NORMAL);

	}

	@Override
	public void dispose() {
		if (libGDX != null) {
			libGDX.dispose();
		}
		if (e7play != null) {
			e7play.dispose();
		}
		if (logoMusic != null) {
			logoMusic.dispose();
		}
	}

	@Override
	public void hide() {
		this.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void render(float p) {
		stateTime += Gdx.graphics.getDeltaTime();
		batch.begin();
		if (stateTime > 2.8f && !animationStarted) {
			showFirst = false;
			animationStarted = true;
			stateTime = 0f;
		}
		if (showFirst) {
			batch.setColor(1, 1, 1, 2 - stateTime);
			batch.draw(libGDX, Constants.WIDTH * 2 / 27, Constants.HEIGHT * 2 / 5, Constants.WIDTH * 23 / 27,
					Constants.WIDTH * 23 / 27 * 0.504f);
		} else {
			if (startupAnimation.isAnimationFinished(stateTime)) {
				screen.init();
				game.setScreen(screen);
			}
			stateTime += Gdx.graphics.getDeltaTime();
			TextureRegion current_frame = startupAnimation.getKeyFrame(stateTime);

			batch.setColor(1, 1, 1, 1);
			if (!logoMusicPlayed) {
				logoMusic.play();
				logoMusicPlayed = true;
			}
			batch.draw(current_frame, Constants.WIDTH / 2 - 153, Constants.HEIGHT / 2 - 51.5f, 306, 138);
		}
		batch.end();
	}

	@Override
	public void resize(int p, int p1) {

	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {

	}

}

