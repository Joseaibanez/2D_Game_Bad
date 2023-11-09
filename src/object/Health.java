package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Health extends SuperObject {
	
	GamePanel gp;
	
	public Health(GamePanel gp) {
		super();
		this.gp = gp;
		name = "Health";
		
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/objects/HealthDotFull2.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/HealthDotEmpty.png"));
			//image3 = ImageIO.read(getClass().getResourceAsStream("/objects/HealthDot2&3.png"));
			//image4 = ImageIO.read(getClass().getResourceAsStream("/objects/HealthDot4.png"));
			//image8 = ImageIO.read(getClass().getResourceAsStream("/objects/noHealth.png"));
			//image = ImageIO.read(getClass().getResourceAsStream(""));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
			uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
			//uTool.scaleImage(image8, gp.tileSize, gp.tileSize);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
