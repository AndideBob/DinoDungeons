package dinodungeons.editor.map.change;

import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.EnemyMapObject;
import dinodungeons.game.data.map.objects.EnemyMapObject.EnemyType;
import dinodungeons.game.data.map.objects.MapObject;

public class EnemyPlacementMapChange extends AbstractMapChange {
	
	EnemyType enemyType;
	
	MapObject previousObject;
	
	private boolean shouldRevert;

	public EnemyPlacementMapChange(int x, int y, EnemyType enemyType) {
		super(x, y);
		this.enemyType = enemyType;
		previousObject = null;
		shouldRevert = false;
	}

	@Override
	public void applyTo(ScreenMap map) {
		if(!shouldRevert) {
			EnemyMapObject enemy = new EnemyMapObject();
			enemy.setEnemyType(enemyType);
			previousObject = map.getMapObjectForPosition(getX(), getY());
			map.setMapObjectForPosition(getX(), getY(), enemy);
		}
		else {
			if(previousObject == null) {
				map.setMapObjectForPosition(getX(), getY(), new EmptyMapObject());
			}
			else {
				map.setMapObjectForPosition(getX(), getY(), previousObject);
			}
		}
		
	}

	@Override
	public void revert() {
		shouldRevert = !shouldRevert;
	}

}
