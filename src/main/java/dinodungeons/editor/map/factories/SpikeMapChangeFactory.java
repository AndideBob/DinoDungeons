package dinodungeons.editor.map.factories;

import lwjgladapter.logging.Logger;
import dinodungeons.editor.map.change.AbstractMapChange;
import dinodungeons.editor.map.change.SpikePlacementMapChange;

public class SpikeMapChangeFactory extends AbstractMapChangeFactory{

	private int spikeType;
	
	public SpikeMapChangeFactory() {
		this.spikeType = 0;
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new SpikePlacementMapChange(x, y, spikeType);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			try{
				spikeType = Integer.parseInt(param);
			}
			catch(NumberFormatException e){
				Logger.logError(e);
			}
		}
	}

}
