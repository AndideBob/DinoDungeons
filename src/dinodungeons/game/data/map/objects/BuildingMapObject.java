package dinodungeons.game.data.map.objects;

import dinodungeons.game.gameobjects.exits.building.BuildingType;

public class BuildingMapObject extends MapObject {

	private String destinationMapID;
	
	private int x;
	
	private int y;
	
	private BuildingType buildingType;
	
	public BuildingMapObject() {
		destinationMapID = "0000";
		x = 0;
		y = 0;
		buildingType = BuildingType.BASIC_HUT;
	}
	
	@Override
	public String getEditorInfo() {
		return "Building:" + buildingType.toString();
	}

	public String getDestinationMapID() {
		return destinationMapID;
	}

	public void setDestinationMapID(String destinationMapID) {
		this.destinationMapID = destinationMapID;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public BuildingType getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(BuildingType buildingType) {
		this.buildingType = buildingType;
	}
	
}
