package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.AbstractMapChange;
import dinodungeons.editor.map.change.ImmovablePlacementMapChange;
import dinodungeons.editor.map.change.ImmovablePlacementMapChange.ImmovableType;

public class ImmovableMapChangeFactory extends AbstractMapChangeFactory{

	private ImmovableType immovableType;
	
	public ImmovableMapChangeFactory() {
		this.immovableType = ImmovableType.STONE_BLOCK;
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new ImmovablePlacementMapChange(x, y, immovableType);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			immovableType = ImmovableType.getByStringRepresentation(param);
		}
	}

}
