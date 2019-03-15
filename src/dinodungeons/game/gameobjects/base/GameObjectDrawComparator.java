package dinodungeons.game.gameobjects.base;

import java.util.Comparator;

public class GameObjectDrawComparator implements Comparator<GameObject> {

	@Override
	public int compare(GameObject o1, GameObject o2) {
		int result = o1.getTag().getSortingLayer() - o2.getTag().getSortingLayer();
		if(result > 0) {
			return 1;
		}
		else if (result < 0) {
			return -1;
		}
		return 0;
	}

}
