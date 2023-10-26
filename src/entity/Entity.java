package entity;

import java.awt.image.BufferedImage;

public class Entity {

	public int x, y;
	public int speed;
	
	public BufferedImage idleRight1, idleRight2, idleLeft1, idleLeft2, up1, up2, down1, down2, left1, left2, right1, right2,
	attackRight1, attackRight2, attackRight3, attackLeft1, attackLeft2, attackLeft3;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
}
