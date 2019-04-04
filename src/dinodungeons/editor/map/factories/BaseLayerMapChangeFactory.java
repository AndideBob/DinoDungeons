package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.BaseLayerMapChange;
import dinodungeons.editor.map.change.AbstractMapChange;

public class BaseLayerMapChangeFactory implements MapChangeFactory{

	private int tileID;
	
	public BaseLayerMapChangeFactory(int tileID) {
		this.tileID = tileID;
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new BaseLayerMapChange(x, y, tileID);
	}

}
