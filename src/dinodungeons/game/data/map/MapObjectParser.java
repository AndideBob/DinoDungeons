package dinodungeons.game.data.map;

import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;

public class MapObjectParser {

	public String parseMapObjectToString(MapObject mapObject){
		return "()";
	}
	
	public MapObject parseStringToMapObject(String string){
		return new EmptyMapObject();
	}

}
