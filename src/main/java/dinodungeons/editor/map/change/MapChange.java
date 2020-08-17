package dinodungeons.editor.map.change;

import dinodungeons.game.data.map.ScreenMap;

interface MapChange {

	public void applyTo(final ScreenMap map);
	
	public void revert();

}
