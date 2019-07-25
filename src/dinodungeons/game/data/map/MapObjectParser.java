package dinodungeons.game.data.map;

import dinodungeons.editor.map.change.SignPlacementMapChange.SignType;
import dinodungeons.game.data.gameplay.RoomEvent;
import dinodungeons.game.data.map.objects.BlockMapObject;
import dinodungeons.game.data.map.objects.BuildingMapObject;
import dinodungeons.game.data.map.objects.CandleMapObject;
import dinodungeons.game.data.map.objects.BlockMapObject.BlockType;
import dinodungeons.game.data.map.objects.DestructibleMapObject;
import dinodungeons.game.data.map.objects.DestructibleMapObject.DestructableType;
import dinodungeons.game.data.map.objects.DoorMapObject;
import dinodungeons.game.data.map.objects.DoorMapObject.DoorType;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.EnemyMapObject;
import dinodungeons.game.data.map.objects.EnemyMapObject.EnemyType;
import dinodungeons.game.data.map.objects.ItemMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.data.map.objects.NonPlayerCharacterMapObject;
import dinodungeons.game.data.map.objects.NonPlayerCharacterMapObject.NPCType;
import dinodungeons.game.data.map.objects.SignMapObject;
import dinodungeons.game.data.map.objects.SpikeMapObject;
import dinodungeons.game.data.map.objects.TransportMapObject;
import dinodungeons.game.data.map.objects.TransportMapObject.TransportationType;
import dinodungeons.game.gameobjects.exits.building.BuildingType;
import dinodungeons.game.gameobjects.player.ItemID;
import dinodungeons.game.gameobjects.text.TextBoxContent;

public class MapObjectParser {
	
	private static final String emptyMapObjectString = "()";
	
	private static final String internalSplitter = ",";
	
	private static final String transportMapObjectID = "T";
	
	private static final String itemMapObjectID = "I";
	
	private static final String spikeMapObjectID = "S";
	
	private static final String destructableMapObjectID = "D";
	
	private static final String enemyMapObjectID = "E";

	private static final String blockMapObjectID = "B";

	private static final String doorMapObjectID = "G";
	
	private static final String candleMapObjectID = "C";
	
	private static final String signMapObjectID = "X";
	
	private static final String buildingMapObjectID = "O";
	
	private static final String npcMapObjectID = "NPC";

	public String parseMapObjectToString(MapObject mapObject){
		if(mapObject instanceof EmptyMapObject){
			return emptyMapObjectString;
		}
		if(mapObject instanceof TransportMapObject){
			return parseTransportMapObject((TransportMapObject)mapObject);
		}
		else if(mapObject instanceof ItemMapObject){
			return parseItemMapObject((ItemMapObject)mapObject);
		}
		else if(mapObject instanceof SpikeMapObject){
			return parseSpikeMapObject((SpikeMapObject)mapObject);
		}
		else if(mapObject instanceof DestructibleMapObject){
			return parseDestructibleMapObject((DestructibleMapObject)mapObject);
		}
		else if(mapObject instanceof EnemyMapObject){
			return parseEnemyMapObject((EnemyMapObject)mapObject);
		}
		else if(mapObject instanceof BlockMapObject){
			return parseBlockMapObject((BlockMapObject)mapObject);
		}
		else if(mapObject instanceof DoorMapObject){
			return parseDoorMapObject((DoorMapObject)mapObject);
		}
		else if(mapObject instanceof CandleMapObject) {
			return parseCandleMapObject((CandleMapObject)mapObject);
		}
		else if(mapObject instanceof SignMapObject) {
			return parseSignMapObject((SignMapObject)mapObject);
		}
		else if(mapObject instanceof BuildingMapObject) {
			return parseBuildingMapObject((BuildingMapObject)mapObject);
		}
		else if(mapObject instanceof NonPlayerCharacterMapObject) {
			return parseNonPlayerCharacterMapObject((NonPlayerCharacterMapObject)mapObject);
		}
		return emptyMapObjectString;
	}
	
	private String parseCandleMapObject(CandleMapObject candleMapObject) {
		String result = "(";
		result += candleMapObjectID;
		result += internalSplitter;
		result += candleMapObject.getTriggeredSwitch().getStringRepresentation();
		result += ")";
		return result;
	}

	private String parseEnemyMapObject(EnemyMapObject enemyMapObject) {
		String result = "(";
		result += enemyMapObjectID;
		result += internalSplitter;
		result += enemyMapObject.getEnemyType().getSaveRepresentation();
		result += ")";
		return result;
	}

	private String parseDestructibleMapObject(DestructibleMapObject destructibleMapObject) {
		String result = "(";
		result += destructableMapObjectID;
		result += internalSplitter;
		result += destructibleMapObject.getDestructableType().getStringRepresentation();
		result += ")";
		return result;
	}

	private String parseSpikeMapObject(SpikeMapObject spikeMapObject) {
		String result = "(";
		result += spikeMapObjectID;
		result += internalSplitter;
		result += spikeMapObject.getSpikeType();
		result += ")";
		return result;
	}

	private String parseItemMapObject(ItemMapObject itemMapObject) {
		String result = "(";
		result += itemMapObjectID;
		result += internalSplitter;
		result += itemMapObject.getItemID().getSaveRepresentation();
		result += ")";
		return result;
	}
	
	private String parseBlockMapObject(BlockMapObject blockMapObject) {
		String result = "(";
		result += blockMapObjectID;
		result += internalSplitter;
		result += blockMapObject.getBlockType().getSaveRepresentation();
		result += ")";
		return result;
	}
	
	private String parseDoorMapObject(DoorMapObject doorMapObject) {
		String result = "(";
		result += doorMapObjectID;
		result += internalSplitter;
		result += doorMapObject.getDoorType().getSaveRepresentation();
		result += ")";
		return result;
	}

