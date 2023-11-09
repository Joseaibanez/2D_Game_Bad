package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import object.Health;
import object.SuperObject;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	BufferedImage health_full, health_empty;
	Font arial_40;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		// FUENTES DE LETRA
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		
		/*try {
			
			InputStream is = getClass().getResourceAsStream("");
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}*/
		SuperObject health = new Health(gp);
		health_full = health.image;
		health_empty = health.image2;
		
	}
	
	public void Draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
			
		}
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			//drawPlayerLife();
			drawPauseScreen();
			
		}
		// GAME OVER STATE
		if(gp.gameState == gp.gameOverState) {
			drawPlayerLife();
			drawDeathScreen();
			
		}
	}
	
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSED";		
		int x = getXForCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	// GAME OVER
	public void drawDeathScreen() {
			
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
			g2.setColor(Color.black);
			String text = "LA PALMASTE CACHO CABRÓN";		
			int x = getXForCenteredText(text);
			int y = gp.screenHeight/2;
			
			g2.drawString(text, x, y);
		}
	
	public int getXForCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
	public void drawPlayerLife() {
		
		int x = gp.tileSize;
		int y = gp.tileSize;
		int i = 0;
		
		// Empty Dot
		while(i < gp.player.maxLife) {
			g2.drawImage(health_empty, x, y, null);
			i++;
			x += 40;
		}
		// RESET
		x = gp.tileSize;
		y = gp.tileSize;
		i = 0;
		// FULL HEALTH
		while(i < gp.player.life) {
			g2.drawImage(health_full, x, y, null);
			i++;
			x += 40;
		}
		//g2.drawImage(health_full, x, y, null);
	}
}
