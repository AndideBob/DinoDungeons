package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.ItemPlacementMapChange;
import dinodungeons.game.data.gameplay.RoomEvent;
import dinodungeons.game.gameobjects.player.ItemID;
import lwjgladapter.logging.Logger;
import dinodungeons.editor.map.change.AbstractMapChange;

public class SwitchMapChangeFactory extends AbstractMapChangeFactory{

	private RoomEvent switchEvent;
	
	private int switchObjectID;
	
	public SwitchMapChangeFactory() {
		this.switchEvent = RoomEvent.NONE;
		switchObjectID = 0;
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return null;
		//return new ItemPlacementMapChange(x, y, itemID);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			switchEvent = RoomEvent.getByStringRepresentation(param);
		}
		else if(index == 1){
			try{
				switchObjectID = Integer.parseInt(param);
			}
			catch(NumberFormatException e){
				Logger.logError(e);
			}
		}
	}

}
