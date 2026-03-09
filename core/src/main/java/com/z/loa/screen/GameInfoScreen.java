package com.z.loa.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.viewport.*;
import com.z.loa.*;

public class GameInfoScreen implements Screen {
	private Stage stage;
	private MyGdxGame game;
	private TitleScreen screen;
	private TextButton backTitleBtn;
	private Table rootTable;
	private boolean initialized;

	public GameInfoScreen(MyGdxGame game, TitleScreen previous_screen) {
		this.game = game;
		this.screen = previous_screen;
		stage = new Stage(new ScreenViewport());
		rootTable = new Table();
	}

	public void init() {
		Texture sys_set = new Texture(Gdx.files.internal("sys_set.png"));
		TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(sys_set));
		TextButton.TextButtonStyle style_t_btn = new TextButton.TextButtonStyle();
		style_t_btn.font = TitleScreen.font;
		style_t_btn.fontColor = Color.WHITE;
		backTitleBtn = new TextButton("返回", style_t_btn);
		backTitleBtn.setPosition(Gdx.graphics.getWidth() * 2 / 3, Gdx.graphics.getHeight() * 3 / 9);
		backTitleBtn.setSize(250, 50);
		backTitleBtn.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				
				game.setScreen(screen);
				return true;

			}
		});
		rootTable.add(backTitleBtn).width(100f);
		rootTable.setBackground(drawable);
		rootTable.setPosition(Gdx.graphics.getWidth() * 2 / 15, Gdx.graphics.getHeight() * 45 / 152);
		rootTable.setSize(Gdx.graphics.getWidth() * 80 / 108, Gdx.graphics.getHeight() * 67 / 152);
		//rootTable.setFillParent(true);
		stage.addActor(rootTable);
		initialized = true;
	}
	
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void hide() {
		// TODO: Implement this method
	}

	@Override
	public void pause() {
		// TODO: Implement this method
	}

	@Override
	public void render(float p) {
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int p, int p1) {
		// TODO: Implement this method
	}

	@Override
	public void resume() {
		// TODO: Implement this method
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

}

