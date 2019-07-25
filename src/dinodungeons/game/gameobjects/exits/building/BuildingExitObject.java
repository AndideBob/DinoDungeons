package dinodungeons.game.gameobjects.exits.building;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.exits.TransitionExitObject;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class BuildingExitObject extends GameObject {
	
	private BuildingType buildingType;
	
	private List<Collider> colliders;
	
	private int positionX;
	private int positionY;
	
	public BuildingExitObject(int positionX, int positionY, BuildingType buildingType, String destinationMapID, int destinationX, int destinationY) {
		super(GameObjectTag.WALL);
		colliders = new ArrayList<Collider>();
		colliders.add(new RectCollider(positionX + 1, positionY + 1, 14, 15));
		colliders.add(new RectCollider(positionX + 33, positionY + 1, 14, 15));
		colliders.add(new RectCollider(positionX + 1, positionY + 16, 46, 42));
		this.positionX = positionX;
		this.positionY = positionY;
		this.buildingType = buildingType;
		GameObjectManager.getInstance().addGameObjectToCurrentMap(new TransitionExitObject(GameObjectTag.TRANSPORT, positionX + 16, positionY,
				destinationMapID, destinationX, destinationY));
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		SpriteManager.getInstance().getSprite(buildingType.getSpriteID()).draw(0, anchorX + positionX, anchorY + positionY);
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.unmodifiableList(colliders);
	}

	

}
