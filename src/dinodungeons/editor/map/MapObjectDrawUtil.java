package dinodungeons.editor.map;

import dinodungeons.game.data.map.BaseLayerTile;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.BlockMapObject;
import dinodungeons.game.data.map.objects.DestructibleMapObject;
import dinodungeons.game.data.map.objects.DoorMapObject;
import dinodungeons.game.data.map.objects.DoorMapObject.DoorType;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.EnemyMapObject;
import dinodungeons.game.data.map.objects.ItemMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.data.map.objects.SpikeMapObject;
import dinodungeons.game.data.map.objects.TransportMapObject;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.text.DrawTextManager;
import dinodungeons.gfx.tilesets.TilesetManager;

public class MapObjectDrawUtil {

	public static void drawMapObject(MapObject mapObject, ScreenMap currentMap, int x, int y) {
		if(mapObject instanceof EmptyMapObject){
			return;
		}
		int colorVariation = currentMap.getTileSet().getColorVariation();
		if(mapObject instanceof TransportMapObject){
			drawTransportMapObject((TransportMapObject) mapObject, currentMap, x, y);
		}
		else if(mapObject instanceof ItemMapObject){
			drawItemMapObject((ItemMapObject)mapObject, x, y);
		}
		else if(mapObject instanceof SpikeMapObject){
			drawSpikeMapObject((SpikeMapObject)mapObject, x, y);
		}
		else if(mapObject instanceof DestructibleMapObject){
			drawDestructibleMapObject((DestructibleMapObject)mapObject, colorVariation, x, y);
		}
		else if(mapObject instanceof EnemyMapObject){
			drawEnemyMapObject((EnemyMapObject)mapObject, x, y);
		}
		else if(mapObject instanceof BlockMapObject){
			drawBlockMapObject((BlockMapObject) mapObject, colorVariation, x, y);
		}
		else if(mapObject instanceof DoorMapObject){
			drawDoorMapObject((DoorMapObject) mapObject, colorVariation, x, y);
		}
	}
	
	private static void drawTextOverlay(int x, int y, char... sings){
		int counter = 0;
		for(char c : sings){
			String sign = "" + c;
			switch (counter) {
			case 0:
				DrawTextManager.getInstance().drawText(x * 16 - 1, y * 16 + 8, sign, 1);
				break;
			case 1:
				DrawTextManager.getInstance().drawText(x * 16 + 7, y * 16 + 8, sign, 1);
				break;
			case 2:
				DrawTextManager.getInstance().drawText(x * 16 - 1, y * 16, sign, 1);
				break;
			case 3:
				DrawTextManager.getInstance().drawText(x * 16 + 7, y * 16, sign, 1);
				break;
			}
			counter++;
		}
	}

	private static void drawTransportMapObject(TransportMapObject transportMapObject, ScreenMap currentMap, int x, int y){
		switch(transportMapObject.getTransportationType()){
		case BLOCKED_CAVE_ENTRY:
			switch(currentMap.getBaseLayerTileForPosition(x, y)){
			case DOOR_DOWN:
				TilesetManager.getInstance().drawTile(BaseLayerTile.BLOCKED_DOOR_DOWN, currentMap.getTileSet(), x * 16, y * 16);
				break;
			case DOOR_LEFT:
				TilesetManager.getInstance().drawTile(BaseLayerTile.BLOCKED_DOOR_LEFT, currentMap.getTileSet(), x * 16, y * 16);
				break;
			case DOOR_RIGHT:
				TilesetManager.getInstance().drawTile(BaseLayerTile.BLOCKED_DOOR_RIGHT, currentMap.getTileSet(), x * 16, y * 16);
				break;
			case DOOR_UP:
				TilesetManager.getInstance().drawTile(BaseLayerTile.BLOCKED_DOOR_UP, currentMap.getTileSet(), x * 16, y * 16);
				break;
			default:
				return;
			}
			drawTextOverlay(x, y, 'C', 'V', 'B', 'K');
			break;
		case CAVE_ENTRY:
			drawTextOverlay(x, y, 'C', 'V', 'E', 'N');
			break;
		case CAVE_EXIT:
			drawTextOverlay(x, y, 'C', 'V', 'E', 'X');
			break;
		case INSTANT_TELEPORT:
			drawTextOverlay(x, y, 'I', 'N', 'T', 'P');
			break;
		case STAIRS:
			drawTextOverlay(x, y, 'S', 'T', 'R', 'S');
			break;
		}
	}
	
