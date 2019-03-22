package dinodungeons.game.utils;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.game.gameobjects.player.ItemID;
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
			//START
			if(inputInformation.getStart().equals(ButtonState.RELEASED)) {
				startTransitionOut();
			}
			//NAVIGATE
			switchSelection(inputInformation);
			//SELECT
			selectItem(inputInformation);
		}
		else{
			internalYPosition = defaultYPosition;
			if(inputInformation.getStart().equals(ButtonState.RELEASED)) {
				startTransitionIn();
			}
		}
	}
	
	private void switchSelection(InputInformation inputInformation){
		if(inputInformation.getDown().equals(ButtonState.RELEASED)){
			currentSelection += 3;
			if(currentSelection > 11){
				currentSelection = currentSelection % 12;
			}
		}
		else if(inputInformation.getUp().equals(ButtonState.RELEASED)){
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
		else if(inputInformation.getRight().equals(ButtonState.RELEASED)){
			if(currentSelection % 3 == 2){
				currentSelection -= 2;
			}
			else{
				currentSelection += 1;
			}
		}
	}
	
	private void selectItem(InputInformation inputInformation){
		if(inputInformation.getA().equals(ButtonState.RELEASED)){
			ItemID selectedItem = getItemBySelection();
			if(selectedItem != null && PlayerStatusManager.getInstance().getCollectedItems().contains(selectedItem)){
				if(PlayerStatusManager.getInstance().getItemB() != null && PlayerStatusManager.getInstance().getItemB().equals(selectedItem)){
					PlayerStatusManager.getInstance().setItemB(PlayerStatusManager.getInstance().getItemA());
				}
				PlayerStatusManager.getInstance().setItemA(selectedItem);
			}
			else{
				PlayerStatusManager.getInstance().setItemA(null);
			}
		}
		if(inputInformation.getB().equals(ButtonState.RELEASED)){
			ItemID selectedItem = getItemBySelection();
			if(selectedItem != null && PlayerStatusManager.getInstance().getCollectedItems().contains(selectedItem)){
				if(PlayerStatusManager.getInstance().getItemA() != null && PlayerStatusManager.getInstance().getItemA().equals(selectedItem)){
					PlayerStatusManager.getInstance().setItemA(PlayerStatusManager.getInstance().getItemB());
				}
				PlayerStatusManager.getInstance().setItemB(selectedItem);
			}
			else{
				PlayerStatusManager.getInstance().setItemB(null);
			}
		}
	}
	
	private ItemID getItemBySelection()
	{
		switch (currentSelection) {
		case 0:
			return ItemID.CLUB;
		case 1:
			return ItemID.BOOMERANG;
		case 2:
			return ItemID.ITEM_2;
		case 3:
			return ItemID.BOMB;
		case 4:
			return ItemID.ITEM_4;
		case 5:
			return ItemID.ITEM_5;
		case 6:
			return ItemID.ITEM_6;
		case 7:
			return ItemID.ITEM_7;
		case 8:
			return ItemID.ITEM_8;
		case 9:
			return ItemID.ITEM_9;
		case 10:
			return ItemID.ITEM_A;
		case 11:
			return ItemID.ITEM_B;
		default:
			return null;
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
