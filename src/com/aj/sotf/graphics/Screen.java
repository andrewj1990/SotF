package com.aj.sotf.graphics;

import java.util.Random;

public class Screen {

	public int width;
	public int height;
	private Random rand = new Random();
	
	
	public int[] pixels;
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void update() {
		
	}
	
	public void render() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = rand.nextInt();
		}
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
}
