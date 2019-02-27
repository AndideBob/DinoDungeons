package dinodungeons.gfx.ui;

import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.gfx.GFXResourceID;
import lwjgladapter.gfx.TileMap;

public class UIManager {
	
	private static final int healthPerLine = 12;

	TileMap healthSprite;
	
	public UIManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void loadResources(){
		healthSprite = new TileMap(GFXResourceID.UI_HEALTH.getFilePath(), 8, 8);
	}
	
	public void draw(int yPosition){
		PlayerStatusManager playerStatus = PlayerStatusManager.getInstance();
		drawHealth(yPosition + 16, playerStatus.getMaxHealth(), playerStatus.getCurrentHealth());
	}
	
	private void drawHealth(int yPosition, int maxHealth, int currentHealth){
		int fullHealthBubbles = (int) Math.floor(currentHealth / 2f);
		boolean drawHalfBubble = currentHealth % 2 == 1;
		int numberBubbles = (int) Math.ceil(maxHealth / 2f);
		int counter = 0;
		for(int i = counter; i < fullHealthBubbles; i++){
			int posX = (i % healthPerLine) * 8;
			int posY = yPosition - 8 * (int)Math.floor(1f * i / healthPerLine);
			healthSprite.draw(0, posX, posY);
			counter++;
		}
		if(drawHalfBubble){
			int posX = (counter % healthPerLine) * 8;
			int posY = yPosition - 8 * (int)Math.floor(1f * counter / healthPerLine);
			healthSprite.draw(1, posX, posY);
			counter++;
		}
		for(int i = counter; i < numberBubbles; i++){
			int posX = (i % healthPerLine) * 8;
			int posY = yPosition - 8 * (int)Math.floor(1f * i / healthPerLine);
			healthSprite.draw(2, posX, posY);
			counter++;
		}
	}

}
