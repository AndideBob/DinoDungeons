package dinodungeons.game.data.transitions;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.transitions.TransitionType;
import lwjgladapter.logging.Logger;

public class TransitionManager {

	private static TransitionManager instance;
	
	public static TransitionManager getInstance(){
		if(instance == null){
			instance = new TransitionManager();
		}
		return instance;
	}
	
	private ScreenTransition nextTransition;
	private boolean shouldTransition;
	
	private ScreenMap currentMap;
	
	private TransitionManager() {
		shouldTransition = false;
		nextTransition = null;
	}

	public ScreenTransition getNextTransition() {
		shouldTransition = false;
		return nextTransition;
	}

	public boolean shouldTransition() {
		return shouldTransition;
	}
	
	public void setCurrentMap(ScreenMap currentMap) {
		this.currentMap = currentMap;
	}

	public void initiateTransition(String destinationMapID, int destinationXPosition, int destinationYPosition,
			TransitionType transitionType){
		if(!shouldTransition){
			shouldTransition = true;
			nextTransition = new ScreenTransition(destinationMapID, destinationXPosition, destinationYPosition, transitionType);
		}
	}

	public void initiateScrollTransitionUp(int playerPositionX, int playerPositionY){
		if(isCurrentMapSet()){
			initiateTransition(currentMap.getTransitionUpID(), playerPositionX,
					playerPositionY - DinoDungeonsConstants.scrollPositionChangeY, TransitionType.SCROLL_UP);
		}
	}
	
	public void initiateScrollTransitionDown(int playerPositionX, int playerPositionY){
		if(isCurrentMapSet()){
			initiateTransition(currentMap.getTransitionDownID(), playerPositionX,
					playerPositionY + DinoDungeonsConstants.scrollPositionChangeY, TransitionType.SCROLL_DOWN);
		}
	}
	
	public void initiateScrollTransitionLeft(int playerPositionX, int playerPositionY){
		if(isCurrentMapSet()){
			initiateTransition(currentMap.getTransitionLeftID(), playerPositionX + DinoDungeonsConstants.scrollPositionChangeX,
					playerPositionY, TransitionType.SCROLL_LEFT);
		}
	}
	
	public void initiateScrollTransitionRight(int playerPositionX, int playerPositionY){
		if(isCurrentMapSet()){
			initiateTransition(currentMap.getTransitionRightID(), playerPositionX - DinoDungeonsConstants.scrollPositionChangeX,
					playerPositionY, TransitionType.SCROLL_RIGHT);
		}
	}
	
	private boolean isCurrentMapSet(){
		if(currentMap == null){
			Logger.logError("Can't scroll transition, because CurrentMap is not set!");
			return false;
		}
		return true;
	}
}
