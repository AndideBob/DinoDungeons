package dinodungeons.game.data;

public class DinoDungeonsConstants {
	
	public static final int mapWidth = 256;
	public static final int mapHeight = 192;

	public static final int scrollBoundryDown = -8;
	public static final int scrollBoundryUp = 192-8;
	
	public static final int scrollBoundryLeft = -8;
	public static final int scrollBoundryRight = mapWidth-8;
	
	public static final int scrollPositionChangeY = scrollBoundryUp - scrollBoundryDown;
	public static final int scrollPositionChangeX = scrollBoundryRight - scrollBoundryLeft;
	
	public static final long scrollTransitionDurationInMs = 1000;

}
