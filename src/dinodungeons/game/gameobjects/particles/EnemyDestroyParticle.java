package dinodungeons.game.gameobjects.particles;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;

public class EnemyDestroyParticle extends BaseParticle { 
	
	private long particleLifeTime;
	
	public EnemyDestroyParticle(int startPositionX, int startPositionY) {
		super(startPositionX, startPositionY);
		particleLifeTime = 300;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		particleLifeTime -= deltaTimeInMs;
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int frame = Math.min(4, (int)Math.floor(particleLifeTime / 60));
		int x = (int)Math.round(positionX);
		int y = (int)Math.round(positionY);
		SpriteManager.getInstance().getSprite(SpriteID.PARTICLES_B).draw(frame, x, y);
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return particleLifeTime <= 0;
	}

}
