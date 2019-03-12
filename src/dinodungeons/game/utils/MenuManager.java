package dinodungeons.game.utils;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import lwjgladapter.input.ButtonState;

public class MenuManager {

	public static final int defaultYPosition = 192;
	
	private int internalYPosition;
	
	private long transitionTimer;
	private boolean scrollingIn;
	
	private int currentSelection;
	
	public MenuManager() {
		scrollingIn = false;
		transitionTimer = 0;
		internalYPosition = defaultYPosition;
		currentSelection = 0;
	}
	
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(isInTransition()) {
			transitionTimer -= deltaTimeInMs;
			float transitionProgress = 1f - (1f * transitionTimer / DinoDungeonsConstants.menuTransitionDurationInMs);
			if(transitionProgress <= 0) {
				transitionProgress = 0;
			}
			if(scrollingIn) {
				internalYPosition = defaultYPosition - (int)Math.round(transitionProgress * defaultYPosition);
			}
			else {
				internalYPosition = (int)Math.round(transitionProgress * defaultYPosition);
			}
		}
		else if(isInMenu()){
			internalYPosition = 0;
			if(inputInformation.getStart().equals(ButtonState.RELEASED)) {
				startTransitionOut();
			}
			if(inputInformation.getDown().equals(ButtonState.RELEASED)){
				currentSelection += 3;
				if(currentSelection > 11){
					currentSelection = currentSelection % 12;
				}
			}
			if(inputInformation.getUp().equals(ButtonState.RELEASED)){
				currentSelection -= 3;
				if(currentSelection < 0){
					currentSelection += 12;
				}
			}
			if(inputInformation.getLeft().equals(ButtonState.RELEASED)){
				if(currentSelection % 3 == 0){
					currentSelection += 2;
				}
				else{
					currentSelection -= 1;
				}
			}
			if(inputInformation.getRight().equals(ButtonState.RELEASED)){
				if(currentSelection % 3 == 2){
					currentSelection -= 2;
				}
				else{
					currentSelection += 1;
				}
			}
		}
		else{
			internalYPosition = defaultYPosition;
			if(inputInformation.getStart().equals(ButtonState.RELEASED)) {
				startTransitionIn();
			}
		}
	}
	
	public void startTransitionIn() {
		scrollingIn = true;
		transitionTimer = DinoDungeonsConstants.menuTransitionDurationInMs;
	}
	
	public void startTransitionOut() {
		scrollingIn = false;
		transitionTimer = DinoDungeonsConstants.menuTransitionDurationInMs;
	}

	public int getInternalYPosition() {
		return internalYPosition;
	}

	public boolean isInTransition() {
		return transitionTimer > 0;
	}

	public boolean isInMenu() {
		return scrollingIn;
	}

	public int getCurrentMenuSelection() {
		return currentSelection;
	}
}
