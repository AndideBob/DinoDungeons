package dinodungeons.game.gameobjects.environment;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.general.BaseExplodable;
import dinodungeons.game.gameobjects.particles.StoneParticle;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class ExplodableStone extends BaseExplodable {
	
	private int colorVariant;
	
	private RectCollider collider;
	
	private int positionX;
	
	private int positionY;
	
	public ExplodableStone(int positionX, int positionY, int colorVariant) {
		super();
		this.colorVariant = colorVariant;
		this.positionX = positionX;
		this.positionY = positionY;
		collider = new RectCollider(positionX + 1, positionY + 1, 14, 14);
	}

	@Override
	protected void explode() {
		for(int i = 0; i < 4; i++){
			GameObjectManager.getInstance().addGameObjectToCurrentMap(new StoneParticle(positionX + 4, positionY + 4, colorVariant));
		}
	}

	@Override
	protected GameObjectTag getTagAfterExplosion() {
		return GameObjectTag.NONE;
	}

	@Override
	protected void drawBeforeExplosion(int anchorX, int anchorY) {
		int positionOnSpriteSheet = 8 + colorVariant;
		SpriteManager.getInstance().getSprite(SpriteID.DESTRUCTABLES).draw(positionOnSpriteSheet, anchorX + positionX, anchorY + positionY);
	}

	@Override
	protected void drawAfterExplosion(int anchorX, int anchorY) {
		// Nothing here
	}

	@Override
	protected void updateBeforeExplosion(long deltaTimeInMs, InputInformation inputInformation) {
		// Nothing to do here
	}

	@Override
	protected void updateAfterExplosion(long deltaTimeInMs, InputInformation inputInformation) {
		// Nothing to do here
	}

	@Override
	protected Collection<Collider> getCollidersBeforeExplosion() {
		return Collections.singleton(collider);
	}

	@Override
	protected Collection<Collider> getCollidersAfterExplosion() {
		return Collections.emptyList();
	}

}
