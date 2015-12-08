package com.aj.sotf.level.tile;

import com.aj.sotf.graphics.Sprite;

public class WaterTile extends Tile {
	
	public WaterTile(Sprite sprite) {
		super(sprite);
	}
	
	public boolean solid() {
		return true;
	}
	
}
