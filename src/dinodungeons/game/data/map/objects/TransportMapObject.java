package dinodungeons.game.data.map.objects;

public class TransportMapObject extends MapObject {

	private String destinationMapID;
	
	private int x;
	
	private int y;
	
	private TransportationType transportationType;
	
	public TransportMapObject() {
		destinationMapID = "0000";
		x = 0;
		y = 0;
		transportationType = TransportationType.INSTANT_TELEPORT;
	}
	
	@Override
	public String getEditorInfo() {
		String infoText = "";
		switch(transportationType){
		case CAVE_ENTRY:
			infoText += "Cave En";
			break;
		case CAVE_EXIT:
			infoText += "Cave Ex";
			break;
		case DUNGEON_EXIT:
			infoText += "Dngn Ex";
			break;
		case INSTANT_TELEPORT:
			infoText += "Instant";
			break;
		case STAIRS:
			infoText += "Stairs";
			break;
		}
		infoText += " Map:" + destinationMapID + " X:" + x + " Y:" + y;
		return infoText;
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

	public TransportationType getTransportationType() {
		return transportationType;
	}

	public void setTransportationType(TransportationType transportationType) {
		this.transportationType = transportationType;
	}

	public enum TransportationType{
		INSTANT_TELEPORT("TP"),
		STAIRS("ST"),
		CAVE_ENTRY("CE"),
		CAVE_EXIT("CX"),
		DUNGEON_EXIT("DE");
		
		private String saveRepresentation;
		
		private TransportationType(String saveRepresentation){
			this.saveRepresentation = saveRepresentation;
		}
		
		public String getSaveRepresentation(){
			return saveRepresentation;
		}
		
		public static TransportationType getTransportationTypeBySaveRepresentation(String saveRepresentation){
			for(TransportationType tt : values()){
				if(tt.getSaveRepresentation().equals(saveRepresentation)){
					return tt;
				}
			}
			return INSTANT_TELEPORT;
		}
	}
	
}