	private static void drawItemMapObject(ItemMapObject itemMapObject, int x, int y){
		SpriteManager.getInstance().getSprite(SpriteID.ITEMS).draw(itemMapObject.getItemID().getSpriteSheetPosition(), x * 16, y * 16);
	}
	
	private static void drawSpikeMapObject(SpikeMapObject spikeMapObject, int x, int y){
		SpriteManager.getInstance().getSprite(SpriteID.SPIKES).draw(spikeMapObject.getSpikeType() * 2, x * 16, y * 16);
	}
	
	private static void drawDestructibleMapObject(DestructibleMapObject destructibleMapObject, int colorVariation, int x, int y){
		SpriteManager.getInstance().getSprite(SpriteID.DESTRUCTABLES).draw(destructibleMapObject.getDestructableType() * 8 + colorVariation, x * 16, y * 16);
	}
	
	private static void drawEnemyMapObject(EnemyMapObject enemyMapObject, int x, int y){
		enemyMapObject.draw(x * 16, y * 16);
	}
	
	private static void drawBlockMapObject(BlockMapObject blockMapObject, int colorVariation, int x, int y){
		SpriteManager.getInstance().getSprite(SpriteID.PUSHABLES).draw(colorVariation, x * 16, y * 16);
		switch(blockMapObject.getBlockType()){
		case SOLID:
			break;
		case NO_SWITCH:
			drawTextOverlay(x, y, 'X');
			break;
		case SWITCH_A:
			drawTextOverlay(x, y, 'A');
			break;
		case SWITCH_AB:
			drawTextOverlay(x, y, 'A', 'B');
			break;
		case SWITCH_ABC:
			drawTextOverlay(x, y, 'A', 'B', 'C');
			break;
		case SWITCH_ABCD:
			drawTextOverlay(x, y, 'A', 'B', 'C', 'D');
			break;
		case SWITCH_B:
			drawTextOverlay(x, y, 'B');
			break;
		case SWITCH_C:
			drawTextOverlay(x, y, 'C');
			break;
		case SWITCH_D:
			drawTextOverlay(x, y, 'D');
			break;
		}
	}
	
	private static void drawDoorMapObject(DoorMapObject doorMapObject, int colorVariation, int x, int y){
		if(doorMapObject.getDoorType() == DoorType.KEY){
			SpriteManager.getInstance().getSprite(SpriteID.DOORS).draw(8 + colorVariation, x * 16, y * 16);
		}
		else if(doorMapObject.getDoorType() == DoorType.MASTER_KEY){
			SpriteManager.getInstance().getSprite(SpriteID.DOORS).draw(16 + colorVariation, x * 16, y * 16);
		}
		else{
			SpriteManager.getInstance().getSprite(SpriteID.DOORS).draw(colorVariation, x * 16, y * 16);
			switch(doorMapObject.getDoorType()){
			case SWITCH_A:
				drawTextOverlay(x, y, 'A');
				break;
			case SWITCH_AB:
				drawTextOverlay(x, y, 'A', 'B');
				break;
			case SWITCH_ABC:
				drawTextOverlay(x, y, 'A', 'B', 'C');
				break;
			case SWITCH_ABCD:
				drawTextOverlay(x, y, 'A', 'B', 'C', 'D');
				break;
			case SWITCH_B:
				drawTextOverlay(x, y, 'B');
				break;
			case SWITCH_C:
				drawTextOverlay(x, y, 'C');
				break;
			case SWITCH_D:
				drawTextOverlay(x, y, 'D');
				break;
			default:
				break;
			}
		}
	}
}
