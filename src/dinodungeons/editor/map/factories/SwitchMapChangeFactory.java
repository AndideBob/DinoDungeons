package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.SwitchPlacementMapChange;
import dinodungeons.game.data.gameplay.RoomEvent;
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
		return new SwitchPlacementMapChange(x, y, switchEvent, switchObjectID);
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
