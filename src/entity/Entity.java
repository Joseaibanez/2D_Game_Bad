package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	GamePanel gp;
	public int worldX, worldY, screenX, screenY;
	public int speed;
	// STATS
	public String name;
	public int maxLife;
	public int life;
	// COMPROBADOR DEL LADO AL QUE MIRA EL JUGADOR
	boolean isLookingRight = false;
	// CARGA DE SPRITES
	public BufferedImage idleRight1, idleRight2, idleLeft1, idleLeft2, up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	public BufferedImage attackRight1, attackRight2, attackRight3, attackLeft1, attackLeft2, attackLeft3;
	// CONTADOR DE SPRITES PARA ANIMACIONES
	public int spriteCounter = 0;
	public int spriteNum = 1;
	// AREA DE COLISIONES
	public Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
	public boolean collisionOn = false;
	// ATAQUE
	boolean attacking = false;
	//
	public int actionLockCounter = 0;
	public int collisionAreaDefaultX;
	public int collisionAreaDefaultY;
	// PERIODO DE INVENCIBILIDAD AL RECIBIR DAÑO
	public boolean invencible = false;
	public int invencibleCounter = 0;
	// COMPROBADOR DEL TIPO DE ENTIDAD
	public int type;// 0 = jugador; 1 = npc; 2 = monstruo
	
	
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAction() {}
	
	public void update() {
		
		setAction();
		
		collisionOn = false;
		gp.colCheck.checkTile(this);
		boolean contactPlayer = gp.colCheck.checkPlayer(this);
		//gp.colCheck.checkEntity(this, gp.monsters);
		
		if(this.type == 2 && contactPlayer == true) {
			if(!gp.player.invencible) {
				gp.player.life--;
				gp.player.invencible = true;
			}
		}
		
		// Moverse si la losa no tiene colisiones
		if(collisionOn == false) {
			switch(direction) {
				case "up":
					worldY -= speed;
					//System.out.println("Arriba");
					break;
				case "down":
					worldY += speed;
					//System.out.println("Abajo");
					break;
				case "left":
					isLookingRight = false;
					worldX -= speed;
					//System.out.println("Izquierda");					
					break;
				case "right":
					isLookingRight = true;
					worldX += speed;
					//System.out.println("Derecha");
					break;
			}
		}
		sCounter();
		
	}
	
	public void sCounter() {
		spriteCounter++;
		if(spriteCounter > 20) {
			if(spriteNum == 1) {
				spriteNum = 2;
			} else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			BufferedImage image = null;
			// ANIMATION SWITCH
			switch(direction) {
				case "idle":// Animacion del personaje parado
					if(isLookingRight) {
						switch(spriteNum) {
							case 1:
								image = idleRight1;
								break;
							case 2:
								image = idleRight2;
								break;
						}
					} else if(!isLookingRight) {
						switch(spriteNum) {
							case 1:
								image = idleLeft1;
								break;
							case 2:
								image = idleLeft2;
								break;
						}
					}
					break;
				case "up":// Animacion de caminar hacia arriba
					if(isLookingRight) {
						switch(spriteNum) {
							case 1:
								image = right1;
								break;
							case 2:
								image = right2;
								break;
						}
					} else if(!isLookingRight) {
						switch(spriteNum) {
							case 1:
								image = left1;
								break;
							case 2:
								image = left2;
								break;
						}
					}
					break;
				case "down":// Animacion de caminar hacia abajo
					if(isLookingRight) {
						switch(spriteNum) {
							case 1:
								image = right1;
								break;
							case 2:
								image = right2;
								break;
						}
					} else if(!isLookingRight) {
						switch(spriteNum) {
							case 1:
								image = left1;
								break;
							case 2:
								image = left2;
								break;
						}
					}
					break;
				case "left":// Animacion de caminar hacia la izquierda
					switch(spriteNum) {
						case 1:
							image = left1;
							break;
						case 2:
							image = left2;
							break;
					}				
					break;
				case "right":// Animacion de caminar hacia la derecha
					switch(spriteNum) {
						case 1:
							image = right1;
							break;
						case 2:
							image = right2;
							break;
					}
					break;
			}
			// FIN
			
			g2.drawImage(image, screenX, screenY, null);
		}
	}
	
	// Metodo para hacer setup de las entidades
	public BufferedImage setup(String imagePath, boolean bigger, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage scaledImage = null;
		
		try {
			if(!bigger) {
				
				scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
				scaledImage = uTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
			} else {
				
				scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
				scaledImage = uTool.scaleImage(scaledImage, width+40, height+20);
			}
			
			//scaledImage = uTool.scaleImage(original, width, height)
				
		} catch(IOException e) {
			e.printStackTrace();
		}
		return scaledImage;
	}
		
	
}
