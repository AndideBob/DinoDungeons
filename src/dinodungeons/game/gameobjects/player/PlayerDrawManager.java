package dinodungeons.game.gameobjects.player;

import dinodungeons.game.gameobjects.player.PlayerObject.PlayerState;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.gfx.SpriteMap;

public class PlayerDrawManager {

	private static final long msBetweenFrames = 150;
	private static final long msBetweenBlinks = 30;
	private long msSinceLastFrame;
	private long msSinceLastBlink;
	
	private int frameNumber;
	private boolean showEvenFrame;
	private boolean isBlinking;
	
	private PlayerState playerState;
	private int movementDirection;
	private boolean hasMoved;
	private int collectedItemSpriteSheetPosition;
	
	public PlayerDrawManager() {
		isBlinking = false;
		msSinceLastFrame = msBetweenFrames;
		msSinceLastBlink = msBetweenBlinks;
		playerState = PlayerState.DEFAULT;
	}
	
	public void setPlayerState(PlayerState playerState){
		if(this.playerState != playerState){
			msSinceLastFrame = 0;
		}
		this.playerState = playerState;
	}
	
	public void setMovemet(boolean hasMoved, int movementDirection){
		this.hasMoved = hasMoved;
		this.movementDirection = movementDirection;
	}
	
	public void setCollectedItem(ItemID collectedItem){
		this.collectedItemSpriteSheetPosition = collectedItem.getSpriteSheetPosition();
	}
	
	public void setCollectedItem(DungeonItemID collectedItem){
		this.collectedItemSpriteSheetPosition = collectedItem.getSpriteSheetPosition();
	}
	
	public void update(long deltaTimeInMs){
		boolean frameChange = false;
		if(msSinceLastFrame > 0){
			msSinceLastFrame -= deltaTimeInMs;
		}
		else{
			msSinceLastFrame = msBetweenFrames;
			frameChange = true;
		}
		int actionNumber = 0;
		int directionNumber = movementDirection;
		switch(playerState){
		case DEFAULT:
			if(frameChange && hasMoved){
				showEvenFrame = !showEvenFrame;
			}
			isBlinking = false;
			break;
		case PUSHING:
			actionNumber = 1;
			directionNumber = movementDirection;
			if(frameChange){
				showEvenFrame = !showEvenFrame;
			}
			isBlinking = false;
			break;
		case ITEM_COLLECTED:
			actionNumber = 3;
			directionNumber = 0;
			showEvenFrame = false;
			isBlinking = false;
			break;
		case STUNNED:
		case DAMAGE_TAKEN:
			if(msSinceLastBlink > 0){
				msSinceLastBlink -= deltaTimeInMs;
			}
			else{
				msSinceLastBlink = msBetweenBlinks;
				isBlinking = !isBlinking;
			}
			actionNumber = 0;
			showEvenFrame = false;
			break;
		case USING_ITEM:
			actionNumber = 3;
			showEvenFrame = true;
			isBlinking = false;
			break;
		}
		frameNumber = (actionNumber * 8) + (directionNumber * 2) + (showEvenFrame ? 0 : 1);
	}
	
	public void draw(int x, int y){
		SpriteMap characterSprite = SpriteManager.getInstance().getSprite(SpriteID.PLAYER);
		if(isBlinking){
			if(playerState == PlayerState.STUNNED) {
				characterSprite.setColorValues(0f, 0.5f, 1f, 1f);
			}
			else {
				characterSprite.setColorValues(1f, 0f, 0f, 1f);
			}
			characterSprite.draw(frameNumber, x, y);
			characterSprite.setColorValues(1f, 1f, 1f, 1f);
		}
		else{
			characterSprite.draw(frameNumber, x, y);
		}
		switch (playerState) {
		case ITEM_COLLECTED:
			SpriteManager.getInstance().getSprite(SpriteID.ITEMS).draw(collectedItemSpriteSheetPosition, x, y + 16);
		default:
			break;
		}
	}

}
