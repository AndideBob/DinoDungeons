package dinodungeons.editor;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.map.BaseLayerTile;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.ScreenMapConstants;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.ItemMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.data.map.objects.TransportMapObject;
import dinodungeons.gfx.GFXResourceID;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.text.DrawTextManager;
import dinodungeons.gfx.tilesets.TileSet;
import dinodungeons.gfx.tilesets.TilesetManager;
import lwjgladapter.gfx.Texture;

public class EditorDrawManager {
	
	private DrawTextManager textManager;
	private TilesetManager tileSetManager = new TilesetManager();
	private Texture selectorTexture;
	
	private ScreenMap currentMap;
	
	public void loadTextures(){
		tileSetManager.loadResources();
		textManager = new DrawTextManager(GFXResourceID.TEXT_WHITE.getFilePath());
		selectorTexture = new Texture(GFXResourceID.EDITOR_SELECTOR.getFilePath());
	}
	
	public void setCurrentMap(ScreenMap currentMap) {
		this.currentMap = currentMap;
	}
	
	public void drawSelector(int x, int y) {
		selectorTexture.draw(x * 16, y * 16);
	}
	
	public void drawInfoText(String text) {
		textManager.DrawText(0, 202, text, 25);
	}
	
	public void drawEnteredText(String enteredText) {
		textManager.DrawText(0, 192, enteredText, 4);
		if(enteredText.length() < 4) {
			textManager.DrawText(enteredText.length() * 10, 192, "|", 1);
		}
	}

	public void drawMap(){
		for(int x = 0; x < currentMap.getSizeX(); x++){
			for(int y = 0; y < currentMap.getSizeY(); y++){
				BaseLayerTile tile = currentMap.getBaseLayerTileForPosition(x, y);
				TileSet tileSet = currentMap.getTileSet();
				tileSetManager.drawTile(tile, tileSet, x * 16, y * 16);
			}
		}
	}
	
	public void drawObjectsLayer(){
		for(int x = 0; x < currentMap.getSizeX(); x++){
			for(int y = 0; y < currentMap.getSizeY(); y++){
				MapObject mapObject = currentMap.getMapObjectForPosition(x, y);
				drawMapObject(mapObject, x, y);
			}
		}
	}
	
	private void drawMapObject(MapObject mapObject, int x, int y) {
		if(mapObject instanceof EmptyMapObject){
			//Do not draw anything
			return;
		}
		if(mapObject instanceof TransportMapObject){
			TransportMapObject transport = (TransportMapObject) mapObject;
			String txtUpL = "";
			String txtLowL = "";
			String txtUpR = "";
			String txtLowR = "";
			switch(transport.getTransportationType()){
			case CAVE_ENTRY:
				txtUpL = "C";
				txtUpR = "V";
				txtLowL = "E";
				txtLowR = "N";
				break;
			case CAVE_EXIT:
				txtUpL = "C";
				txtUpR = "V";
				txtLowL = "E";
				txtLowR = "X";
				break;
			case DUNGEON_EXIT:
				txtUpL = "D";
				txtUpR = "N";
				txtLowL = "E";
				txtLowR = "X";
				break;
			case INSTANT_TELEPORT:
				txtUpL = "I";
				txtUpR = "N";
				txtLowL = "T";
				txtLowR = "P";
				break;
			case STAIRS:
				txtUpL = "S";
				txtUpR = "T";
				txtLowL = "R";
				txtLowR = "S";
				break;
			}
			textManager.DrawText(x * 16 - 1, y * 16 + 8, txtUpL, 1);
			textManager.DrawText(x * 16 + 7, y * 16 + 8, txtUpR, 1);
			textManager.DrawText(x * 16 - 1, y * 16, txtLowL, 1);
			textManager.DrawText(x * 16 + 7, y * 16, txtLowR, 1);
			return;
		}
		else if(mapObject instanceof ItemMapObject){
			ItemMapObject item = (ItemMapObject) mapObject;
			SpriteManager.getInstance().getSprite(SpriteID.ITEMS).draw(item.getItemID(), x * 16, y * 16);
		}
	}

