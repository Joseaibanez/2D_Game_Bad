package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	
	// Opciones de pantalla
	final int originalTileSize = 20;
	final int scale = 3;
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 13;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	// Gestionado de teclas
	KeyHandler keyH = new KeyHandler();
	// Entidades
	Player player = new Player(this, keyH);
	// Tiempo de juego (mediante threads)
	Thread gameThread;
	// FPS
	int fps = 60;
	// Gestionador de baldosas (tiles)
	TileManager tileM = new TileManager(this);

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		player.updatePlayer();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tileM.drawTile(g2);
		player.drawPlayer(g2);
	}

	// Metodo para generar el bucle de juego
	// Mientras que el hilo siga corriendo
	// este método se repetirá una y otra vez
	@Override
	public void run() {
		
		double drawInterval = 1000000000/fps;// 0.01666 segundos
		double nextDrawTime = System.nanoTime() + drawInterval;
		long timer = 0;
		int drawTimer = 0;
		
		while(gameThread != null) {
	
			// 1 ACTUALIZAR: actualizar la información de juego		
			update();
			// 2 DIBUJAR: dibujar la pantalla con la nuevo información
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval;
				
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
