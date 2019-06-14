package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.AbstractMapChange;
import dinodungeons.editor.map.change.EnemyPlacementMapChange;
import dinodungeons.game.data.map.objects.EnemyMapObject.EnemyType;

public class EnemyMapChangeFactory extends AbstractMapChangeFactory{

	private EnemyType enemeyType;
	
	public EnemyMapChangeFactory() {
		this.enemeyType = EnemyType.GREEN_BAT;
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new EnemyPlacementMapChange(x, y, enemeyType);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			enemeyType = EnemyType.getEnemyTypeBySaveRepresentation(param);
		}
	}

}
