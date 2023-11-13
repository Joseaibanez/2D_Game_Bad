package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{

	KeyHandler keyH;
	boolean isLookingRight = true;// Comprueba si se ha pulsado "D" para determinar a que dirección mira el personaje
	
	public final int screenX;
	public final int screenY;
	 
	public Player(GamePanel gp, KeyHandler kh) {
		
		super(gp);
		this.keyH = kh;
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		// Area solida del modelo
		collisionArea = new Rectangle();
		collisionArea.x = 8;
		collisionArea.y = 16;
		collisionArea.width = 50;
		collisionArea.height = 60;
		// Area de ataque del modelo
		attackArea.width = 50;
		attackArea.height = 20;
		// Fin
		type = 0;
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 25;
		worldY = gp.tileSize * 24;
		speed = 4;
		direction = "down";
		// PLAYER STATUS
		maxLife = 8;
		life = maxLife;
	}
	
	// Metodo encargado de seleccionar el sprite
	// correspondiente para cada situación
	public void getPlayerImage() {
		// Animaciones de movimiento
		left1 = setup("/player/Run1Left.png", false, gp.tileSize, gp.tileSize);
		left2 = setup("/player/Run2Left.png", false, gp.tileSize, gp.tileSize);
		right1 = setup("/player/Run1Right.png", false, gp.tileSize, gp.tileSize);
		right2 = setup("/player/Run2Right.png", false, gp.tileSize, gp.tileSize);
		// Animacion parado
		idleRight1 = setup("/player/IdleRight1.png", false, gp.tileSize, gp.tileSize);
		idleRight2 = setup("/player/IdleRight2.png", false, gp.tileSize, gp.tileSize);
		idleLeft1 = setup("/player/IdleLeft1.png", false, gp.tileSize, gp.tileSize);
		idleLeft2 = setup("/player/IdleLeft2.png", false, gp.tileSize, gp.tileSize);
	}
	
	public void getPlayerAttackImage() {
		// Animaciones de ataque
		attackRight1 = setup("/player/AttackRight1.png", false, gp.tileSize, gp.tileSize*5);
		attackRight2 = setup("/player/AttackRight2.png", false, gp.tileSize, gp.tileSize*5);
		attackRight3 = setup("/player/AttackRight3.png", false, gp.tileSize, gp.tileSize*5);
		attackLeft1 = setup("/player/AttackLeft1.png", false, gp.tileSize, gp.tileSize*5);
		attackLeft2 = setup("/player/AttackLeft2.png", false, gp.tileSize, gp.tileSize*5);
		attackLeft3 = setup("/player/AttackLeft3.png", false, gp.tileSize, gp.tileSize*5);
	}
	
	
	
	// Metodo que se encarga de actualizar
	// la información referente al jugador
	public void updatePlayer() {
		
		if(attacking) {
			attacking();
		}
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			//attacking = false;
			if(keyH.upPressed == true) {
				direction = "up";
			} else if(keyH.downPressed == true) {
				direction = "down";
			} else if(keyH.leftPressed == true) {
				direction = "left";
				isLookingRight = false;
			} else if(keyH.rightPressed == true) {
				direction = "right";
				isLookingRight = true;
			}
			
			// Contador de sprites
			sCounter();
			// Comprobacion de colisiones
			collisionOn = false;
			gp.colCheck.checkTile(this);
			int npcIndex = gp.colCheck.checkEntity(this, gp.npcs);// Comprueba las colisiones entre Jugador/NPCs
			int monsterIndex = gp.colCheck.checkEntity(this, gp.monsters);// Comprueba las colisiones entre Jugador/NPCs
			contactMonster(monsterIndex);// El jugador recibe daño
			interactNPC(npcIndex);
			if(collisionOn == false) {
				switch(direction) {
					case "up":
						worldY -= speed;
						break;
					case "down":
						worldY += speed;
						break;
					case "left":
						worldX -= speed;
						break;
					case "right":
						worldX += speed;
						break;
				}
			}
			// FIN
			// Animacion de ataque
		} else if(keyH.attackPressed) {
			attacking = true;
			//
			
			//
			
		} else {
			attacking = false;
			direction = "idle";
			sCounter();
		}
		// CONTADOR DE INEVNCIBILIDAD
		if(invencible) {
			invencibleCounter++;
			if(invencibleCounter > 60) {
				invencible = false;
				invencibleCounter = 0;
			}
		}
		
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
	
	// METODO QUE GESTIONA LAS ANIMACIONES DE ATAQUE
	public void attacking() {
		spriteCounter++;
		
		if(spriteCounter <= 30) {
			spriteNum = 1;
		}
		if(spriteCounter > 30 && spriteCounter <= 60) {
			spriteNum = 2;
			
			// GUARDAR LA POSICION ACTUAL DEL MODELO
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int collisionAreaWidth = collisionArea.width;
			int collisionAreaHeight = collisionArea.height;
			// AJUSTAR LA POSICION PARA EL ATAQUE
			switch(direction) {
				case "up":
					if(!isLookingRight) {
						worldX -= attackArea.width;
					} else if(isLookingRight) {
						worldX += attackArea.width;
					}
					break;
				case "down":
					if(!isLookingRight) {
						worldX -= attackArea.width;
					} else if(isLookingRight) {
						worldX += attackArea.width;
					}
					break;
				case "left":
					worldX -= attackArea.width;
					break;
				case "right":
					worldX += attackArea.width;
					break;
			}
			collisionArea.width = attackArea.width;
			collisionArea.height = attackArea.height;
			
			int monsterIndex = gp.colCheck.checkEntity(this,  gp.monsters);
			damageMonster(monsterIndex);
			// Despues de comprobar la colision restauramos los datos originales
			worldX = currentWorldX;
			worldY = currentWorldY;
			collisionArea.width = collisionAreaWidth;
			collisionArea.height = collisionAreaHeight;
			
			
		}
		if(spriteCounter > 60 && spriteCounter <= 70) {
			spriteNum = 3;
		}
		if(spriteCounter > 70) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}

	// Metodo que se ocupa de las interacciones entre
	// el jugador y los otros tipos de entidades
	private void interactNPC(int npcIndex) {
		if(npcIndex != 999) {
			//System.out.println("ESTAS CHOCANDO");
			//------------------------------------------------------------++++++++++++++++++++++++++++++++++++++++++++++++++++
		}
		
	}
	
	
	// Metodo para dibujar al jugador
	// en el panel de juego
	public void drawPlayer(Graphics2D g2) {
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
			case "idle":// Animacion del personaje parado
				if(isLookingRight) {
					if(!attacking) {
						if(spriteNum == 1) {image = idleRight1;}
						if(spriteNum == 2) {image = idleRight2;}
						if(spriteNum == 3) {image = idleRight2;}
					}
					if(attacking) {
						if(spriteNum == 1) {image = attackRight1;}
						if(spriteNum == 2) {image = attackRight2;}
						if(spriteNum == 3) {image = attackRight3;}
					}
				} else if(!isLookingRight) {
					if(!attacking) {
						if(spriteNum == 1) {image = idleLeft1;}
						if(spriteNum == 2) {image = idleLeft2;}
						if(spriteNum == 3) {image = idleLeft2;}
					}
					if(attacking) {
						if(spriteNum == 1) {image = attackLeft1;}
						if(spriteNum == 2) {image = attackLeft2;}
						if(spriteNum == 3) {image = attackLeft3;}
					}
				}
				break;
			case "up":// Animacion de caminar hacia arriba
				if(isLookingRight) {
					if(!attacking) {
						if(spriteNum == 1) {image = right1;}
						if(spriteNum == 2) {image = right2;}
						if(spriteNum == 3) {image = right2;}
					}
					if(attacking) {
						if(spriteNum == 1) {image = attackRight1;}
						if(spriteNum == 2) {image = attackRight2;}
						if(spriteNum == 3) {image = attackRight3;}
					}
				} else if(!isLookingRight) {
					if(!attacking) {
						if(spriteNum == 1) {image = left1;}
						if(spriteNum == 2) {image = left2;}
						if(spriteNum == 3) {image = left2;}
					}
					if(attacking) {
						if(spriteNum == 1) {image = attackLeft1;}
						if(spriteNum == 2) {image = attackLeft2;}
						if(spriteNum == 3) {image = attackLeft3;}
					}
				}
				break;
			case "down":
				if(isLookingRight) {
					if(!attacking) {
						if(spriteNum == 1) {image = right1;}
						if(spriteNum == 2) {image = right2;}
						if(spriteNum == 3) {image = right2;}
					}
					if(attacking) {
						if(spriteNum == 1) {image = attackRight1;}
						if(spriteNum == 2) {image = attackRight2;}
						if(spriteNum == 3) {image = attackRight3;}
					}
				} else if(!isLookingRight) {
					if(!attacking) {
						if(spriteNum == 1) {image = left1;}
						if(spriteNum == 2) {image = left2;}
						if(spriteNum == 3) {image = left2;}
					}
					if(attacking) {
						if(spriteNum == 1) {image = attackLeft1;}
						if(spriteNum == 2) {image = attackLeft2;}
						if(spriteNum == 3) {image = attackLeft3;}
					}
				}
				break;
			case "left":
				if(!attacking) {
					if(spriteNum == 1) {image = left1;}
					if(spriteNum == 2) {image = left2;}
					if(spriteNum == 3) {image = left2;}
				}
				if(attacking) {
					if(spriteNum == 1) {image = attackLeft1;}
					if(spriteNum == 2) {image = attackLeft2;}
					if(spriteNum == 3) {image = attackLeft3;}
				}		
				break;
			case "right":
				if(!attacking) {
					if(spriteNum == 1) {image = right1;}
					if(spriteNum == 2) {image = right2;}
					if(spriteNum == 3) {image = right2;}
				}
				if(attacking) {
					if(spriteNum == 1) {image = attackRight1;}
					if(spriteNum == 2) {image = attackRight2;}
					if(spriteNum == 3) {image = attackRight3;}
				}
				break;
		}
		g2.drawImage(image, screenX, screenY, null);
		// HITBOX
		//g2.setColor(new Color(255,0,30));
		//g2.fillRect(screenX, screenY, 50, 20);
		// FIN DE HITBOX
		
	}
	
	public void contactMonster(int i) {
		if(i != 999) {
			if(!invencible) {
				life--;
				invencible = true;
			}
		}
	}
	
	public void damageMonster(int i) {
		if(i != 999) {
			
			if(gp.monsters[i].invencible == false) {
				gp.monsters[i].life--;
				System.out.println("DEMON LIFE: "+gp.monsters[i].life);
				gp.monsters[i].invencible = true;
				if(gp.monsters[i].life <= 0) {
					gp.monsters[i].dying = true;
				}
			}
		}
		else {
			//System.out.println("FALLO...");
		}
	}
	
	// FIN CODIGO
}
