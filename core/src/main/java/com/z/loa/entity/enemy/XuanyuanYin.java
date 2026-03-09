package com.z.loa.entity.enemy;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.z.loa.*;
import com.z.loa.entity.*;

public class XuanyuanYin extends BaseEntity {
	private Array<TextureAtlas.AtlasRegion> await;
	private TextureAtlas atlas;

	public XuanyuanYin() {
		this(0, 0);
	}

	public XuanyuanYin(float x, float y) {
		super();
		this.setPosition(x, y);
        super.name = "xuanyuan_yin";
		super.maxHp  = 9800;
		super.maxMp = 4000;
		super.remainHp  = 9800;
		super.remainMp = 4000;
	}

	public void init() {
		atlas = new TextureAtlas("battle/enemy/packer-7.atlas");
		battleAnimation = new Animation[4];
		await = atlas.findRegions("1501002_await");
		battleAnimation[0] = new Animation<TextureRegion>(0.3f, await);
		battleAnimation[0].setPlayMode(Animation.PlayMode.LOOP);
		this.setSize(Constants.WIDTH * 79 / 108 / 176 * 52, Constants.HEIGHT * 33 / 76 / 220 * 69);
	}

	public Array<TextureAtlas.AtlasRegion> getAwait() {
		return await;
	}
}
