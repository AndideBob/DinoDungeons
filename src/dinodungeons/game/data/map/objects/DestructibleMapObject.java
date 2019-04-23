package dinodungeons.game.data.map.objects;

public class DestructibleMapObject extends MapObject {

	DestructableType destructableType;
	
	public DestructibleMapObject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEditorInfo() {
		return destructableType.getStringRepresentation();
	}
	
	public DestructableType getDestructableType() {
		return destructableType;
	}

	public void setDestructableType(DestructableType destructableType) {
		this.destructableType = destructableType;
	}
	
	public enum DestructableType{
		BUSH_NORMAL("BSH_NRM"),
		EXPLODABLE_ROCK("EXP_RCK");
		
		private String stringRepresentation;
		
		private DestructableType(String stringRepresentation) {
			this.stringRepresentation = stringRepresentation;
		}
		
		public String getStringRepresentation() {
			return stringRepresentation;
		}
		
		public static DestructableType getByStringRepresentation(String stringRepresentation) {
			for(DestructableType type : values()) {
				if(type.stringRepresentation.equals(stringRepresentation)) {
					return type;
				}
			}
			return BUSH_NORMAL;
		}
	}

}
