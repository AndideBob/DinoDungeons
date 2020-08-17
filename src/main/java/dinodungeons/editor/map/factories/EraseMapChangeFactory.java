package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.AbstractMapChange;
import dinodungeons.editor.map.change.EraseMapChange;

public class EraseMapChangeFactory extends AbstractMapChangeFactory{
	
	public EraseMapChangeFactory() {
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new EraseMapChange(x, y);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		// No params needed
	}

}
