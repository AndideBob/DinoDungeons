package dinodungeons.game.data;

import java.util.Random;

public class DinoDungeonsConstants {
	
	public static final Random random = new Random(System.nanoTime());
	
	// SCREEN-RELEATED CONSTANTS 
	public static final int mapWidth = 256;
	public static final int mapHeight = 192;

	public static final int scrollBoundryDown = -8;
	public static final int scrollBoundryUp = 192-8;
	
	public static final int scrollBoundryLeft = -8;
	public static final int scrollBoundryRight = mapWidth-8;
	
	public static final int scrollPositionChangeY = scrollBoundryUp - scrollBoundryDown;
	public static final int scrollPositionChangeX = scrollBoundryRight - scrollBoundryLeft;
	
	// UI-RELEATED CONSTANTS
	public static final int textboxCharactersPerSecond = 21;
	
	public static final int textboxPosX = 16;
	public static final int textboxPosY = 16;
	
	public static final int textboxWidth = 224;
	public static final int textboxHeight = 64;
	
	public static final int textboxLettersPerLine = 21;
	public static final int textboxLineAmount = 5;
	
	// TIMING-RELEATED CONSTANTS
	public static final long menuTransitionDurationInMs = 1000;
	public static final long fadeTransitionDurationInMs = 750;
	public static final long scrollTransitionDurationInMs = 1000;
	public static final long itemCollectionCharacterFreeze = 2000;
	public static final long dungeonItemCollectionCharacterFreeze = 750;
	public static final long damageTime = 200;
	public static final long invulnerabilityTime = 500;
	public static final long stunTime = 1250;
	public static final long stunKnockbackTime = 300;
	public static final long dropItemDuration = 100;
	
	public static final long bombFuseTimer = 3000;
	public static final long bombBlinkTimerStart = bombFuseTimer / 10;
	public static final long bombBlinkTimerReduction = bombBlinkTimerStart / 20;
	public static final long minBombBlinkTimer = 30;
	public static final long explosionDurationTime = 300;
	public static final long pushDelay = 600;	
	public static final long clubStageTime = 80;
	public static final long doorSpawnDelay = 80;
	
	// GAMEPLAY-RELEATED CONSTANTS 
	public static final int numberOfItems = 16;
	public static final int numberOfSpikes = 3;
	public static final int numberOfDestructables = 1;
	
	public static final int maxMoneyAmount = 999;
	public static final int maxBombAmountBasic = 10;
	public static final int maxBombAmountAdvanced = 20;
	public static final int maxKeyAmount = 99;
	
	public static final int directionDown = 0;
	public static final int directionLeft = 1;
	public static final int directionUp = 2;
	public static final int directionRight = 3;

	

	
	

}
