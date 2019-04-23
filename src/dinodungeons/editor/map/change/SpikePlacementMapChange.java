package dinodungeons.editor.map.change;

import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.data.map.objects.SpikeMapObject;

public class SpikePlacementMapChange extends AbstractMapChange {
	
	int spikeType;
	
	MapObject previousObject;
	
	private boolean shouldRevert;

	public SpikePlacementMapChange(int x, int y, int spikeType) {
		super(x, y);
		this.spikeType = spikeType;
		previousObject = null;
		shouldRevert = false;
	}

	@Override
	public void applyTo(ScreenMap map) {
		if(!shouldRevert) {
			SpikeMapObject spike = new SpikeMapObject();
			spike.setSpikeType(spikeType);;
			previousObject = map.getMapObjectForPosition(getX(), getY());
			map.setMapObjectForPosition(getX(), getY(), spike);
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
