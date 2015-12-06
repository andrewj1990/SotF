package com.aj.sotf.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;

	public int[] pixels;
	public int size;
	
//	public static SpriteSheet background = new SpriteSheet("/res/levels/level.png");
	public static SpriteSheet tiles = new SpriteSheet("/spritesheet/spritesheet.png");
	
	public SpriteSheet(String path) {
		this.path = path;
		load();
	}
	
	public void load() {
		try {
			BufferedImage image = ImageIO.read(getClass().getResource(path));
			int width = image.getWidth();
			int height = image.getHeight();
			this.size = width;
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
