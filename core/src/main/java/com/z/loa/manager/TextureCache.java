package com.z.loa.manager;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;

public class TextureCache {
	private static final ObjectMap<String, Texture> textureMap = new ObjectMap<>();
	private static final ObjectMap<String, TextureRegion> textureRegionMap = new ObjectMap<>();
	private static final ObjectMap<String, TextureRegion[][]> splitMap = new ObjectMap<>();
	
	public static TextureRegion[][] getSplit(String file_name) {
		if (splitMap.containsKey(file_name)) {
			return splitMap.get(file_name);
		}

		Texture texture = textureMap.get(file_name);
		if (texture == null) {
			texture = new Texture(Gdx.files.internal("man/" + file_name + ".png"));
			texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
			textureMap.put(file_name, texture);
		}
		TextureRegion[][] split = TextureRegion.split(texture, texture.getWidth() / 3, texture.getHeight() / 4);
		splitMap.put(file_name, split);
		return split;
	}
	
	public static TextureRegion[][] getSplit(String file_name, int column_count) {
		if (splitMap.containsKey(file_name)) {
			return splitMap.get(file_name);
		}

		Texture texture = textureMap.get(file_name);
		if (texture == null) {
			texture = new Texture(Gdx.files.internal("effect/" + file_name + ".png"));
			texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
			textureMap.put(file_name, texture);
		}
		TextureRegion[][] split = TextureRegion.split(texture, texture.getWidth() / column_count, texture.getHeight());
	
		splitMap.put(file_name, split);
		return split;
	}
	
	public static TextureRegion[][] getSplit(String file_name, int column_count, int line_count) {
		if (splitMap.containsKey(file_name)) {
			return splitMap.get(file_name);
		}

		Texture texture = textureMap.get(file_name);
		if (texture == null) {
			texture = new Texture(Gdx.files.internal(file_name + ".png"));
			texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
			textureMap.put(file_name, texture);
		}
		TextureRegion[][] split = TextureRegion.split(texture, texture.getWidth() / column_count, texture.getHeight() / line_count);

		splitMap.put(file_name, split);
		return split;
	}

	public static TextureRegion getAvatarTextureRegion(String avatar) {
		if (avatar == null) {
			return null;
		}
		if (textureRegionMap.containsKey(avatar)) {
			return textureRegionMap.get(avatar);
		}
		Texture texture = textureMap.get(avatar);
		if (texture == null) {
			texture = new Texture(Gdx.files.internal("avatar/" + avatar + ".png"));
			texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
			textureMap.put(avatar, texture);
		}
		TextureRegion region = new TextureRegion(texture);
		textureRegionMap.put(avatar, region);
		return region;
	}

	public static void dispose() {
		for (Texture t : textureMap.values()) {
			t.dispose();
		}
		textureMap.clear();
		splitMap.clear();
		textureRegionMap.clear();
	}
}