	private String parseTransportMapObject(TransportMapObject transportMapObject) {
		String result = "(";
		result += transportMapObjectID;
		result += internalSplitter;
		result += transportMapObject.getDestinationMapID();
		result += internalSplitter;
		result += transportMapObject.getX();
		result += internalSplitter;
		result += transportMapObject.getY();
		result += internalSplitter;
		result += transportMapObject.getTransportationType().getSaveRepresentation();
		result += ")";
		return result;
	}
	
	private String parseSignMapObject(SignMapObject signMapObject) {
		String result = "(";
		result += signMapObjectID;
		result += internalSplitter;
		result += signMapObject.getSignType().getStringRepresentation();
		result += internalSplitter;
		result += TextBoxContent.parseMultipleToString(signMapObject.getTextBoxContent());
		result += ")";
		return result;
	}
	
	private String parseBuildingMapObject(BuildingMapObject buildingMapObject) {
		String result = "(";
		result += buildingMapObjectID;
		result += internalSplitter;
		result += buildingMapObject.getDestinationMapID();
		result += internalSplitter;
		result += buildingMapObject.getX();
		result += internalSplitter;
		result += buildingMapObject.getY();
		result += internalSplitter;
		result += buildingMapObject.getBuildingType().getStringRepresentation();
		result += ")";
		return result;
	}
	
	private String parseNonPlayerCharacterMapObject(NonPlayerCharacterMapObject npcMapObject) {
		String result = "(";
		result += npcMapObjectID;
		result += internalSplitter;
		result += npcMapObject.getNPCType().getStringRepresentation();
		result += internalSplitter;
		result += TextBoxContent.parseMultipleToString(npcMapObject.getTextBoxContent());
		result += ")";
		return result;
	}

	public MapObject parseStringToMapObject(String string){
		if(string.equals(emptyMapObjectString)){
			return new EmptyMapObject();
		}
		String strippedString = string.replace('(', ' ').replace(')', ' ').trim();
		String[] stringParts = strippedString.split(internalSplitter);
		if(stringParts[0].equals(transportMapObjectID)){
			TransportMapObject transportMapObject = new TransportMapObject();
			transportMapObject.setDestinationMapID(stringParts[1]);
			transportMapObject.setX(Integer.parseInt(stringParts[2]));
			transportMapObject.setY(Integer.parseInt(stringParts[3]));
			transportMapObject.setTransportationType(TransportationType.getTransportationTypeBySaveRepresentation(stringParts[4]));
			return transportMapObject;
		}
		else if(stringParts[0].equals(itemMapObjectID)){
			ItemMapObject itemMapObject = new ItemMapObject();
			itemMapObject.setItemID(ItemID.getItemIDBySaveRepresentation(stringParts[1]));
			return itemMapObject;
		}
		else if(stringParts[0].equals(spikeMapObjectID)){
			SpikeMapObject spikeMapObject = new SpikeMapObject();
			spikeMapObject.setSpikeType(Integer.parseInt(stringParts[1]));
			return spikeMapObject;
		}
		else if(stringParts[0].equals(destructableMapObjectID)){
			DestructibleMapObject destructibleMapObject = new DestructibleMapObject();
			destructibleMapObject.setDestructableType(DestructableType.getByStringRepresentation(stringParts[1]));
			return destructibleMapObject;
		}
		else if(stringParts[0].equals(enemyMapObjectID)){
			EnemyMapObject enemyMapObject = new EnemyMapObject();
			enemyMapObject.setEnemyType(EnemyType.getEnemyTypeBySaveRepresentation(stringParts[1]));
			return enemyMapObject;
		}
		else if(stringParts[0].equals(blockMapObjectID)){
			BlockMapObject blockMapObject = new BlockMapObject();
			blockMapObject.setBlockType(BlockType.getBlockTypeBySaveRepresentation(stringParts[1]));
			return blockMapObject;
		}
		else if(stringParts[0].equals(doorMapObjectID)){
			DoorMapObject doorMapObject = new DoorMapObject();
			doorMapObject.setDoorType(DoorType.getDoorTypeBySaveRepresentation(stringParts[1]));
			return doorMapObject;
		}
		else if(stringParts[0].equals(candleMapObjectID)){
			CandleMapObject candleMapObject = new CandleMapObject();
			candleMapObject.setTriggeredSwitch(RoomEvent.getByStringRepresentation(stringParts[1]));
			return candleMapObject;
		}
		else if(stringParts[0].equals(signMapObjectID)){
			SignMapObject signMapObject = new SignMapObject();
			signMapObject.setSignType(SignType.getByStringRepresentation(stringParts[1]));
			signMapObject.setTextBoxContent(TextBoxContent.parseStringToMultiple(stringParts[2]));
			return signMapObject;
		}
		else if(stringParts[0].equals(buildingMapObjectID)){
			BuildingMapObject buildingMapObject = new BuildingMapObject();
			buildingMapObject.setDestinationMapID(stringParts[1]);
			buildingMapObject.setX(Integer.parseInt(stringParts[2]));
			buildingMapObject.setY(Integer.parseInt(stringParts[3]));
			buildingMapObject.setBuildingType(BuildingType.getByStringRepresentation(stringParts[4]));
			return buildingMapObject;
		}
		else if(stringParts[0].equals(npcMapObjectID)){
			NonPlayerCharacterMapObject npcMapObject = new NonPlayerCharacterMapObject();
			npcMapObject.setNPCType(NPCType.getByStringRepresentation(stringParts[1]));
			npcMapObject.setTextBoxContent(TextBoxContent.parseStringToMultiple(stringParts[2]));
			return npcMapObject;
		}
		return new EmptyMapObject();
	}

}
