package dinodungeons.editor.map.change;

import dinodungeons.game.data.exceptions.ScreenMapIndexOutOfBounds;
import dinodungeons.game.data.map.ScreenMap;
import lwjgladapter.logging.Logger;

public class BaseLayerMapChange extends AbstractMapChange {

	private int newBaseLayerValue;
	private int oldBaseLayerValue;
	
	public BaseLayerMapChange(int x, int y, int value) {
		super(x, y);
		newBaseLayerValue = value;
		oldBaseLayerValue = -1;
	}

	@Override
	public void applyTo(final ScreenMap map) {
		oldBaseLayerValue = map.getBaseLayerValueForPosition(getX(), getY());
		try {
			map.setBaseLayer(getX(), getY(), newBaseLayerValue);
			map.updateBaseLayerTiles();
		} catch (ScreenMapIndexOutOfBounds e) {
			Logger.logError(e);
		}
	}

	@Override
	public void revert() {
		int nextOldVal = newBaseLayerValue;
		newBaseLayerValue = oldBaseLayerValue;
		oldBaseLayerValue = nextOldVal;
	}

}
