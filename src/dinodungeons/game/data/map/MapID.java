package dinodungeons.game.data.map;

public enum MapID {
	TESTROOM("000");
	
	private String internalID;
	
	private MapID(String internalID){
		this.internalID = internalID;
	}
	
	public String getInternalID(){
		return internalID;
	}
}
