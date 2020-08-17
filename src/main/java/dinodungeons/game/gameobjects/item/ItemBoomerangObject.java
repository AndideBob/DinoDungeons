package dinodungeons.game.gameobjects.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.collectable.PickupObject;
import dinodungeons.game.gameobjects.player.PlayerObject;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.sfx.sound.SoundEffect;
import dinodungeons.sfx.sound.SoundManager;
import lwjgladapter.physics.collision.CircleCollider;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.base.Collision;

public class ItemBoomerangObject extends GameObject {
	
	private static final float movementSpeed = 96f;
	
	private float positionX;
	private float positionY;

	private boolean flyingOut;
	private int outDirection;
	
	private boolean arrivedBack;
	
	private int frame;
	private long nextFrameCounter;
	
	private CircleCollider collider;
	
	private ArrayList<PickupObject> pickedUpObjects;
	private HashSet<Long> collectedColliders;
	
	public ItemBoomerangObject(int spawnX, int spawnY, int direction) {
		super(GameObjectTag.ITEM_BOOMERANG);
		arrivedBack = false;
		flyingOut = true;
		positionX = spawnX;
		positionY = spawnY;
		outDirection = direction;
		collider = new CircleCollider(6f);
		updateColliderPosition();
		pickedUpObjects = new ArrayList<>();
		collectedColliders = new HashSet<>();
		frame = 0;
		nextFrameCounter = 100L;
		SoundManager.getInstance().playSoundEffect(SoundEffect.BOOMERANG_FLY);
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		nextFrameCounter -= deltaTimeInMs;
		if(nextFrameCounter <= 0) {
			nextFrameCounter = 100;
			frame++;
			if(frame % 2 == 0) {
				SoundManager.getInstance().playSoundEffect(SoundEffect.BOOMERANG_FLY);	
			}
			if(frame >= 4) {
				frame = 0;
			}
		}
		float timeChange = deltaTimeInMs / 1000f;
		float xChange = 0f;
		float yChange = 0f;
		checkPickUps();
		if(flyingOut) {
			if(hasCollisionWithObjectWithTag(GameObjectTag.WALL)
					|| positionX <= 0f
					|| positionX >= DinoDungeonsConstants.mapWidth
					|| positionY <= 0f
					|| positionY >= DinoDungeonsConstants.mapHeight) {
				SoundManager.getInstance().playSoundEffect(SoundEffect.BOOMERANG_HIT);
				flyingOut = false;
			}
			else {
				switch (outDirection) {
				case DinoDungeonsConstants.directionDown:
					yChange = -1f;
					break;
				case DinoDungeonsConstants.directionUp:
					yChange = 1;
					break;
				case DinoDungeonsConstants.directionLeft:
					xChange = -1;
					break;
				case DinoDungeonsConstants.directionRight:
					xChange = 1;
					break;
				}
			}
		}
		else {
			if(hasCollisionWithObjectWithTag(GameObjectTag.PLAYER)) {
				arrivedBack = true;
			}
			PlayerObject player = GameObjectManager.getInstance().getPlayerObject();
			int playerX = player.getPositionX() + 8;
			int playerY = player.getPositionY() + 8;
			xChange = playerX - positionX;
			yChange = playerY - positionY;
		}
		moveNormalized(xChange, yChange, timeChange);
		movePickUps();
		updateColliderPosition();
	}
	
	private void checkPickUps() {
		for(GameObjectTag c : GameObjectTag.pickups) {
			for(Collision collision : getCollisionsWithObjectsWithTag(c)) {
				long otherID = collision.getOtherID(collider.getID());
				if(!collectedColliders.contains(otherID)) {
					Collection<GameObject> collidedGameObjects = GameObjectManager.getInstance().getGameObjectsUsingColliderWithID(otherID);
					for(GameObject other : collidedGameObjects) {
						if(other instanceof PickupObject) {
							pickedUpObjects.add((PickupObject) other);
						}
					}
					collectedColliders.add(otherID);
				}
			}
		}
		
	}

	private void movePickUps() {
		int x = Math.round(positionX);
		int y = Math.round(positionY);
		for(PickupObject pickup : pickedUpObjects) {
			pickup.setCenter(x, y);
		}
	}

	private void moveNormalized(float changeX, float changeY, float timeChange){
		if(changeX == 0f && changeY == 0f) {
			return;
		}
		double vectorLength = Math.sqrt(Math.pow(changeX, 2) + Math.pow(changeY, 2));
		double lengthFactor = 1 / vectorLength;
		changeX *= lengthFactor;
		changeY *= lengthFactor;
		positionX += changeX * movementSpeed * timeChange;
		positionY += changeY * movementSpeed * timeChange;
	}
	
	private void updateColliderPosition() {
		int x = Math.round(positionX);
		int y = Math.round(positionY);
		collider.setPosition(x, y);
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int x = anchorX + Math.round(positionX) - 6;
		int y = anchorY + Math.round(positionY) - 6;
		SpriteManager.getInstance().getSprite(SpriteID.WEAPON_BOOMERANG).draw(frame, x, y);
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return arrivedBack;
	}

	@Override
	public boolean isTemporary() {
		return true;
	}
}
