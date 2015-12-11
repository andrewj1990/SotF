package com.aj.sotf.level.tile;

import com.aj.sotf.graphics.Screen;
import com.aj.sotf.graphics.Sprite;

public class WaterTile extends Tile {
	
	private int anim;
	
	public WaterTile(Sprite sprite) {
		super(sprite);
		anim = 0;
	}

	public boolean solid() {
		return true;
	}
	
}
