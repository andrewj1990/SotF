package com.aj.sotf.level.tile;

import com.aj.sotf.graphics.Screen;
import com.aj.sotf.graphics.Sprite;

public class Tile {

	private int width;
	private int height;
	
	Sprite sprite;
	
	public static Tile grass_tile = new GrassTile(Sprite.s1);
	public static Tile grass_tile2 = new GrassTile(Sprite.grass);
	public static Tile water_tile = new WaterTile(Sprite.water2);
	public static Tile anim_water_tile = new AnimWaterTile(Sprite.water);
	public static Tile dirt_tile = new GrassTile(Sprite.dirt);
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int xx, int yy, float intensity, Screen screen) {
		sprite.render(xx, yy, intensity, screen);
	}
	
	public boolean solid() {
		return false;
	}
	
}
