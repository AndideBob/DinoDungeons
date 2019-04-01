package dinodungeons.game.data.map.objects;

public class DoorMapObject extends MapObject {
	
	DoorType doorType;
	
	public DoorMapObject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEditorInfo() {
		return getDoorName(doorType);
	}

	public DoorType getDoorType() {
		return doorType;
	}

	public void setDoorType(DoorType doorType) {
		this.doorType = doorType;
	}

	public static String getDoorName(DoorType doorType){
		switch (doorType) {
		case ENEMIES:
			return "ENM DOOR";
		case KEY:
			return "KEY DOOR";
		case SWITCH_A:
			return "A DOOR";
		case SWITCH_AB:
			return "AB DOOR";
		case SWITCH_ABC:
			return "ABC DOOR";
		case SWITCH_ABCD:
			return "ABCD DOR";
		case SWITCH_B:
			return "B DOOR";
		case SWITCH_C:
			return "C DOOR";
		case SWITCH_D:
			return "D DOOR";
		default:
			return "Door";
		}
	}
	
	public enum DoorType{
		KEY("0000"),
		MASTER_KEY("0001"),
		ENEMIES("0002"),
		SWITCH_A("000A"),
		SWITCH_B("000B"),
		SWITCH_C("000C"),
		SWITCH_D("000D"),
		SWITCH_AB("00AB"),
		SWITCH_ABC("0ABC"),
		SWITCH_ABCD("ABCD");
		
		private String saveRepresentation;
		
		private DoorType(String saveRepresentation){
			this.saveRepresentation = saveRepresentation;
		}

		public String getSaveRepresentation() {
			return saveRepresentation;
		}

		public static DoorType getDoorTypeBySaveRepresentation(String saveRepresentation){
			for(DoorType id : values()){
				if(id.getSaveRepresentation().equals(saveRepresentation)){
					return id;
				}
			}
			return ENEMIES;
		}
	}
}
