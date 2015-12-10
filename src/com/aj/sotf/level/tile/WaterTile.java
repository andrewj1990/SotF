package com.aj.sotf.level.tile;

import com.aj.sotf.graphics.Screen;
import com.aj.sotf.graphics.Sprite;

public class WaterTile extends Tile {
	
	private int anim;
	
	public WaterTile(Sprite sprite) {
		super(sprite);
		anim = 0;
	}
	
	public void render(int xx, int yy, float intensity, Screen screen) {
		if (anim < 10000000) {
			anim++;
		} else {
			anim = 0;
		}
//		System.out.println("anim :" + anim);
		
		if (anim % 500000 > 250000) {
			sprite = Sprite.water2;
		} else {
			sprite = Sprite.water;
		}
		
		sprite.render(xx, yy, intensity, screen);
	}
	
	public boolean solid() {
		return true;
	}
	
}
