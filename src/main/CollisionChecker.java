package main;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	// Metodo que comprueba el area de 
	// colisiones de cada  entidad
	// *REPASAR ESTO*
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.worldX + entity.collisionArea.x;
		int entityRightWorldX = entity.worldX +entity.collisionArea.x + entity.collisionArea.height;
		int entityTopWorldY = entity.worldY + entity.collisionArea.y;
		int entityBottomWorldY = entity.worldY +entity.collisionArea.y + entity.collisionArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
			if(gp.tileM.tiles[tileNum1].colision || gp.tileM.tiles[tileNum2].colision) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
			if(gp.tileM.tiles[tileNum1].colision || gp.tileM.tiles[tileNum2].colision) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
			if(gp.tileM.tiles[tileNum1].colision || gp.tileM.tiles[tileNum2].colision) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
			if(gp.tileM.tiles[tileNum1].colision || gp.tileM.tiles[tileNum2].colision) {
				entity.collisionOn = true;
			}
			break;
		
		
		}
		
	}
	
	// Colisiones de npcs
	public int checkEntity(Entity entity, Entity[] target) {
		
		int index = 999;
		
		for(int i = 0; i < target.length; i++) {
			
			if(target[i] != null) {
				
				// Obtener la parte solida del jugador
				entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
				entity.collisionArea.y = entity.worldY + entity.collisionArea.y;
				// Obtener la parte solida de la otra entidad
				target[i].collisionArea.x = target[i].worldX + target[i].collisionArea.x;
				target[i].collisionArea.y = target[i].worldY + target[i].collisionArea.y;
				
				switch(entity.direction) {
					case "up":
						entity.collisionArea.y -= entity.speed;
						if(entity.collisionArea.intersects(target[i].collisionArea)) {
							entity.collisionOn = true;
							index = i;
						}
						break;
					case "down":
						entity.collisionArea.y += entity.speed;
						if(entity.collisionArea.intersects(target[i].collisionArea)) {
							entity.collisionOn = true;
							index = i;
						}
						break;
					case "left":
						entity.collisionArea.x -= entity.speed;
						if(entity.collisionArea.intersects(target[i].collisionArea)) {
							entity.collisionOn = true;
							index = i;
						}
						break;
					case "right":
						entity.collisionArea.y += entity.speed;
						if(entity.collisionArea.intersects(target[i].collisionArea)) {
							entity.collisionOn = true;
							index = i;
						}
						break;
				}
				entity.collisionArea.x = entity.collisionAreaDefaultX;
				entity.collisionArea.y = entity.collisionAreaDefaultY;
				target[i].collisionArea.x = target[i].collisionAreaDefaultX;
				target[i].collisionArea.y = target[i].collisionAreaDefaultY;
			}
		}
		
		return index;
	}
	
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		// Obtener la parte solida de esta entidad
		entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
		entity.collisionArea.y = entity.worldY + entity.collisionArea.y;
		// Obtener la parte solida del jugador
		gp.player.collisionArea.x = gp.player.worldX + gp.player.collisionArea.x;
		gp.player.collisionArea.y = gp.player.worldY + gp.player.collisionArea.y;
		
		switch(entity.direction) {
			case "up":
				entity.collisionArea.y -= entity.speed;
				/*if(entity.collisionArea.intersects(gp.player.collisionArea)) {
					entity.collisionOn = true;
				}*/
				break;
			case "down":
				entity.collisionArea.y += entity.speed;
				/*if(entity.collisionArea.intersects(gp.player.collisionArea)) {
					entity.collisionOn = true;
				}*/
				break;
			case "left":
				entity.collisionArea.x -= entity.speed;
				/*if(entity.collisionArea.intersects(gp.player.collisionArea)) {
					entity.collisionOn = true;
				}*/
				break;
			case "right":
				entity.collisionArea.y += entity.speed;
				/*if(entity.collisionArea.intersects(gp.player.collisionArea)) {
					entity.collisionOn = true;
				}*/
				break;
		}
		if(entity.collisionArea.intersects(gp.player.collisionArea)) {
			entity.collisionOn = true;
			contactPlayer = true;
		}
		entity.collisionArea.x = entity.collisionAreaDefaultX;
		entity.collisionArea.y = entity.collisionAreaDefaultY;
		gp.player.collisionArea.x = gp.player.collisionAreaDefaultX;
		gp.player.collisionArea.y = gp.player.collisionAreaDefaultY;
		
		return contactPlayer;
		
	}
}
