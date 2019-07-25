package dinodungeons.editor.map.change;

import dinodungeons.game.data.map.BaseLayerTile;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.BuildingMapObject;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.gameobjects.exits.building.BuildingType;
import lwjgladapter.logging.Logger;

public class BuildingPlacementMapChange extends AbstractMapChange {
	
	MapObject buildingObject;
	
	MapObject previousObject;
	
	private boolean shouldRevert;

	public BuildingPlacementMapChange(int x, int y, BuildingType buildingType, String destinationMapID, int destinationX, int destinationY) {
		super(x, y);
		buildingObject = buildBuildingMapObject(buildingType, destinationMapID, destinationX, destinationY);
		previousObject = null;
		shouldRevert = false;
	}
	
	private static MapObject buildBuildingMapObject(BuildingType buildingType, String destinationMapID, int destinationX, int destinationY){
		BuildingMapObject building =  new BuildingMapObject();
		building.setBuildingType(buildingType);
		building.setDestinationMapID(destinationMapID);
		building.setX(destinationX);
		building.setY(destinationY);
		return building;
	}

	@Override
	public void applyTo(ScreenMap map) {
		if(!shouldRevert) {
			if(buildingObject == null){
				Logger.logError("Could not place unmapped Switch!");
			}
			previousObject = map.getMapObjectForPosition(getX(), getY());
			if(isPlacementAllowed(map))
			{
				map.setMapObjectForPosition(getX(), getY(), buildingObject);
			}
			else{
				map.setMapObjectForPosition(getX(), getY(), new EmptyMapObject());
			}
		}
		else {
			if(previousObject == null) {
				map.setMapObjectForPosition(getX(), getY(), new EmptyMapObject());
			}
			else {
				map.setMapObjectForPosition(getX(), getY(), previousObject);
			}
		}
		
	}

	private boolean isPlacementAllowed(ScreenMap map) {
		return BaseLayerTile.floorTiles.contains(map.getBaseLayerTileForPosition(getX(), getY()));
	}

	@Override
	public void revert() {
		shouldRevert = !shouldRevert;
	}

}
