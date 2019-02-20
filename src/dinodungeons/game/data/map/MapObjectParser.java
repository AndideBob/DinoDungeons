package dinodungeons.game.data.map;

import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;

public class MapObjectParser {
	
	private static final String emptyMapObjectString = "()";

	public String parseMapObjectToString(MapObject mapObject){
		if(mapObject instanceof EmptyMapObject){
			return emptyMapObjectString;
		}
		return emptyMapObjectString;
	}
	
	public MapObject parseStringToMapObject(String string){
		if(string.equals(emptyMapObjectString)){
			return new EmptyMapObject();
		}
		return new EmptyMapObject();
	}

}
