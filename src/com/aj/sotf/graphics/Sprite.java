package com.aj.sotf.graphics;

public class Sprite {
	
	private int x;
	private int y;

	public int size;
	private SpriteSheet sheet;
	
	public int[] pixels;
	
	public static Sprite s1 = new Sprite(0, 0, 16, SpriteSheet.tiles);
	public static Sprite s2 = new Sprite(1, 0, 16, SpriteSheet.tiles);
	public static Sprite grass = new Sprite(2, 0, 16, SpriteSheet.tiles);
	public static Sprite water = new Sprite(0, 1, 16, SpriteSheet.tiles);
	public static Sprite water2 = new Sprite(1, 1, 16, SpriteSheet.tiles);
	public static Sprite dirt = new Sprite(1, 0, 16, SpriteSheet.tiles);
	
	public static Sprite player_up = new Sprite(0, 0, 16, SpriteSheet.player);
	public static Sprite player_up2 = new Sprite(1, 0, 16, SpriteSheet.player);
	public static Sprite player_up3 = new Sprite(2, 0 , 16, SpriteSheet.player);
	
	public static Sprite player_right = new Sprite(0, 1, 16, SpriteSheet.player);
	public static Sprite player_right2 = new Sprite(1, 1, 16, SpriteSheet.player);
	public static Sprite player_right3 = new Sprite(2, 1, 16, SpriteSheet.player);

	public static Sprite player_down = new Sprite(0, 2, 16, SpriteSheet.player);
	public static Sprite player_down2 = new Sprite(1, 2, 16, SpriteSheet.player);
	public static Sprite player_down3 = new Sprite(2, 2, 16, SpriteSheet.player);

	public static Sprite player_left = new Sprite(0, 3, 16, SpriteSheet.player);
	public static Sprite player_left2 = new Sprite(1, 3, 16, SpriteSheet.player);
	public static Sprite player_left3 = new Sprite(2, 3, 16, SpriteSheet.player);
	
	
	public Sprite(int x, int y, int size, SpriteSheet sheet) {
		this.size = size;
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		pixels = new int[size*size];
		
		load();
	}

	public void load() {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				pixels[x + y * size] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.size];
			}
		}
	}
	
	public void render(int xx, int yy, float intensity, Screen screen) {
//		for (int y = yy; y < yy + Sprite.s1.size; y++) {
//			int ya = this.y + y;
//			for (int x = xx; x < xx + Sprite.s1.size; x++) {
//				int xa = this.x + x;
//				screen.pixels[xa + ya * screen.width] = Sprite.s1.pixels[(x - xx) + (y - yy) * Sprite.s1.size];
//			}
//		}	
		
		int tx = xx * size;
		int ty = yy * size;
		for (int y = 0; y < size; y++) {
			int ya = ty + y;
			for (int x = 0; x < size; x++) {
				int xa = tx + x;
				if (xa < 0 || xa >= screen.width || ya < 0 || ya >= screen.height) break;
//				System.out.println("xa : " + xa + ", ya : " + ya);
				
				int colour = pixels[x + y * size];
				if (colour == 0xFFFF00FF) continue;
				int r = (colour >> 16) & 0xff;
				int g = (colour >>  8) & 0xff;
				int b = (colour) & 0xff;
				
				r *= intensity;
				g *= intensity;
				b *= intensity;
				
				colour = (r << 16) | (g << 8) | b;
				
				screen.pixels[xa + ya * screen.width] = colour;
			}
		}	
	}
	
}
