package dinodungeons.game.gameobjects.immovable;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.general.BasePushable;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class UnpushableStone extends BasePushable {
	
	private int colorVariant;
	
	private RectCollider collider;
	
	public UnpushableStone(int positionX, int positionY, int colorVariant) {
		super(positionX, positionY);
		this.colorVariant = colorVariant;
		collider = new RectCollider(positionX + 1, positionY + 1, 14, 14);
	}

	@Override
	protected void updateSpecific(long deltaTimeInMs, InputInformation inputInformation) {
		// Do nothing
	}

	@Override
	protected void push(int direction) {
		// Do nothing
	}

	@Override
	protected boolean canBePushed() {
		return false;
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int positionOnSpriteSheet = 0 + colorVariant;
		SpriteManager.getInstance().getSprite(SpriteID.PUSHABLES).draw(positionOnSpriteSheet, anchorX + positionX, anchorY + positionY);
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
