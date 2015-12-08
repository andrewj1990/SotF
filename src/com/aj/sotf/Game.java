package com.aj.sotf;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.aj.sotf.graphics.Screen;
import com.aj.sotf.input.Keyboard;
import com.aj.sotf.input.Mouse;
import com.aj.sotf.level.Level;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private int width = 1280;
	private int height = 720;
	private boolean running = false;
	private String title = "SotF";
	
	private JFrame frame;
	private Thread game_thread;
	
	private BufferedImage image;
	private int[] pixels;
	
	private Screen screen;
	private Level level;
	private Mouse mouse;
	private Keyboard keys;
	
	public Game() {
		Dimension dimension = new Dimension(width, height);
		
		frame = new JFrame(title);
		frame.setPreferredSize(dimension);
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		System.out.println(pixels.length);
		System.out.println(getWidth() + " " + getHeight());
		
		screen = new Screen(getWidth(), getHeight());
		
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		keys = new Keyboard();
		addKeyListener(keys);
		
		level = new Level("/levels/spawn.png", screen, mouse, keys);
		
	}
	
	public synchronized void start() {
		running = true;
		game_thread = new Thread(this);
		game_thread.start();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
//				System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle(title + "   |   " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
				
			}
		}	}
	
	public void update() {
		level.update();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xffff00ff;
		}
		
//		screen.render();
		screen.clear();
		level.render();
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", 0, 50));
		g.dispose();
		bs.show();

	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
//		System.out.println("hello world");
	}
}
