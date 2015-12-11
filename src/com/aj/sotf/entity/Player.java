package com.aj.sotf.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.aj.sotf.graphics.Screen;
import com.aj.sotf.graphics.Sprite;
import com.aj.sotf.input.Keyboard;
import com.aj.sotf.level.Level;

public class Player extends Entity {

	private Keyboard keys;
	private int tileX;
	private int tileY;
	private int dx = 0;
	private int dy = 0;

	boolean moving = false;
	
	private Sprite sprite;
	
	private int offset = 0;
	private Level level;

	public int size;
	
	private int anim = 0;
	
	public Player(int x, int y, Keyboard keys, Level level, int size) {
		this.x = (x / 16) * 16;
		this.y = (y / 16) * 16;
		tileX = this.x;
		tileY = this.y;
		
		this.size = size;
		
		this.keys = keys;
		this.level = level;
	
		sprite = Sprite.player_up;
//		loadSprite("/spritesheet/player.png");
		
	}
//	
//	public void loadSprite(String path) {
//		try {
//			BufferedImage image = ImageIO.read(getClass().getResource(path));
//			int w = width = image.getWidth();
//			int h = height = image.getHeight();
//			sprite = new int[w * h];
//			image.getRGB(0, 0, w, h, sprite, 0, w);
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println("Exception! Could not load player file!");
//		}
//	}
//	
	public void update() {
		int move_speed = 4;
		keys.update();
		dx = 0; 
		dy = 0;

		if (keys.right) {		
			dx++;
			moving = true;
		}
		if (keys.down) {
			dy++;
			moving = true;
		}
		if (keys.up) {
			dy--;
			moving = true;
		}
		if (keys.left) {
			dx--;
			moving = true;
		}

		if (anim < 500) {
			anim++;
		} else {
			anim = 0;
		}
		
		if (!level.getTile2((x / size) + dx + level.getXOffset(), (y / size) + level.getYOffset()).solid()) {
			level.setOffset(level.getXOffset() + dx, level.getYOffset());
		}

		if (!level.getTile2((x / size) + level.getXOffset(), (y / size) + dy + level.getYOffset()).solid()) {
			level.setOffset(level.getXOffset(), level.getYOffset() + dy);
		}

	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void render(Screen screen) {
		if (moving) {
			int temp = anim % 20;
			if (temp < 5) {
				sprite = Sprite.player_up;
			} else if (temp >= 5 && temp < 10) {
				sprite = Sprite.player_up2;
			} else if (temp >= 10 && temp < 15) {
				sprite = Sprite.player_up;
			} else {
				sprite = Sprite.player_up3;
			}
			moving = false;
		}
		
		sprite.render(x / 16, y / 16, 1.0f, screen);
		//		for (int yy = this.y; yy < y + size; yy++) {
//			if (yy < 0 || yy >= screen.height) break;
//			for (int xx = this.x; xx < x + size; xx++) {
//				if (xx < 0 || xx >= screen.width) break;
////				if (sprite[(xx-x + offset) + (yy-y) * width] == 0xFFBFFFFF) continue;
////				screen.pixels[xx + yy * screen.width] = sprite[(xx-x + offset) + (yy-y) * width];
//				screen.pixels[xx + yy * screen.width] = sprite[(xx-x) + (yy-y) * width];
//
//			}
//		}
	}
	
}
