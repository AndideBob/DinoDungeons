package dinodungeons.game.data;

public class DinoDungeonsConstants {
	
	// SCREEN-RELEATED CONSTANTS 
	public static final int mapWidth = 256;
	public static final int mapHeight = 192;

	public static final int scrollBoundryDown = -8;
	public static final int scrollBoundryUp = 192-8;
	
	public static final int scrollBoundryLeft = -8;
	public static final int scrollBoundryRight = mapWidth-8;
	
	public static final int scrollPositionChangeY = scrollBoundryUp - scrollBoundryDown;
	public static final int scrollPositionChangeX = scrollBoundryRight - scrollBoundryLeft;
	
	// TIMING-RELEATED CONSTANTS
	public static final long fadeTransitionDurationInMs = 750;
	public static final long scrollTransitionDurationInMs = 1000;
	public static final long itemCollectionCharacterFreeze = 2000;
	public static final long damageTime = 200;
	public static final long invulnerabilityTime = 500;
	
	// GAMEPLAY-RELEATED CONSTANTS 
	public static final int numberOfItems = 16;
	public static final int numberOfSpikes = 3;

}
