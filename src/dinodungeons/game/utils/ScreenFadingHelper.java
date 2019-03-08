package dinodungeons.game.utils;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.transitions.TransitionType;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.GameWindowConstants;

public class ScreenFadingHelper {

	private long fadeTimer = 0;
	private float fadeProgress = 0f;
	
	private int fadeCenterX;
	private int fadeCenterY;
	
	private boolean fadeIn;
	
	public ScreenFadingHelper() {
		
	}
	
	public void update(long deltaTimeInMs) {
		if(fadeTimer > 0) {
			fadeTimer += fadeIn ? deltaTimeInMs : -deltaTimeInMs;
			fadeProgress = 1f - ((1f * fadeTimer) / DinoDungeonsConstants.fadeTransitionDurationInMs);
		}
		else {
			if(fadeIn) {
				fadeIn = false;
				fadeTimer = 0;
			}
			else {
				fadeProgress = 1f;
			}
		}
	}
	
	public void drawFade() {
		float bottomProgress = fadeCenterY * fadeProgress;
		int topProgress = DinoDungeonsConstants.mapHeight - (int)bottomProgress;
		float leftProgress = fadeCenterX * fadeProgress;
		int rightProgress = DinoDungeonsConstants.mapWidth - (int)leftProgress;
		//Bottom Bar
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(2, 0, 0, DinoDungeonsConstants.mapWidth, bottomProgress);
		//Top Bar
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(2, 0, topProgress, DinoDungeonsConstants.mapWidth, bottomProgress);
		//Left Bar
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(2, 0, 0, leftProgress, DinoDungeonsConstants.mapHeight);
		//Top Bar
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(2, 0, rightProgress, leftProgress, DinoDungeonsConstants.mapHeight);
	}
	
	public boolean fadingIn() {
		return fadeIn;
	}
	
	public boolean fadeFinished() {
		return !fadeIn && fadeProgress >= 1f;
	}

	public void startFading(TransitionType transitionType, int playerCenterX, int playerCenterY) {
		switch(transitionType){
		case TELEPORT:
			fadeIn = true;
			break;
		default:
			fadeIn = false;
			return;
		}
		fadeCenterX = playerCenterX;
		fadeCenterY = playerCenterY;
		fadeProgress = 0f;
		fadeTimer = DinoDungeonsConstants.fadeTransitionDurationInMs;
		update(0);
	}
}
