package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.GamePanel;

public class Npc_SwordGirl extends Entity {
	
	//boolean isLookingRight = false;
	public GamePanel gp;
	public Npc_SwordGirl(GamePanel gp) {
		super(gp);
		this.gp = gp;
		direction = "";
		speed = 1;
		getNpcImage();
	}
	
	public void getNpcImage() {
		
		// Animacion parado
		idleRight1 = setup("/npc/IdleRight1.png", false, gp.tileSize, gp.tileSize);
		idleRight2 = setup("/npc/IdleRight1.png", false, gp.tileSize, gp.tileSize);
		idleLeft1 = setup("/npc/IdleLeft1.png", false, gp.tileSize, gp.tileSize);
		idleLeft2 = setup("/npc/IdleLeft2.png", false, gp.tileSize, gp.tileSize);
		
	}
	
	public void setAction() {
		
		actionLockCounter++;
		if(actionLockCounter == 20) {
			Random rand = new Random();
			int i = rand.nextInt(100)+1;
			if(i <= 20) {
				direction = "up";
				//System.out.println("Arriba");
			}
			else if(i < 20 && i <= 45) {
				direction = "right";
				//System.out.println("Abajo");
			}
			else if(i < 45 && i <= 65) {
				direction = "left";
				//System.out.println("Izquierda");
			}
			else if(i < 65 && i <= 100) {
				direction = "down";
				//System.out.println("Derecha");
			}
			actionLockCounter = 0;
		}
		
		//System.out.println("INICIA SELECCION");
		
		
	}
	
	
}