	public void drawUI(EditorState currentState, int currentSelection) {
		textManager.DrawText(0, 247, "[F1]Save", 9);
		textManager.DrawText(0, 238, "[F2]Load", 9);
		textManager.DrawText(0, 229, "[F3]Exits", 10);
		textManager.DrawText(96, 247, "[</>]", 5);
		//ToolSelection
		int optionsShown = 3;
		int lowest;
		int highest;
		switch(currentState) {
		case INSPECTOR:
			textManager.DrawText(146, 247, "Inspector", 10);
			break;
		case PLACE_BASELAYER:
			textManager.DrawText(146, 247, "Base layer", 10);
			optionsShown = 3;
			lowest = Math.max(ScreenMapConstants.minBaseLayerSelection, currentSelection-optionsShown);
			highest = Math.min(ScreenMapConstants.maxBaseLayerSelection, lowest+optionsShown);
			for(int i = lowest; i <= highest; i++) {
				String text = i == currentSelection ? ">" : " ";
				text += "[" + i + "]";
				switch(i) {
				case 0:
					text += "wall";
					break;
				case 1:
					text += "border";
					break;
				case 2:
					text += "floor a";
					break;
				case 3:
					text += "floor b";
					break;
				case 4:
					text += "floor c";
					break;
				case 5:
					text += "stairs";
					break;
				}
				textManager.DrawText(136, 211 + 9*optionsShown, text, 16);
				optionsShown--;
			}
			break;
		case CHANGE_TILESET:
			textManager.DrawText(146, 247, "Tile Set", 10);
			lowest = Math.max(0, currentSelection-optionsShown);
			highest = Math.min(Editor.tileSets.length -1, lowest+optionsShown);
			for(int i = lowest; i <= highest; i++) {
				String text = i == currentSelection ? ">" : " ";
				text += "[" + i + "]";
				text += Editor.tileSets[i].getRepresentationInFile();
				textManager.DrawText(126, 211 + 9*optionsShown, text, 16);
				optionsShown--;
			}
			break;
		case PLACE_EXITS:
			textManager.DrawText(146, 247, "Exits", 10);
			lowest = Math.max(0, currentSelection-optionsShown);
			highest = Math.min(Editor.transportationTypes.length -1, lowest+optionsShown);
			for(int i = lowest; i <= highest; i++) {
				String text = i == currentSelection ? ">" : " ";
				text += "[" + i + "]";
				switch(Editor.transportationTypes[i]){
				case CAVE_ENTRY:
					text += "Cave En";
					break;
				case CAVE_EXIT:
					text += "Cave Ex";
					break;
				case DUNGEON_EXIT:
					text += "Dngn Ex";
					break;
				case INSTANT_TELEPORT:
					text += "Instant";
					break;
				case STAIRS:
					text += "Stairs";
					break;
				}
				
				textManager.DrawText(126, 211 + 9*optionsShown, text, 16);
				optionsShown--;
			}
			break;
		case PLACE_ITEMS:
			textManager.DrawText(146, 247, "Items", 10);
			optionsShown = 3;
			lowest = Math.max(0, currentSelection-optionsShown);
			highest = Math.min(DinoDungeonsConstants.numberOfItems -1, lowest+optionsShown);
			for(int i = lowest; i <= highest; i++) {
				String text = i == currentSelection ? ">" : " ";
				text += "[" + i + "]";
				switch(i) {
				case 0:
					text += "Item 0";
					break;
				case 1:
					text += "Item 1";
					break;
				case 2:
					text += "Item 2";
					break;
				case 3:
					text += "Item 3";
					break;
				case 4:
					text += "Item 4";
					break;
				case 5:
					text += "Item 5";
					break;
				case 6:
					text += "Mirror";
					break;
				case 7:
					text += "Item 7";
					break;
				case 8:
					text += "Item 8";
					break;
				case 9:
					text += "Item 9";
					break;
				case 10:
					text += "Item A";
					break;
				case 11:
					text += "Item B";
					break;
				case 12:
					text += "Item C";
					break;
				case 13:
					text += "Item D";
					break;
				case 14:
					text += "Item E";
					break;
				case 15:
					text += "Item F";
					break;
				}
				textManager.DrawText(136, 211 + 9*optionsShown, text, 16);
				optionsShown--;
			}
			break;
		case ENTER_TEXT:
		default:
			break;
		}
	}

	

	

}
