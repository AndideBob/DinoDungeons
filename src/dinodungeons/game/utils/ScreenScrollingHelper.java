package dinodungeons.game.utils;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.transitions.TransitionType;

public class ScreenScrollingHelper {

	private long scrollTimer = 0;
	private int scrollX = 0;
	private int scrollY = 0;
	private float scrollProgress = 1f;
	
	private int offsetOldX;
	private int offsetOldY;
	private int offsetNewX;
	private int offsetNewY;
	
	public ScreenScrollingHelper() {
		
	}
	
	public void update(long deltaTimeInMs) {
		if(scrollTimer > 0) {
			scrollTimer -= deltaTimeInMs;
			scrollProgress = 1f - ((1f * scrollTimer) / DinoDungeonsConstants.scrollTransitionDurationInMs);
			offsetOldX = Math.round(scrollX * DinoDungeonsConstants.mapWidth * scrollProgress);
			offsetOldY = Math.round(scrollY * DinoDungeonsConstants.mapHeight * scrollProgress);
			offsetNewX = (scrollX > 0 ? -DinoDungeonsConstants.mapWidth : scrollX < 0 ? DinoDungeonsConstants.mapWidth : 0) + offsetOldX;
			offsetNewY = (scrollY > 0 ? -DinoDungeonsConstants.mapHeight : scrollY < 0 ? DinoDungeonsConstants.mapHeight : 0) + offsetOldY;
		}
		else {
			scrollProgress = 1f;
		}
	}
	
	public boolean scrollingFinished() {
		return scrollProgress >= 1f;
	}
	
	public int getOffsetOldX() {
		return offsetOldX;
	}

	public int getOffsetOldY() {
		return offsetOldY;
	}

	public int getOffsetNewX() {
		return offsetNewX;
	}

	public int getOffsetNewY() {
		return offsetNewY;
	}

	public void startScrolling(TransitionType transitionType) {
		switch(transitionType){
		case SCROLL_DOWN:
			scrollX = 0;
			scrollY = 1;
			break;
		case SCROLL_LEFT:
			scrollX = 1;
			scrollY = 0;
			break;
		case SCROLL_RIGHT:
			scrollX = -1;
			scrollY = 0;
			break;
		case SCROLL_UP:
			scrollX = 0;
			scrollY = -1;
			break;
		default:
			scrollX = 0;
			scrollY = 0;
			return;
		}
		scrollTimer = DinoDungeonsConstants.scrollTransitionDurationInMs;
		scrollProgress = 0f;
		update(0);
	}
}
