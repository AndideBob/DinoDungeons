package dinodungeons.game.gameobjects.particles;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;

public class SmokeParticle extends BaseParticle {
	
	private static final float maxMovementSpeed = 32f;
	private static final float minMovementSpeed = 8f;
	
	private float speedRight;
	private float speedUp;
	private float movementSpeed;
	private long particleLifeTime;
	
	private int frame;
	
	public SmokeParticle(int startPositionX, int startPositionY) {
		super(startPositionX, startPositionY);
		setRandomDirection();
		particleLifeTime = 600;
		frame = 0;
	}
	
	private void setRandomDirection(){
		speedRight = -1f + DinoDungeonsConstants.random.nextFloat() * 2f;
		speedUp = -1f + DinoDungeonsConstants.random.nextFloat() * 2f;
		while(speedRight == 0f && speedUp == 0f){
			speedRight = -1f + DinoDungeonsConstants.random.nextFloat() * 2f;
			speedUp = -1f + DinoDungeonsConstants.random.nextFloat() * 2f;
		}
		normalizeMovementChange();
		movementSpeed = minMovementSpeed + DinoDungeonsConstants.random.nextFloat() * (maxMovementSpeed - minMovementSpeed);
	}
	
	private void normalizeMovementChange(){
		double vectorLength = Math.sqrt(Math.pow(speedRight, 2) + Math.pow(speedUp, 2));
		double lengthFactor = 1 / vectorLength;
		speedRight *= lengthFactor;
		speedUp *= lengthFactor;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		particleLifeTime -= deltaTimeInMs;
		if(particleLifeTime > 400) {
			frame = 0;
		}
		else if(particleLifeTime > 200) {
			frame = 1;
		}
		else {
			frame = 2;
		}
		positionX += speedRight * movementSpeed * deltaTimeInMs / 1000;
		positionY += speedUp * movementSpeed * deltaTimeInMs / 1000;
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int shownFrame = frame + 5;
		int x = (int)Math.round(positionX);
		int y = (int)Math.round(positionY);
		SpriteManager.getInstance().getSprite(SpriteID.PARTICLES_B).draw(shownFrame, anchorX + x, anchorY + y);
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return particleLifeTime <= 0;
	}

}
