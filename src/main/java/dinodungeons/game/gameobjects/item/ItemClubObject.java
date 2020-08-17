package dinodungeons.game.gameobjects.item;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class ItemClubObject extends GameObject {
	
	private int positionX;
	
	private int positionY;
	
	private RectCollider collider;
	
	private long internalTimer;
	
	private int direction;
	private int hitState;

	public ItemClubObject(int playerX, int playerY, int direction) {
		super(GameObjectTag.ITEM_CLUB);
		internalTimer = 0L;
		hitState = 0;
		this.direction = direction;
		switch(direction) {
		case DinoDungeonsConstants.directionUp:
			positionX = playerX;
			positionY = playerY + 8;
			break;
		case DinoDungeonsConstants.directionDown:
			positionX = playerX;
			positionY = playerY - 8;
			break;
		case DinoDungeonsConstants.directionLeft:
			positionX = playerX - 8;
			positionY = playerY;
			break;
		case DinoDungeonsConstants.directionRight:
			positionX = playerX + 8;
			positionY = playerY;
			break;
		}
		updateCollider();
	}
	
	private void updateCollider() {
		if(collider == null) {
			collider = new RectCollider(positionX, positionY, 1, 1);
		}
		boolean colliderSet = false;
		switch(direction) {
		case DinoDungeonsConstants.directionUp:
			if(hitState == 0) {
				collider.setPositionX(positionX + 1);
				collider.setPositionY(positionY + 5);
				collider.setWidth(7);
				collider.setHeight(7);
				colliderSet = true;
			}
			else if(hitState == 1) {
				collider.setPositionX(positionX);
				collider.setPositionY(positionY + 6);
				collider.setWidth(8);
				collider.setHeight(8);
				colliderSet = true;
			}
			else if(hitState >= 2) {
				collider.setPositionX(positionX);
				collider.setPositionY(positionY + 6);
				collider.setWidth(8);
				collider.setHeight(10);
				colliderSet = true;
			}
			break;
		case DinoDungeonsConstants.directionDown:
			if(hitState == 0) {
				collider.setPositionX(positionX + 7);
				collider.setPositionY(positionY + 9);
				collider.setWidth(8);
				collider.setHeight(7);
				colliderSet = true;
			}
			else if(hitState == 1) {
				collider.setPositionX(positionX + 8);
				collider.setPositionY(positionY + 4);
				collider.setWidth(7);
				collider.setHeight(7);
				colliderSet = true;
			}
			else if(hitState >= 2) {
				collider.setPositionX(positionX + 7);
				collider.setPositionY(positionY);
				collider.setWidth(8);
				collider.setHeight(10);
				colliderSet = true;
			}
			break;
		case DinoDungeonsConstants.directionLeft:
			if(hitState == 0) {
				collider.setPositionX(positionX + 7);
				collider.setPositionY(positionY + 5);
				collider.setWidth(6);
				collider.setHeight(10);
				colliderSet = true;
			}
			else if(hitState == 1) {
				collider.setPositionX(positionX);
				collider.setPositionY(positionY + 8);
				collider.setWidth(8);
				collider.setHeight(7);
				colliderSet = true;
			}
			else if(hitState >= 2) {
				collider.setPositionX(positionX);
				collider.setPositionY(positionY);
				collider.setWidth(10);
				collider.setHeight(8);
				colliderSet = true;
			}
			break;
		case DinoDungeonsConstants.directionRight:
			if(hitState == 0) {
				collider.setPositionX(positionX + 3);
				collider.setPositionY(positionY + 5);
				collider.setWidth(6);
				collider.setHeight(10);
				colliderSet = true;
			}
			else if(hitState == 1) {
				collider.setPositionX(positionX + 8);
				collider.setPositionY(positionY + 8);
				collider.setWidth(8);
				collider.setHeight(7);
				colliderSet = true;
			}
			else if(hitState >= 2) {
				collider.setPositionX(positionX + 6);
				collider.setPositionY(positionY);
				collider.setWidth(10);
				collider.setHeight(8);
				colliderSet = true;
			}
			break;
		}
		if(!colliderSet) {
			collider.setPositionX(positionX + 7);
			collider.setPositionY(positionY + 7);
			collider.setWidth(1);
			collider.setHeight(1);
		}
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		internalTimer += deltaTimeInMs;
		if(internalTimer > DinoDungeonsConstants.clubStageTime) {
			internalTimer -= 0;
			hitState++;
			updateCollider();
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int frame = direction * 3 + ((hitState < 2) ? hitState : 2);
		SpriteManager.getInstance().getSprite(SpriteID.WEAPON_CLUB).draw(frame, positionX, positionY);
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return hitState >= 4;
	}
	
	@Override
	public boolean isTemporary() {
		return true;
	}

	@Override
	public Collection<Collider> getColliders() {
		if(collider != null) {
			return Collections.singleton(collider);
		}
		return Collections.emptyList();
	}

}
