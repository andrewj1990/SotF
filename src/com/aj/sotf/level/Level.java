package com.aj.sotf.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.aj.sotf.entity.Player;
import com.aj.sotf.graphics.Screen;
import com.aj.sotf.graphics.Sprite;
import com.aj.sotf.input.Keyboard;
import com.aj.sotf.input.Mouse;
import com.aj.sotf.level.tile.Tile;


public class Level {

	public int[] tiles;
	private int width;
	private int height;
	private Screen screen;
	private Mouse mouse;
	private Keyboard keys;
	private Player player;
	
	private int xOffset;
	private int yOffset;
	
	private List<Integer> x_coords = new ArrayList<Integer>();
	private List<Integer> y_coords = new ArrayList<Integer>();
	private List<Float> intensity_list = new ArrayList<Float>();

	private boolean[] visited;
	
	public Level(String path, Screen screen, Mouse mouse, Keyboard keys) {
		this.xOffset = 20;
		this.yOffset = 0;
		loadLevel(path);
		this.screen = screen;
		visited = new boolean[width * height];
		this.mouse = mouse;
		this.keys = keys;
		
		
		player = new Player(screen.width / 2, screen.height / 2, keys, this, 16);
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
	
	public void update() {
		player.update();
	}
	
	public void render() {
		
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
////				System.out.println("x : " + x + ", y : " + y + ", colour : " + tiles[x + y * width]);
////				screen.pixels[x + y * screen.width] = Sprite.s1.pixels[x + y * Sprite.s1.size];
//				getTile2(x + xOffset, y + yOffset).render(x, y, 1.0f, screen);
////				testRender(x, y, screen);
//			}
//		}
		
		// add lighting
		floodFill(player.getX() / player.size, player.getY() / player.size, 1.0f);
//		floodFill(Mouse.getX() / 16, Mouse.getY() / 16, 1.0f, 100);
//		floodFill(player.getX() / 16, player.getY() / 16, 1.0f, 97);
		
		player.render(screen);
	}
	
	public int getXOffset() {
		return xOffset;
	}
	
	public int getYOffset() {
		return yOffset;
	}
	
	public void setOffset(int x, int y) {
		xOffset = x;
		yOffset = y;
	}
	
	// dfs floodfill
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

	public void floodFill(int x, int y, float intensity, int size) {

		addCoords(x, y, intensity);

		while (x_coords.size() > 0 && x_coords.size() < size) {
			int xx = x_coords.get(0);
			int yy = y_coords.get(0);
			float new_intensity = intensity_list.get(0) * 1.0f;
						
			if (xx < 0 || xx >= screen.width || yy < 0 || yy > (screen.height / 16) + 1 || visited[xx + yy * width]) {
				removeCoords();
				continue;
			}
			
			visited[xx + yy * width] = true;
			
			getTile2(xx, yy).render(xx, yy, new_intensity, screen);
			
			addCoords(xx + 1, yy, new_intensity);
			addCoords(xx, yy + 1, new_intensity);
			addCoords(xx - 1, yy, new_intensity);
			addCoords(xx, yy - 1, new_intensity);
			removeCoords();
			
		}
		
		x_coords.clear();
		y_coords.clear();
		
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
	}

	
	// bfs floodfill
	public void floodFill(int x, int y, float intensity) {

		addCoords(x, y, intensity);

		while (x_coords.size() > 0) {
			int xx = x_coords.get(0);
			int yy = y_coords.get(0);
			float new_intensity = intensity_list.get(0) * 0.97f;
						
			if (xx < 0 || xx >= screen.width || yy < 0 || yy > (screen.height / player.size) + 1 || visited[xx + yy * width]) {
				removeCoords();
				continue;
			}
			
			visited[xx + yy * width] = true;
			
			getTile2(xx + xOffset, yy + yOffset).render(xx, yy, new_intensity, screen);
			
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
	
	public Tile getTile2(int x, int y) {
		if (x < 0 || x >= width || y <= 0 || y >= height) return Tile.water_tile;
		if (tiles[x + y * width] == 0xff00ff00) return Tile.grass_tile;
		else if (tiles[x + y * width] == 0xff0000ff) return Tile.water_tile;
		else if (tiles[x + y * width] == 0xff7a3b00) return Tile.dirt_tile;
		return Tile.water_tile;
		
	}

	public Sprite getTile(int x, int y) {
		if (tiles[x + y * width] == 0xff00ff00) return Sprite.s1;
		else if (tiles[x + y * width] == 0xff0000ff) return Sprite.s2;
		return Sprite.s1;
		
	}
	
	
}
