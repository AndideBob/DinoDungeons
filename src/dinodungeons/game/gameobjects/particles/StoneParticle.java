package dinodungeons.game.gameobjects.particles;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;

public class StoneParticle extends BaseParticle {
	
	private static final float maxSpeed = 64f;
	private static final float minSpeed = 16f;
	
	private static final float changeYSpeedPerSecond = 128f;
	
	private int bounceState;
	private int colorVariation;
	private boolean flyingRight;
	private float baseYPosition;
	private float xSpeed;
	private float ySpeed;
	private float initialYSpeed;
	
	public StoneParticle(int startPositionX, int startPositionY, int colorVariation) {
		super(startPositionX, startPositionY);
		this.colorVariation = colorVariation;
		baseYPosition = startPositionY + (-32f + DinoDungeonsConstants.random.nextFloat() * 48f);
		flyingRight = DinoDungeonsConstants.random.nextBoolean();
		bounceState = 0;
		initialYSpeed = minSpeed + DinoDungeonsConstants.random.nextFloat() * (maxSpeed - minSpeed);
		ySpeed = initialYSpeed;
		xSpeed = (minSpeed + DinoDungeonsConstants.random.nextFloat() * (maxSpeed - minSpeed)) / 2f;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		float change = 1f * deltaTimeInMs / 1000;
		ySpeed -= changeYSpeedPerSecond * change;
		positionX += (flyingRight ? 1f : -1f) * change * xSpeed;
		positionY += change * ySpeed;
		if(positionY < baseYPosition){
			if(ySpeed < 0){
				bounceState++;
			}
			if(bounceState > 0){
				ySpeed = initialYSpeed / (bounceState * 2f);
			}
			else{
				ySpeed = initialYSpeed;
			}
			
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int frame = 16 + colorVariation;
		int x = (int)Math.round(positionX);
		int y = (int)Math.round(positionY);
		SpriteManager.getInstance().getSprite(SpriteID.PARTICLES_A).draw(frame, x, y);
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return bounceState > 3;
	}

}
