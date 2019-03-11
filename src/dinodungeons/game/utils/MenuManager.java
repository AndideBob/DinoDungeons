package dinodungeons.game.utils;

import dinodungeons.game.data.DinoDungeonsConstants;
import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;

public class MenuManager {

	public static final int defaultYPosition = 192;
	
	private int internalYPosition;
	
	private long transitionTimer;
	private boolean scrollingIn;
	
	public MenuManager() {
		scrollingIn = false;
		transitionTimer = 0;
		internalYPosition = defaultYPosition;
	}
	
	public void update(long deltaTimeInMs) {
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
		else {
			internalYPosition = scrollingIn ? 0 : defaultYPosition;
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_ESCAPE).equals(ButtonState.RELEASED)) {
				if(isInMenu()) {
					startTransitionOut();
				}
				else {
					startTransitionIn();
				}
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
}
