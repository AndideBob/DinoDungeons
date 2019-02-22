package dinodungeons.game.data.transitions;

import java.util.Arrays;
import java.util.List;

public enum TransitionType{
	INSTANT,
	SCROLL_UP,
	SCROLL_DOWN,
	SCROLL_LEFT,
	SCROLL_RIGHT,
	TELEPORT;
	
	private static final List<TransitionType> scrollTransitions = Arrays.asList(SCROLL_DOWN, SCROLL_LEFT, SCROLL_RIGHT, SCROLL_UP);
	
	public boolean isScrollTransition(){
		return scrollTransitions.contains(this);
	}
}
