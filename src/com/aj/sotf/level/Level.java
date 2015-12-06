package com.aj.sotf.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.aj.sotf.graphics.Screen;
import com.aj.sotf.graphics.Sprite;
import com.aj.sotf.input.Mouse;


public class Level {

	public int[] tiles;
	private int width;
	private int height;
	private Screen screen;
	private Mouse mouse;
	
	private List<Integer> x_coords = new ArrayList<Integer>();
	private List<Integer> y_coords = new ArrayList<Integer>();
	private List<Float> intensity_list = new ArrayList<Float>();

	private boolean[] visited;
	
	public Level(String path, Screen screen, Mouse mouse) {
		loadLevel(path);
		this.screen = screen;
		visited = new boolean[width * height];
		this.mouse = mouse;
	}
	
	public void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(getClass().getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! Could not load level file!");
		}
	}
	
	public void render() {

//		for (int i = 0; i < screen.pixels.length; i++) {
//			screen.pixels[i] = SpriteSheet.tiles.pixels[i];
//		}
	
//		testRender(0, 0, screen);
//		testRender(1, 0, screen);
				
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
////				System.out.println("x : " + x + ", y : " + y + ", colour : " + tiles[x + y * width]);
////				screen.pixels[x + y * screen.width] = Sprite.s1.pixels[x + y * Sprite.s1.size];
//				getTile(x, y).render(x, y, 1.0f, screen);
////				testRender(x, y, screen);
//			}
//		}
		
		// add lighting
		floodFill(mouse.getX() / 16, mouse.getY() / 16, 1.0f);
//		floodFill2(5,5, 1.0f);
	}
	
	public void floodFill2(int x, int y, float intensity) {

		int coord = x + y * width;
		
		if (x < 0 || x >= width || y < 0 || y >= height - 1 || visited[coord]) {
			return;
		}

		visited[coord] = true;

		getTile(x, y).render(x, y, intensity, screen);
		
		floodFill2(x + 1, y, intensity * 1.0f);
		floodFill2(x, y + 1, intensity * 1.0f);
		floodFill2(x - 1, y, intensity * 1.0f);
		floodFill2(x, y - 1, intensity * 1.0f);
		
	}
	
	private void addCoords(int x, int y, float intensity) {
		x_coords.add(x);
		y_coords.add(y);		
		intensity_list.add(intensity);
	}
	
	private void removeCoords() {
		if (x_coords.size() > 0) x_coords.remove(0);
		if (y_coords.size() > 0) y_coords.remove(0);
		if (intensity_list.size() > 0) intensity_list.remove(0);
	}
	
	// bfs floodfill
	public void floodFill(int x, int y, float intensity) {

		addCoords(x, y, intensity);

		while (x_coords.size() > 0) {
			int xx = x_coords.get(0);
			int yy = y_coords.get(0);
			float new_intensity = intensity_list.get(0) * 0.90f;
						
			if (xx < 0 || xx >= screen.width || yy < 0 || yy >= (screen.height / 16) - 1 || visited[xx + yy * width]) {
				removeCoords();
				continue;
			}
			
			visited[xx + yy * width] = true;
			
			getTile(xx, yy).render(xx, yy, new_intensity, screen);
			
			addCoords(xx + 1, yy, new_intensity);
			addCoords(xx, yy + 1, new_intensity);
			addCoords(xx - 1, yy, new_intensity);
			addCoords(xx, yy - 1, new_intensity);
			removeCoords();
			
		}
		
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		
		
		
	}
	
	public void testRender(int xx, int yy, Screen screen) {
		int tx = xx * 32;
		int ty = yy * 32;
		for (int y = yy; y < yy + Sprite.s1.size; y++) {
			int ya = ty + y;
			for (int x = xx; x < xx + Sprite.s1.size; x++) {
				int xa = tx + x;
				screen.pixels[xa + ya * screen.width] = Sprite.s1.pixels[(x - xx) + (y - yy) * Sprite.s1.size];
			}
		}	
		
//		for (int y = 0; y < Sprite.s1.size; y++) {
//			for (int x = xx; x < xx + Sprite.s1.size; x++) {
//				screen.pixels[x + y * screen.width] = Sprite.s1.pixels[(x - xx) + y * Sprite.s1.size];
//			}
//		}		
	}
	
	public Sprite getTile(int coord) {
		if (tiles[coord] == 0xff00ff00) return Sprite.s1;
		else if (tiles[coord] == 0xff0000ff) return Sprite.s2;
		return Sprite.s1;		
	}

	public Sprite getTile(int x, int y) {
		if (tiles[x + y * width] == 0xff00ff00) return Sprite.s1;
		else if (tiles[x + y * width] == 0xff0000ff) return Sprite.s2;
		return Sprite.s1;
		
	}
	
	
}
