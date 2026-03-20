package com.z.loa.entity.enemy;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.z.loa.Constants;
import com.z.loa.entity.BaseEntity;

public class Kui extends EnemyEntity {
   private Array<TextureAtlas.AtlasRegion> await;
	private TextureAtlas atlas;

	public Kui() {
		this(0, 0);
	}

	public Kui(float x, float y) {
		super();
		this.setPosition(x, y);
        super.name = "kui";
		super.maxHp  = 9800;
		super.maxMp = 4000;
		super.remainHp  = 9800;
		super.remainMp = 4000;
	}

	public void init() {
		atlas = new TextureAtlas("battle/enemy/packer-10.atlas");
		battleAnimation = new Animation[4];
		await = atlas.findRegions("1502057");
		battleAnimation[0] = new Animation<TextureRegion>(0.3f, await);
		battleAnimation[0].setPlayMode(Animation.PlayMode.LOOP);
		this.setSize(Constants.WIDTH * 79 / 108 / 176 * 64, Constants.HEIGHT * 33 / 76 / 220 * 100);
	}
}
