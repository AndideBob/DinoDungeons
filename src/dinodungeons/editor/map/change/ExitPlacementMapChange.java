package dinodungeons.editor.map.change;

import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.data.map.objects.TransportMapObject;
import dinodungeons.game.data.map.objects.TransportMapObject.TransportationType;
import lwjgladapter.logging.Logger;

public class ExitPlacementMapChange extends AbstractMapChange {
	
	MapObject exitObject;
	
	MapObject previousObject;
	
	private boolean shouldRevert;

	public ExitPlacementMapChange(int x, int y, TransportationType transportationType, String destinationMapID, int destinationX, int destinationY) {
		super(x, y);
		exitObject = buildExitObject(transportationType, destinationMapID, destinationX, destinationY);
		previousObject = null;
		shouldRevert = false;
	}
	
	private static MapObject buildExitObject(TransportationType transportationType, String destinationMapID, int destinationX, int destinationY){
		TransportMapObject transport =  new TransportMapObject();
		transport.setTransportationType(transportationType);
		transport.setDestinationMapID(destinationMapID);
		transport.setX(destinationX);
		transport.setY(destinationY);
		return transport;
	}

	@Override
	public void applyTo(ScreenMap map) {
		if(!shouldRevert) {
			if(exitObject == null){
				Logger.logError("Could not place unmapped Switch!");
			}
			previousObject = map.getMapObjectForPosition(getX(), getY());
			map.setMapObjectForPosition(getX(), getY(), exitObject);
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
