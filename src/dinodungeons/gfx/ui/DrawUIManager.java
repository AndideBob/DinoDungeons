package dinodungeons.gfx.ui;

import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.game.utils.MenuManager;
import dinodungeons.gfx.GFXResourceID;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.text.DrawTextManager;
import lwjgladapter.GameWindowConstants;
import lwjgladapter.gfx.SpriteMap;

public class DrawUIManager {
	
	private static final int healthPerLine = 12;

	private SpriteMap healthSprite;
	private SpriteMap borderSprite;
	private SpriteMap collectableSprites;
	private DrawTextManager textManager;
	
	public void loadResources(){
		healthSprite = new SpriteMap(GFXResourceID.UI_HEALTH.getFilePath(), 8, 8);
		borderSprite = new SpriteMap(GFXResourceID.UI_BORDERS.getFilePath(), 8, 8);
		textManager = new DrawTextManager(GFXResourceID.TEXT_BLACK.getFilePath());
		collectableSprites = SpriteManager.getInstance().getSprite(SpriteID.COLLECTABLES);
	}
	
	public void draw(MenuManager menuManager){
		PlayerStatusManager playerStatus = PlayerStatusManager.getInstance();
		int internalYPosition = menuManager.getInternalYPosition();
		drawOnscreenUI(internalYPosition, playerStatus);
		if(internalYPosition < MenuManager.defaultYPosition){
			drawMenuUI(internalYPosition, playerStatus);
		}
	}
	
	private void drawOnscreenUI(int yPosition, PlayerStatusManager playerStatus){
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(0, 0, yPosition, GameWindowConstants.DEFAULT_SCREEN_WIDTH, 64f);
		drawBorders(yPosition);
		//Draw Items
		drawItems(yPosition);
		//Health
		drawBubbles(yPosition + 32, playerStatus.getMaxHealth(), playerStatus.getCurrentHealth(), 0);
		//Mana
		drawBubbles(yPosition + 16, 0, 0, 1);
		//Collectables
		drawCollectables(yPosition + 40);
	}
	
	private void drawCollectables(int yPosition) {
		//24 pixels on Y axis
		//Money
		collectableSprites.setColorValues(0f, 0.8f, 1f, 1f);
		collectableSprites.draw(0, 0, yPosition + 13);
		collectableSprites.setColorValues(1f, 1f, 1f, 1f);
		int moneyAmount = PlayerStatusManager.getInstance().getCurrentMoney();
		String money = String.format("%03d", moneyAmount);
		textManager.DrawText(10, yPosition + 14, money, 3);
		//Bombs
		collectableSprites.draw(4, 0, yPosition + 1);
		int bombAmount = 0;
		String bombs = String.format("%02d", bombAmount);
		textManager.DrawText(20, yPosition + 2, bombs, 2);
	}

	private void drawBorders(int yPosition){
		//Bottom Border
		borderSprite.draw(0, 0, yPosition);
		borderSprite.draw(1, 31*8, yPosition);
		for(int x = 1; x < 31; x++){
			borderSprite.draw(4, x * 8, yPosition);
		}
	}
	
	private void drawItems(int yPosition){ //TODO: Add ItemIDA and ItemIDB
		//Item Borders
		int healthEnding = healthPerLine * 8;
		drawItemBorder(healthEnding, yPosition + 8);
		textManager.DrawText(healthEnding + 32, yPosition + 24, "A", 1);
		SpriteManager.getInstance().getSprite(SpriteID.ITEMS).draw(6, healthEnding + 8, yPosition + 16);
		drawItemBorder(healthEnding + 48, yPosition + 8);
		textManager.DrawText(healthEnding + 80, yPosition + 24, "B", 1);
		SpriteManager.getInstance().getSprite(SpriteID.ITEMS).draw(0, healthEnding + 56, yPosition + 16);
	}
	private void drawItemBorder(int x, int y){
		//Bottom
		borderSprite.draw(6, x, y);
		borderSprite.draw(4, x + 8, y);
		borderSprite.draw(4, x + 16, y);
		borderSprite.draw(7, x + 24, y);
		//Top
		borderSprite.draw(8, x, y + 24);
		borderSprite.draw(4, x + 8, y + 24);
		borderSprite.draw(4, x + 16, y + 24);
		borderSprite.draw(9, x + 24, y + 24);
	}
	
	private void drawBubbles(int yPosition, int maxValue, int currentValue, int color){
		boolean drawHalfBubble = currentValue % 2 == 1;
		int numberBubbles = (int) Math.ceil(maxValue / 2f);
		int fullBubbles = (int) Math.floor(currentValue / 2f);
		if(fullBubbles > numberBubbles){
			fullBubbles = numberBubbles;
			drawHalfBubble = false;
		}
		int counter = 0;
		for(int i = counter; i < fullBubbles; i++){
			int posX = (i % healthPerLine) * 8;
			int posY = yPosition - 8 * (int)Math.floor(1f * i / healthPerLine);
			healthSprite.draw(color * 4, posX, posY);
			counter++;
		}
		if(drawHalfBubble){
			int posX = (counter % healthPerLine) * 8;
			int posY = yPosition - 8 * (int)Math.floor(1f * counter / healthPerLine);
			healthSprite.draw(color * 4 + 1, posX, posY);
			counter++;
		}
		for(int i = counter; i < numberBubbles; i++){
			int posX = (i % healthPerLine) * 8;
			int posY = yPosition - 8 * (int)Math.floor(1f * i / healthPerLine);
			healthSprite.draw(color * 4 + 2, posX, posY);
			counter++;
		}
	}
	
	private void drawMenuUI(int yPosition, PlayerStatusManager playerStatus){
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(0, 0, yPosition + 64, GameWindowConstants.DEFAULT_SCREEN_WIDTH, 192f);
	}

}