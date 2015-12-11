package com.aj.sotf.level.tile;

import com.aj.sotf.graphics.Screen;
import com.aj.sotf.graphics.Sprite;

public class AnimWaterTile extends WaterTile{

	private int anim = 0;
	
	public AnimWaterTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int xx, int yy, float intensity, Screen screen) {
		if (anim < 1000) {
			anim++;
		} else {
			anim = 0;
		}
//		System.out.println("anim :" + anim);
		
		if (anim % 1500 > 750) {
			sprite = Sprite.water2;
		} else {
			sprite = Sprite.water;
		}
		
		sprite.render(xx, yy, intensity, screen);
	}
	
	
	
}
