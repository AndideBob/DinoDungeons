package dinodungeons.editor.map.change;

import dinodungeons.game.data.map.ScreenMap;

public interface MapChange {

	public void applyTo(ScreenMap map);
	
	public void revert();

}
