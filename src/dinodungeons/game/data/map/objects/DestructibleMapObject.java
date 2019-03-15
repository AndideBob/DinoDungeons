package dinodungeons.game.data.map.objects;

public class DestructibleMapObject extends MapObject {

	int destructableType;
	
	public DestructibleMapObject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEditorInfo() {
		return getDestructableName(destructableType);
	}
	
	public int getDestructableType() {
		return destructableType;
	}

	public void setDestructableType(int destructableType) {
		this.destructableType = destructableType;
	}

	public static String getDestructableName(int destructableType){
		switch (destructableType) {
		case 0:
			return "Bush[NRM]";
		}
		return "Other Destructable";
	}

}
