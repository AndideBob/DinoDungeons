package dinodungeons.game.data.map;

import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.data.map.objects.TransportMapObject;
import dinodungeons.game.data.map.objects.TransportMapObject.TransportationType;

public class MapObjectParser {
	
	private static final String emptyMapObjectString = "()";
	
	private static final String internalSplitter = ",";
	
	private static final String transportMapObjectID = "T";

	public String parseMapObjectToString(MapObject mapObject){
		if(mapObject instanceof EmptyMapObject){
			return emptyMapObjectString;
		}
		if(mapObject instanceof TransportMapObject){
			return parseTransportMapObject((TransportMapObject)mapObject);
		}
		return emptyMapObjectString;
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
		return new EmptyMapObject();
	}

}
