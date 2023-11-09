package main;

import entity.Npc_SwordGirl;
import monster.Demon;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setNPC() {
		/*gp.npcs[0] = new Npc_SwordGirl(gp);
		gp.npcs[0].worldX = gp.tileSize * 27;
		gp.npcs[0].worldY = gp.tileSize * 23;*/
		
		
		
	}
	
	public void setMonster() {
		gp.monsters[0] = new Demon(gp);
		gp.monsters[0].worldX = gp.tileSize * 27;
		gp.monsters[0].worldY = gp.tileSize * 23;

		gp.monsters[1] = new Demon(gp);
		gp.monsters[1].worldX = gp.tileSize * 37;
		gp.monsters[1].worldY = gp.tileSize * 33;
		
		gp.monsters[2] = new Demon(gp);
		gp.monsters[2].worldX = gp.tileSize * 19;
		gp.monsters[2].worldY = gp.tileSize * 23;
	}
}
