package dinodungeons.game.data;

import dinodungeons.game.data.map.MapManager;
import lwjgladapter.game.Game;
import lwjgladapter.logging.Logger;

public class DinoDungeons extends Game {

	MapManager mapManager;
	
	public DinoDungeons() {
		mapManager = new MapManager();
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadResources() {
		mapManager.loadMaps();
	}

	@Override
	public void update(long deltaTimeInMS) {
		//Logger.logDebug("FPS: " + deltaTimeInMS);

	}

}
