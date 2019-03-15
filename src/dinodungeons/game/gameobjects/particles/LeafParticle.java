package dinodungeons.game.gameobjects.particles;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;

public class LeafParticle extends BaseParticle {
	
	private static final float fallingSpeed = 32f; 
	
	private long flowTimer;
	
	private int colorVariation;
	private boolean flyingRight;
	private boolean flyingUp;
	private long particleLifeTime;
	
	public LeafParticle(int startPositionX, int startPositionY, int colorVariation) {
		super(startPositionX, startPositionY);
		this.colorVariation = colorVariation;
		flowTimer = 100 + DinoDungeonsConstants.random.nextInt(200);
		flyingUp = true;
		flyingRight = DinoDungeonsConstants.random.nextBoolean();
		particleLifeTime = 1000 + DinoDungeonsConstants.random.nextInt(1000);
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		particleLifeTime -= deltaTimeInMs;
		if(particleLifeTime > 0) {
			flowTimer -= deltaTimeInMs;
			float positionChange = fallingSpeed * deltaTimeInMs / 1000f;
			if(flowTimer > 0) {
				positionY += (flyingUp ? 2f : -1f) * positionChange;
				positionX += (flyingUp ? 2f : 0.5f) * (flyingRight ? 1f : -1f) * positionChange;
			}
			else {
				flyingUp = false;
				flyingRight = !flyingRight;
				flowTimer = 200 + DinoDungeonsConstants.random.nextInt(200);
			}
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int frame = (flyingRight ? 0 : 8) + colorVariation;
		int x = (int)Math.round(positionX);
		int y = (int)Math.round(positionY);
		SpriteManager.getInstance().getSprite(SpriteID.PARTICLES_A).draw(frame, x, y);
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return particleLifeTime <= 0;
	}

}
