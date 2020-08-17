package dinodungeons.game.data.gameplay;

public enum RoomEvent {
	NONE("none"),
	SWITCH_A("a"),
	SWITCH_B("b"),
	SWITCH_C("c"),
	SWITCH_D("d"),
	SWITCH_AB("ab"),
	SWITCH_ABC("abc"),
	SWITCH_ABCD("abcd"),
	SWITCH_ALL_ENEMIES("x");
	
	private String stringRepresentation;
	
	private RoomEvent(String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}
	
	public String getStringRepresentation() {
		return stringRepresentation;
	}
	
	public static RoomEvent getByStringRepresentation(String stringRepresentation) {
		for(RoomEvent event : values()) {
			if(event.stringRepresentation.equals(stringRepresentation)) {
				return event;
			}
		}
		return SWITCH_A;
	}
}
