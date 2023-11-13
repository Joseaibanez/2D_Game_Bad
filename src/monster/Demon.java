package monster;

import java.awt.Color;
import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class Demon extends Entity {
	
		public GamePanel gp;
	
		public Demon(GamePanel gp) {
			super(gp);
			this.gp = gp;
			direction = "";
			speed = 1;
			getImage();
			getDemonAttackImage();
			name = "Red Demon";
			maxLife = 4;
			life = maxLife;
			type = 2;
			collisionArea.x = 5;
			collisionArea.y = 10;
			collisionArea.width = 90;
			collisionArea.height = 50;
			collisionAreaDefaultX = collisionArea.x;
			collisionAreaDefaultY = collisionArea.y;
			
			
		}
		
		public void getImage() {
			
			// Animacion parado
			idleRight1 = setup("/demon/IdleRight1.png", true, gp.tileSize, gp.tileSize);
			idleRight2 = setup("/demon/IdleRight2.png", true, gp.tileSize, gp.tileSize);
			idleLeft1 = setup("/demon/IdleLeft1.png", true, gp.tileSize, gp.tileSize);
			idleLeft2 = setup("/demon/IdleLeft2.png", true, gp.tileSize, gp.tileSize);
			// Animacion de caminar a la derecha
			right1 = setup("/demon/WalkRight1.png", true, gp.tileSize, gp.tileSize);
			right2 = setup("/demon/WalkRight2.png", true, gp.tileSize, gp.tileSize);
			// Animacion de caminar a la izquierda
			left1 = setup("/demon/WalkLeft1.png", true, gp.tileSize, gp.tileSize);
			left2 = setup("/demon/WalkLeft2.png", true, gp.tileSize, gp.tileSize);
			
		}
		
		public void getDemonAttackImage() {
			// Animaciones de ataque
			attackRight1 = setup("/demon/DemAttackRight1.png", true, gp.tileSize, gp.tileSize+20);
			attackRight2 = setup("/demon/DemAttackRight2.png", true, gp.tileSize, gp.tileSize+20);
			attackLeft1 = setup("/demon/DemAttackLeft1.png", true, gp.tileSize, gp.tileSize+20);
			attackLeft2 = setup("/demon/DemAttackLeft2.png", true, gp.tileSize, gp.tileSize+20);
			//attackRight3 = setup("/player/AttackRight3.png", false, gp.tileSize, gp.tileSize*5);
			//attackLeft1 = setup("/player/AttackLeft1.png", false, gp.tileSize, gp.tileSize*5);
			//attackLeft2 = setup("/player/AttackLeft2.png", false, gp.tileSize, gp.tileSize*5);
			//attackLeft3 = setup("/player/AttackLeft3.png", false, gp.tileSize, gp.tileSize*5);
		}
		
		public void getDeathImage() {
			// ANIMACION MUERTE
			death1 = setup("/demon/DemDeath1.png", true, gp.tileSize, gp.tileSize);
			death2 = setup("/demon/DemDeath2.png", true, gp.tileSize, gp.tileSize);
			death3 = setup("/demon/DemDeath3.png", true, gp.tileSize, gp.tileSize);
		}
		
		public void setAction() {
			
			actionLockCounter++;
			if(actionLockCounter == 120) {
				Random rand = new Random();
				int i = rand.nextInt(100)+1;
				if(i <= 20) {
					direction = "up";
				}
				else if(i < 20 && i <= 45) {
					direction = "down";
				}
				else if(i < 45 && i <= 65) {
					direction = "left";
				}
				else if(i < 65 && i <= 100) {
					direction = "right";
				} else {
					direction = "idle";
				}
				actionLockCounter = 0;
			}			
			
		}
}
