package dinodungeons.game.gameobjects.collectable;

import java.util.Collection;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.gfx.Sprite;
import lwjgladapter.gfx.SpriteMap;
import lwjgladapter.physics.collision.base.Collider;

public class MoneyObject extends GameObject {

	private long animationTimer;
	
	private SpriteMap sprite;
	
	private int positionX;
	private int positionY;
	
	public MoneyObject(GameObjectTag tag, int posX, int posY) {
		super(tag);
		resetAnimationTimer();
		sprite = SpriteManager.getInstance().getSprite(SpriteID.COLLECTABLES);
		positionX = posX;
		positionY = posY;
	}

	@Override
	public void update(long deltaTimeInMs) {
		animationTimer -= deltaTimeInMs;
		if(animationTimer <= 0){
			resetAnimationTimer();
		}
	}
	
	private void resetAnimationTimer(){
		animationTimer = 500 + DinoDungeonsConstants.random.nextInt(1000);
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int currentFrame = 0;
		if(animationTimer < 100){
			currentFrame = 3;
		}
		else if(animationTimer < 200){
			currentFrame = 2;
		}
		else if(animationTimer < 300){
			currentFrame = 1;
		}
		switch(tag){
		case COLLECTABLE_MONEY_OBJECT_VALUE_ONE:
			sprite.setColorValues(0f, 0.8f, 1f, 1f);
			break;
		case COLLECTABLE_MONEY_OBJECT_VALUE_FIVE:
			sprite.setColorValues(0f, 0.8f, 0f, 1f);
			break;
		case COLLECTABLE_MONEY_OBJECT_VALUE_TEN:
			sprite.setColorValues(0.8f, 0f, 0f, 1f);
			break;
		case COLLECTABLE_MONEY_OBJECT_VALUE_TWENTYFIVE:
			sprite.setColorValues(0.8f, 0f, 0.8f, 1f);
			break;
		default:
			sprite.setColorValues(1f, 1f, 1f, 1f);
			break;
		}
		sprite.draw(currentFrame, anchorX + positionX, anchorY + positionY);
	}

	@Override
	public Collection<Collider> getColliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
