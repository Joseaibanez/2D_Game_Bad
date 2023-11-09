package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tiles;
	public int mapTileNumber[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tiles = new Tile[11];
		mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap();
	}
	
	public void getTileImage() {
		
		// Baldosas de background arenoso
		setup(0, "bgSand", false);
		// Baldosas de suelo de castillo
		setup(1, "SueloCastillo", false);
		// Baldosas de piedra
		setup(2, "Muro", true);
		// Baldosas de tierra con el borde hacia la izquierda
		setup(3, "TierraBordeIzquierda", false);
		// Baldosas de tierra con el borde hacia arriba
		setup(4, "TierraBordeArriba", false);
		// Baldosas de tierra normal
		setup(5, "TierraNormal", false);
		// Baldosas de casa pequeña
		setup(6, "CasaPequeñaSobreARena", false);
		// Baldosas de borde
		setup(7, "Stone", true);
		// Baldosas de agua
		setup(8, "Water", true);
		// Arbol seco 1
		setup(9, "ArbolSecoSobreTierra1", true);
	}
	
	public void setup(int index, String imagePath, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		try {
			tiles[index] = new Tile();
			tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imagePath+".png"));
			tiles[index].image = uTool.scaleImage(tiles[index].image, gp.tileSize, gp.tileSize);
			tiles[index].colision = collision;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap() {
		
		try {
			InputStream is = getClass().getResourceAsStream("/mapas/Mundo.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				while(col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNumber[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
					
		} catch(Exception e) {
			
		}
	}
	
	public void drawTile(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNumber[worldCol][worldRow];
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tiles[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			
			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
}
