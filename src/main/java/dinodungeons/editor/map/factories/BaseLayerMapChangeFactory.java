package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.BaseLayerMapChange;
import lwjgladapter.logging.Logger;
import dinodungeons.editor.map.change.AbstractMapChange;

public class BaseLayerMapChangeFactory extends AbstractMapChangeFactory{

	private int tileID;
	
	public BaseLayerMapChangeFactory() {
		this.tileID = 0;
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new BaseLayerMapChange(x, y, tileID);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			try{
				tileID = Integer.parseInt(param);
			}
			catch(NumberFormatException e){
				Logger.logError(e);
			}
		}
	}

}
