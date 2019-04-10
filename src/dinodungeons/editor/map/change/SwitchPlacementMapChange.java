package dinodungeons.editor.map.change;

import dinodungeons.game.data.gameplay.RoomEvent;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.BlockMapObject;
import dinodungeons.game.data.map.objects.BlockMapObject.BlockType;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import lwjgladapter.logging.Logger;

public class SwitchPlacementMapChange extends AbstractMapChange {
	
	MapObject switchObject;
	
	MapObject previousObject;
	
	private boolean shouldRevert;

	public SwitchPlacementMapChange(int x, int y, RoomEvent switchEvent, int switchObjectID) {
		super(x, y);
		switchObject = buildSwitchObject(switchEvent, switchObjectID);
		previousObject = null;
		shouldRevert = false;
	}
	
	private static MapObject buildSwitchObject(RoomEvent switchEvent, int switchObjectID){
		switch(switchObjectID){
		case 0:
			BlockMapObject blockObject = new BlockMapObject();
			switch(switchEvent){
			case SWITCH_A:
				blockObject.setBlockType(BlockType.SWITCH_A);
				break;
			case SWITCH_AB:
				blockObject.setBlockType(BlockType.SWITCH_AB);
				break;
			case SWITCH_ABC:
				blockObject.setBlockType(BlockType.SWITCH_ABC);
				break;
			case SWITCH_ABCD:
				blockObject.setBlockType(BlockType.SWITCH_ABCD);
				break;
			case SWITCH_B:
				blockObject.setBlockType(BlockType.SWITCH_B);
				break;
			case SWITCH_C:
				blockObject.setBlockType(BlockType.SWITCH_C);
				break;
			case SWITCH_D:
				blockObject.setBlockType(BlockType.SWITCH_D);
				break;
			default:
				blockObject.setBlockType(BlockType.NO_SWITCH);
				break;
			}
			return blockObject;
		}
		Logger.logError("Could not map SwitchObjectID " + switchObjectID + " to object!");
		return null;
	}

	@Override
	public void applyTo(ScreenMap map) {
		if(!shouldRevert) {
			if(switchObject != null){
				
			}
			else{
				Logger.logError("Could not place unmapped Switch!");
			}
			previousObject = map.getMapObjectForPosition(getX(), getY());
			map.setMapObjectForPosition(getX(), getY(), switchObject);
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

	@Override
	public void revert() {
		shouldRevert = !shouldRevert;
	}

}
