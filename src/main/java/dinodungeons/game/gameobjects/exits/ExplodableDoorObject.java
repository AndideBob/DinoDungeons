package dinodungeons.game.gameobjects.exits;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.GameEventManager;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.map.BaseLayerTile;
import dinodungeons.game.data.transitions.TransitionManager;
import dinodungeons.game.data.transitions.TransitionType;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.general.BaseExplodable;
import dinodungeons.game.gameobjects.particles.StoneParticle;
import dinodungeons.gfx.tilesets.TileSet;
import dinodungeons.gfx.tilesets.TilesetManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class ExplodableDoorObject extends BaseExplodable {
	
	private TileSet tileSet;
	private int direction;
	
	private int positionX;
	private int positionY;
	
	private RectCollider blockCollider;
	
	private String eventKey;
	
	private String targetMap;
	private int targetX;
	private int targetY;
	private RectCollider transportCollider;
	
	public ExplodableDoorObject(TileSet tileSet, int direction, int positionX, int positionY, String eventKey, String targetMap, int targetX, int targetY) {
		super();
		this.direction = direction;
		this.positionX = positionX;
		this.positionY = positionY;
		this.tileSet = tileSet;
		this.targetMap = targetMap;
		this.targetX = targetX;
		this.targetY = targetY;
		blockCollider = new RectCollider(positionX, positionY, 16, 16);
		transportCollider = new RectCollider(positionX + 6, positionY + 6, 4, 4);
		this.eventKey = eventKey;
		if(GameEventManager.getInstance().hasEventOccured(eventKey)){
			setExplodedManually(false);
		}
	}
	
	@Override
	protected void explode() {
		// TODO Play sound and generate Particles
		for(int i = 0; i < 4; i++){
			GameObjectManager.getInstance().addGameObjectToCurrentMap(new StoneParticle(positionX + 4, positionY + 4, tileSet.getColorVariation()));
		}
		GameEventManager.getInstance().markEventAsOccured(eventKey);
	}

	@Override
	protected GameObjectTag getTagAfterExplosion() {
		return GameObjectTag.NONE;
	}

	@Override
	protected void drawBeforeExplosion(int anchorX, int anchorY) {
		BaseLayerTile tile = BaseLayerTile.BLOCKED_DOOR_DOWN;
		switch(direction){
		case DinoDungeonsConstants.directionDown:
			tile = BaseLayerTile.BLOCKED_DOOR_DOWN;
			break;
		case DinoDungeonsConstants.directionUp:
			tile = BaseLayerTile.BLOCKED_DOOR_UP;
			break;
		case DinoDungeonsConstants.directionRight:
			tile = BaseLayerTile.BLOCKED_DOOR_RIGHT;
			break;
		case DinoDungeonsConstants.directionLeft:
			tile = BaseLayerTile.BLOCKED_DOOR_LEFT;
			break;
		}
		TilesetManager.getInstance().drawTile(tile, tileSet, anchorX + positionX, anchorY + positionY);
	}

	@Override
	protected void drawAfterExplosion(int anchorX, int anchorY) {
		//Nothing needs to be drawn
	}

	@Override
	protected void updateBeforeExplosion(long deltaTimeInMs, InputInformation inputInformation) {
		//Does Nothing
	}

	@Override
	protected void updateAfterExplosion(long deltaTimeInMs, InputInformation inputInformation) {
		if(hasCollisionWithObjectWithTag(GameObjectTag.PLAYER)){
			TransitionManager.getInstance().initiateTransition(targetMap, targetX * 16, targetY * 16, TransitionType.TELEPORT);
		}
	}

	@Override
	protected Collection<Collider> getCollidersBeforeExplosion() {
		return Collections.singleton(blockCollider);
	}

	@Override
	protected Collection<Collider> getCollidersAfterExplosion() {
		return Collections.singleton(transportCollider);
	}
	
	

}
