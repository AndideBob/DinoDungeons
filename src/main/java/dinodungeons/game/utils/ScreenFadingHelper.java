package dinodungeons.game.utils;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.transitions.TransitionType;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;

public class ScreenFadingHelper {

	private long fadeTimer = 0;
	private float fadeProgress = 0f;
	
	private int fadeCenterX;
	private int fadeCenterY;
	
	private boolean fadeIn;
	
	private String destinationMapID;
	
	private int destinationX;
	private int destinationY;
	
	private boolean shouldTransition;
	
	public ScreenFadingHelper() {
		shouldTransition = false;
	}
	
	public void update(long deltaTimeInMs) {
		if((fadeTimer > 0 && fadeIn) || (fadeTimer < DinoDungeonsConstants.fadeTransitionDurationInMs && !fadeIn)) {
			fadeTimer += fadeIn ? -deltaTimeInMs : deltaTimeInMs;
			fadeProgress = 1f - ((1f * fadeTimer) / DinoDungeonsConstants.fadeTransitionDurationInMs);
		}
		else {
			if(fadeIn) {
				shouldTransition = true;
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
		float topProgress = (DinoDungeonsConstants.mapHeight - fadeCenterY) * fadeProgress;
		float leftProgress = fadeCenterX * fadeProgress;
		float rightProgress = (DinoDungeonsConstants.mapWidth - fadeCenterX) * fadeProgress;
		//Bottom Bar
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(2, 0, 0, DinoDungeonsConstants.mapWidth, bottomProgress);
		//Top Bar
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(2, 0, DinoDungeonsConstants.mapHeight - (int)topProgress, DinoDungeonsConstants.mapWidth, topProgress);
		//Left Bar
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(2, 0, 0, leftProgress, DinoDungeonsConstants.mapHeight);
		//Top Bar
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(2, DinoDungeonsConstants.mapWidth - (int)rightProgress, 0, rightProgress, DinoDungeonsConstants.mapHeight);
	}
	
	public boolean fadingIn() {
		return fadeIn;
	}
	
	public boolean fadeFinished() {
		return !fadeIn && fadeTimer >= DinoDungeonsConstants.fadeTransitionDurationInMs;
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

	public String getDestinationMapID() {
		return destinationMapID;
	}

	public void setDestination(String destinationMapID, int x, int y) {
		this.destinationMapID = destinationMapID;
		destinationX = x;
		destinationY = y;
	}

	public int getDestinationX() {
		return destinationX;
	}

	public int getDestinationY() {
		return destinationY;
	}

	public boolean shouldTransition() {
		boolean result = shouldTransition;
		shouldTransition = false;
		return result;
	}	
}
