package dinodungeons.editor.map.factories;

import dinodungeons.game.data.map.objects.DestructibleMapObject.DestructableType;
import dinodungeons.editor.map.change.AbstractMapChange;
import dinodungeons.editor.map.change.DestructablePlacementMapChange;

public class DestructapleMapChangeFactory extends AbstractMapChangeFactory{

	private DestructableType destructableType;
	
	public DestructapleMapChangeFactory() {
		this.destructableType = DestructableType.BUSH_NORMAL;
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new DestructablePlacementMapChange(x, y, destructableType);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			destructableType = DestructableType.getByStringRepresentation(param);
		}
	}

}
