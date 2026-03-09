package com.z.loa.manager;

import com.badlogic.gdx.*;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.math.*;

public class MapsManager {
	private float width, height;
	private TmxMapLoader tmxMapLoader;
	public static TiledMap map;
	public static MapObjects objects;
	public static TiledMapTileLayer collision;
	public static int mapPixelWidth, mapPixelHeight;
	public static float rectX, rectY, scale;

	public MapsManager() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		tmxMapLoader = new TmxMapLoader();
	}

	private void setObjectInfo() {
		objects = map.getLayers().get(1).getObjects();
		MapObject obj = objects.get(0);
		Rectangle rect = ((RectangleMapObject) obj).getRectangle();
		collision = (TiledMapTileLayer) map.getLayers().get("Tile Layer 3");
		rectX = rect.x;
		rectY = rect.y;

	}

	public OrthogonalTiledMapRenderer getRenderer(String map_name) {
		map = tmxMapLoader.load(String.format("maps/%s.tmx", map_name));
		MapProperties properties = map.getProperties();
		int map_width = properties.get("width", Integer.class);
		int map_height = properties.get("height", Integer.class);
		int tile_width = properties.get("tilewidth", Integer.class);
		int tile_height = properties.get("tileheight", Integer.class);
        
		mapPixelWidth = map_width * tile_width;
		mapPixelHeight = map_height * tile_height;
		if (mapPixelWidth / mapPixelHeight == 1) {
			//指定分数位向上取整，防止计算出的maxY较小导致非法参数
			scale = (float) ceilTo(height * 33 / 76 / mapPixelHeight, 3);
		} else {
			scale = (float) ceilTo(width * 79 / 108 / mapPixelWidth, 3);
		}

		setObjectInfo();

		OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map, scale);
		return renderer;

	}
	
	public static double ceilTo(double value, int fraction_precision) {
        if (scale < 0) {
            throw new IllegalArgumentException("小数位数出现负数");
        }

        double multiplier = Math.pow(10, fraction_precision);
        return Math.ceil(value * multiplier) / multiplier;
    }
	

}

