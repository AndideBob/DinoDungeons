package dinodungeons.editor.map.factories;

import dinodungeons.game.data.map.objects.TransportMapObject.TransportationType;
import lwjgladapter.logging.Logger;
import dinodungeons.editor.map.change.AbstractMapChange;
import dinodungeons.editor.map.change.ExitPlacementMapChange;

public class ExitMapChangeFactory extends AbstractMapChangeFactory{

	private TransportationType transportType;
	
	private String mapID;
	
	private int mapX;
	
	private int mapY;
	
	public ExitMapChangeFactory() {
		transportType = TransportationType.INSTANT_TELEPORT;
		mapID = "0000";
		mapX = 0;
		mapY = 0;
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new ExitPlacementMapChange(x, y, transportType, mapID, mapX, mapY);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			transportType = TransportationType.getTransportationTypeBySaveRepresentation(param);
		}
		else if(index == 1){
			mapID = param;
		}
		else if(index == 2){
			try{
				mapX = Integer.parseInt(param);
			}
			catch(NumberFormatException e){
				Logger.logError(e);
			}
		}
		else if(index == 3){
			try{
				mapY = Integer.parseInt(param);
			}
			catch(NumberFormatException e){
				Logger.logError(e);
			}
		}
	}

}
