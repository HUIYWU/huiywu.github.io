package com.z.loa.entity.enemy;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.z.loa.Constants;
import com.z.loa.entity.BaseEntity;

public class ZhanTingyi extends BaseEntity{
   private Array<TextureAtlas.AtlasRegion> await;
	private TextureAtlas atlas;

	public ZhanTingyi() {
		this(0, 0);
	}

	public ZhanTingyi(float x, float y) {
		super();
		this.setPosition(x, y);
        super.name = "zhan_tingyi";
		super.maxHp = 9900;
		super.maxMp = 4200;
		super.remainHp  = 9900;
		super.remainMp = 4200;
	}

	public void init() {
		atlas = new TextureAtlas("battle/enemy/packer-11.atlas");
		battleAnimation = new Animation[4];
		await = atlas.findRegions("1528001");
		battleAnimation[0] = new Animation<TextureRegion>(0.3f, await);
		battleAnimation[0].setPlayMode(Animation.PlayMode.LOOP);
		this.setSize(Constants.WIDTH * 79 / 108 / 176 * 81, Constants.HEIGHT * 33 / 76 / 220 * 99);
	}
}
