package dinodungeons.game;

import java.util.ArrayList;

import dinodungeons.game.data.exceptions.InvalidMapIDException;
import dinodungeons.game.data.map.BaseLayerTile;
import dinodungeons.game.data.map.MapManager;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.ScreenMapUtil;
import dinodungeons.game.gameobjects.GameObject;
import dinodungeons.game.gameobjects.GameObjectTag;
import dinodungeons.game.gameobjects.player.PlayerObject;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.tilesets.TileSet;
import dinodungeons.gfx.tilesets.TilesetManager;
import lwjgladapter.datatypes.LWJGLAdapterException;
import lwjgladapter.game.Game;
import lwjgladapter.physics.collision.PhysicsHelper;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

public class DinoDungeons extends Game {
	
	private MapManager mapManager;
	private TilesetManager tileSetManager;
	
	private ArrayList<GameObject> gameObjects;
	private ScreenMap currentMap;
	
	public DinoDungeons() {
		mapManager = new MapManager();
		tileSetManager = new TilesetManager();
		gameObjects = new ArrayList<>();
	}
	
	private void loadInitialGameState() throws InvalidMapIDException{
		currentMap = mapManager.getMapById("0000");
		gameObjects.addAll(ScreenMapUtil.createGameObjectsForMap(currentMap));
		gameObjects.add(new PlayerObject(GameObjectTag.PLAYER, 36,36));
	}

	@Override
	public void draw() {
		//DrawMap
		for(int x = 0; x < currentMap.getSizeX(); x++){
			for(int y = 0; y < currentMap.getSizeY(); y++){
				BaseLayerTile tile = currentMap.getBaseLayerTileForPosition(x, y);
				TileSet tileSet = currentMap.getTileSet();
				tileSetManager.drawTile(tile, tileSet, x * 16, y * 16);
			}
		}
		//DrawGameObjects
		for(GameObject o : gameObjects){
			o.draw();
		}
	}

	@Override
	public void loadResources() throws LWJGLAdapterException{
		SpriteManager.getInstance().loadSprites();
		mapManager.loadMaps();
		tileSetManager.loadResources();
		loadInitialGameState();
	}

	@Override
	public void update(long deltaTimeInMs) throws LWJGLAdapterException {
		updateCollisions();
		for(GameObject o : gameObjects){
			o.update(deltaTimeInMs);
		}
	}
	
	private void updateCollisions() throws CollisionNotSupportedException{
		PhysicsHelper.getInstance().resetCollisions();
		for(GameObject o1 : gameObjects){
			o1.clearCollisionTags();
			for(GameObject o2 : gameObjects){
				if(!o1.equals(o2)){
					for(Collider c1 : o1.getColliders()){
						for(Collider c2 : o2.getColliders()){
							if(PhysicsHelper.getInstance().checkCollisionBetween(c1, c2)){
								o1.addCollisionTag(c1.getKey(), o2.getTag());
							}
						}
					}
				}
			}
		}
	}

}
