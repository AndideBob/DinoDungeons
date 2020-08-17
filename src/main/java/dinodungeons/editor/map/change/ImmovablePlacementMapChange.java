package dinodungeons.editor.map.change;

import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.BlockMapObject;
import dinodungeons.game.data.map.objects.BlockMapObject.BlockType;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import lwjgladapter.logging.Logger;

public class ImmovablePlacementMapChange extends AbstractMapChange {
	
	private ImmovableType immovableType;
	
	MapObject previousObject;
	
	private boolean shouldRevert;

	public ImmovablePlacementMapChange(int x, int y, ImmovableType immovableType) {
		super(x, y);
		this.immovableType = immovableType;
		previousObject = null;
		shouldRevert = false;
	}

	@Override
	public void applyTo(ScreenMap map) {
		if(!shouldRevert) {
			previousObject = map.getMapObjectForPosition(getX(), getY());
			map.setMapObjectForPosition(getX(), getY(), createMapObject());
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
	
	private MapObject createMapObject(){
		switch (immovableType) {
		case STONE_BLOCK:
			BlockMapObject block = new BlockMapObject();
			block.setBlockType(BlockType.SOLID);
			return block;
		}
		Logger.logDebug("Could not create MapObject for ImmovableType: " + immovableType.toString());
		return new EmptyMapObject();
	}

	@Override
	public void revert() {
		shouldRevert = !shouldRevert;
	}
	
	public enum ImmovableType{
		STONE_BLOCK("STN_BLK");
		
		private String stringRepresentation;
		
		private ImmovableType(String stringRepresentation) {
			this.stringRepresentation = stringRepresentation;
		}
		
		public String getStringRepresentation() {
			return stringRepresentation;
		}
		
		public static ImmovableType getByStringRepresentation(String stringRepresentation) {
			for(ImmovableType type : values()) {
				if(type.stringRepresentation.equals(stringRepresentation)) {
					return type;
				}
			}
			return STONE_BLOCK;
		}
	}

}
