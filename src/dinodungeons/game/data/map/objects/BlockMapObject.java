package dinodungeons.game.data.map.objects;

public class BlockMapObject extends MapObject {
	
	BlockType blockType;
	
	public BlockMapObject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEditorInfo() {
		return getBlockName(blockType);
	}

	public BlockType getBlockType() {
		return blockType;
	}

	public void setBlockType(BlockType blockType) {
		this.blockType = blockType;
	}

	public static String getBlockName(BlockType blockType){
		switch (blockType) {
		case SOLID:
			return "Solid";
		case SWITCH_A:
			return "A BLOCK";
		case SWITCH_AB:
			return "AB BLCK";
		case SWITCH_ABC:
			return "ABC BLK";
		case SWITCH_ABCD:
			return "ABCD BK";
		case SWITCH_B:
			return "B BLOCK";
		case SWITCH_C:
			return "C BLOCK";
		case SWITCH_D:
			return "D BLOCK";
		default:
			return "Block";
		}
	}
	
	public enum BlockType{
		SOLID("0000"),
		SWITCH_A("000A"),
		SWITCH_B("000B"),
		SWITCH_C("000C"),
		SWITCH_D("000D"),
		SWITCH_AB("00AB"),
		SWITCH_ABC("0ABC"),
		SWITCH_ABCD("ABCD");
		
		private String saveRepresentation;
		
		private BlockType(String saveRepresentation){
			this.saveRepresentation = saveRepresentation;
		}

		public String getSaveRepresentation() {
			return saveRepresentation;
		}

		public static BlockType getBlockTypeBySaveRepresentation(String saveRepresentation){
			for(BlockType id : values()){
				if(id.getSaveRepresentation().equals(saveRepresentation)){
					return id;
				}
			}
			return SOLID;
		}
	}
}
