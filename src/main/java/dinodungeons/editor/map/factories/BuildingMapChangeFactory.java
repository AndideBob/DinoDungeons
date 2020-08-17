package dinodungeons.editor.map.factories;

import dinodungeons.game.gameobjects.exits.building.BuildingType;
import lwjgladapter.logging.Logger;
import dinodungeons.editor.map.change.AbstractMapChange;
import dinodungeons.editor.map.change.BuildingPlacementMapChange;

public class BuildingMapChangeFactory extends AbstractMapChangeFactory{

	private BuildingType buildingType;
	
	private String mapID;
	
	private int mapX;
	
	private int mapY;
	
	public BuildingMapChangeFactory() {
		buildingType = BuildingType.BASIC_HUT;
		mapID = "0000";
		mapX = 0;
		mapY = 0;
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new BuildingPlacementMapChange(x, y, buildingType, mapID, mapX, mapY);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			buildingType = BuildingType.getByStringRepresentation(param);
		}
		else if(index == 1){
			mapID = param;
		}
		else if(index == 2){
			try{
				mapX = Integer.parseInt(param);
			}
			catch(NumberFormatException e){
				Logger.logError(e);
			}
		}
		else if(index == 3){
			try{
				mapY = Integer.parseInt(param);
			}
			catch(NumberFormatException e){
				Logger.logError(e);
			}
		}
	}

}
