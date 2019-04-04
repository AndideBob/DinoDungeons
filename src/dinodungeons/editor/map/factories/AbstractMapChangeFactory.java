package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.AbstractMapChange;

public abstract class AbstractMapChangeFactory implements MapChangeFactory {

	@Override
	public abstract AbstractMapChange buildMapChange(int x, int y);

	@Override
	public final void handleParams(String... params) {
		for(int i = 0; i < params.length; i++){
			if(params[i] != null){
				handleParam(params[i], i);
			}
		}
	}
	
	protected abstract void handleParam(String param, int index);

}
