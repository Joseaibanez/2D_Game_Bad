package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Npc_SwordGirl;
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
	KeyHandler keyH = new KeyHandler(this);
	// Entidades
	public AssetSetter aSetter = new AssetSetter(this);
	public Player player = new Player(this, keyH);
	public Npc_SwordGirl friendlyNpc = new Npc_SwordGirl(this);
	public Entity npcs[] = new Entity[10];
	ArrayList<Entity> entityList = new ArrayList<>();
	public Entity monsters[] = new Entity[20];
	
	// Tiempo de juego (mediante threads)
	Thread gameThread;
	
	// INTERFAZ
	public UI ui = new UI(this);
	
	// ESTADOS DE JUEGO
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int gameOverState = 3;
	
	// Detector de colisiones
	public CollisionChecker colCheck = new CollisionChecker(this);
	
	// Parametros de mundo
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
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
	
	public void setupGame() {
		aSetter.setNPC();
		aSetter.setMonster();
		gameState = playState;
	}
	
	public void startGameThread() {
		setupGame();
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		if(player.life == 0) {
			gameState = gameOverState;
		}
		if(gameState == playState) {
			// PLAYER
			player.updatePlayer();
			// NPC
			for(int i = 0; i < npcs.length; i++) {
				if(npcs[i] != null) {
					npcs[i].update();
				}
			}
			// MONSTERS
			for(int i = 0; i < monsters.length; i++) {
				if(monsters[i] != null) {
					monsters[i].update();
				}
			}
			// FIN
		} else if(gameState == gameOverState) {
			ui.drawDeathScreen();
		}
		if(gameState == pauseState) {
			//ui.Draw();
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tileM.drawTile(g2);
		player.drawPlayer(g2);
		
		// UI
		ui.Draw(g2);
		//NPC
		//entityList.add(player);
		for(int i = 0; i < npcs.length; i++) {
			if(npcs[i] != null) {
				entityList.add(npcs[i]);
				//npcs[i].draw(g2);
			}
		}
		// MONSTERS
		for(int i = 0; i < monsters.length; i++) {
			if(monsters[i] != null) {
				entityList.add(monsters[i]);
				//npcs[i].draw(g2);
			}
		}
		// SORT
		Collections.sort(entityList, new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				// TODO Auto-generated method stub
				int result = Integer.compare(e1.worldY, e2.worldY);
				return result;
			}
			
		});
		// DRAW ENTITIES
		for(int i = 0; i < entityList.size(); i++) {
			entityList.get(i).draw(g2);
		}
		// EMPTY ENTITY LIST
		/*for(int i = 0; i < entityList.size(); i++) {
			entityList.remove(i);
		}*/
		entityList.clear();
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
			repaint();// Esto es la llamada al metodo paintComponents
			
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
