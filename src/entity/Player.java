package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	GamePanel panel;
	KeyHandler keyH;
	Boolean isLookingRight = true;// Comprueba si se ha pulsado "D" para determinar a que dirección mira el personaje
	
	public Player(GamePanel gp, KeyHandler kh) {
		this.panel = gp;
		this.keyH = kh;
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		x = 100;
		y = 100;
		speed = 4;
		direction = "down";
	}
	
	// Metodo encargado de seleccionar el sprite
	// correspondiente para cada situación
	public void getPlayerImage() {
		try {
			// Animaciones de movimiento
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/Run1Left.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/Run2Left.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/Run1Right.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/Run2Right.png"));
			// Animacion parado
			idleRight1 = ImageIO.read(getClass().getResourceAsStream("/player/IdleRight1.png"));
			idleRight2 = ImageIO.read(getClass().getResourceAsStream("/player/IdleRight2.png"));
			idleLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/IdleLeft1.png"));
			idleLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/IdleLeft2.png"));
			// Animaciones de ataque
			attackRight1 = ImageIO.read(getClass().getResourceAsStream("/player/AttackRight1.png"));
			attackRight2 = ImageIO.read(getClass().getResourceAsStream("/player/AttackRight2.png"));
			attackRight3 = ImageIO.read(getClass().getResourceAsStream("/player/AttackRight2.png"));
			attackLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/AttackLeft1.png"));
			attackLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/AttackLeft2.png"));
			attackLeft3 = ImageIO.read(getClass().getResourceAsStream("/player/AttackLeft3.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Metodo que se encarga de actualizar
	// la información referente al jugador
	public void updatePlayer() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.attackPressed) {
			
			if(keyH.upPressed == true) {
				direction = "up";
				y -= speed;
			} else if(keyH.downPressed == true) {
				direction = "down";
				y += speed;
			} else if(keyH.leftPressed == true) {
				direction = "left";
				isLookingRight = false;
				x -= speed;
			} else if(keyH.rightPressed == true) {
				direction = "right";
				isLookingRight = true;
				x += speed;
			} else if(keyH.attackPressed == true) {
				direction = "attack";
			}
			
			spriteCounter++;
			if(spriteCounter > 20) {
				if(spriteNum == 1) {
					spriteNum = 2;
				} else if(spriteNum == 2) {
					spriteNum = 1;
				} else if(spriteNum == 3) {
					spriteNum = 2;
				}
				spriteCounter = 0;
			}
		} else {
			direction = "idle";
		}
		
	}

	
	// Metodo para dibujar al jugador
	// en el panel de juego
	public void drawPlayer(Graphics2D g2) {
		BufferedImage image = null;
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
			case "down":
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
			case "left":
				switch(spriteNum) {
					case 1:
						image = left1;
						break;
					case 2:
						image = left2;
						break;
				}				
				break;
			case "right":
				switch(spriteNum) {
					case 1:
						image = right1;
						break;
					case 2:
						image = right2;
						break;
				}
				break;
			case "attack":
				if(isLookingRight) {
					switch(spriteNum) {
						case 1:
							image = attackRight1;
							break;
						case 2:
							image = attackRight2;
							break;
						case 3:
							image = attackRight3;
							break;
					}
				} else if(!isLookingRight) {
					switch(spriteNum) {
						case 1:
							image = attackLeft1;
							break;
						case 2:
							image = attackLeft2;
							break;
						case 3:
							image = attackLeft3;
							break;
					}
				}
				break;
		}
		g2.drawImage(image, x, y, panel.tileSize, panel.tileSize, null);
	}
}
