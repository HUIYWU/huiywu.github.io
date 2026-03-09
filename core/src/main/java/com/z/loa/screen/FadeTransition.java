package com.z.loa.screen;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class FadeTransition {
	private Stage stage;
	private Image overlay;

	public FadeTransition(Stage stage) {
		this.stage = stage;
		createOverlay();
	}

	public void setOverlay(Color color) {
		overlay.setColor(color);
	}

	public Image getOverlay() {
		return overlay;
	}

	private void createOverlay() {
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		overlay = new Image(new Texture(pixmap));
		pixmap.dispose();

		overlay.setSize(stage.getWidth(), stage.getHeight());
		overlay.setColor(1.0f, 1.0f, 1.0f, 0);
		overlay.setVisible(false);
		overlay.setZIndex(9); // 确保在最顶层

		stage.addActor(overlay);
	}

	public void fadeOut(float duration, final Runnable onComplete) {
		overlay.clearActions();
		overlay.getColor().a = 0; // alpha值，即透明度
		overlay.setVisible(true);

		overlay.addAction(Actions.sequence(Actions.fadeIn(duration), // 淡入到不透明
				Actions.run(new Runnable() {
					@Override
					public void run() {
						if (onComplete != null)
							onComplete.run();
					}
				})));
	}

	public void fadeIn(float duration, final Runnable onComplete) {
		overlay.clearActions();
		overlay.getColor().a = 1;
		overlay.setVisible(true);

		overlay.addAction(Actions.sequence(Actions.fadeOut(duration), // 淡出到透明
				Actions.run(new Runnable() {
					@Override
					public void run() {
						overlay.setVisible(false);
						if (onComplete != null) {
							onComplete.run();
						}
					}
				})));
	}

	//	public void fadeScreen(final Game game, final Screen fromScreen, final Screen toScreen, final float duration) {
	//		fadeOut(duration / 2, new Runnable() {
	//			@Override
	//			public void run() {
	//				// 切换屏幕
	//				fromScreen.dispose();
	//				game.setScreen(toScreen);
	//
	//				// 淡入显示新屏幕
	//				fadeIn(duration / 2, null);
	//			}
	//		});
	//	}
}

