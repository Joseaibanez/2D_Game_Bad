package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	Tile[] tiles;
	int mapTileNumber[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tiles = new Tile[10];
		mapTileNumber = new int[gp.maxScreenCol][gp.maxScreenRow];
		getTileImage();
		loadMap();
	}
	
	public void getTileImage() {
		
		try {
			
			// Baldosas de background arenoso
			tiles[0] = new Tile();
			tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bgSand.png"));
			// Baldosas de tierra con el borde hacia abajo
			tiles[1] = new Tile();
			tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/TierraBordeAbajo.png"));
			// Baldosas de tierra con el borde hacia la derecha
			tiles[2] = new Tile();
			tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/TierraBordeDerecha.png"));
			// Baldosas de tierra con el borde hacia la izqierda
			tiles[3] = new Tile();
			tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/TierraBordeIzquierda.png"));
			// Baldosas de tierra con el borde hacia arriba
			tiles[4] = new Tile();
			tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/TierraBordeArriba.png"));
			// Baldosas de Agua
			tiles[5] = new Tile();
			tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Water.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap() {
		
		try {
			InputStream is = getClass().getResourceAsStream("/mapas/Map1.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col = 0;
			int row = 0;
			
			while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
				String line = br.readLine();
				while(col < gp.maxScreenCol) {
					
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNumber[col][row] = num;
					col++;
				}
				if(col == gp.maxScreenCol) {
					col = 0;
					row++;
				}
			}
			br.close();
					
		} catch(Exception e) {
			
		}
	}
	
	public void drawTile(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
			
			int tileNum = mapTileNumber[col][row];
			
			g2.drawImage(tiles[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;
			
			if(col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
		}
		
	}
}
