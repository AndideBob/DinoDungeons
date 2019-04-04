package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.AbstractMapChange;

public interface MapChangeFactory {
	
	public AbstractMapChange buildMapChange(int x, int y);
	
	public void handleParams(String... params);
	
}
