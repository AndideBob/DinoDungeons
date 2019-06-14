package dinodungeons.editor.ui.buttons.mapchange;

import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.EnemyButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.map.objects.EnemyMapObject.EnemyType;

public class EnemyChangeButton extends BaseButton {
	
	private EnemyButtonGroup belongingButtonGroup;
	
	private EnemyType enemyType;
	
	public EnemyChangeButton(int positionX, int positionY, final EnemyButtonGroup belongingButtonGroup, EnemyType enemyType) {
		super(positionX, positionY, getButtonSpriteForEnemyType(enemyType));
		this.enemyType = enemyType;
		this.belongingButtonGroup = belongingButtonGroup;
	}

	@Override
	protected void onClick() {
		belongingButtonGroup.unpressAll();
		setPressed(true);
		belongingButtonGroup.setEnemyType(enemyType);
	}
	
	private static ButtonSprite getButtonSpriteForEnemyType(EnemyType enemyType){
		switch (enemyType) {
		case GREEN_BAT:
			return ButtonSprite.ENEMY_BAT_GREEN;
		case TRICERABLOB:
			return ButtonSprite.ENEMY_TRICERABLOB;
		}
		return ButtonSprite.CANCEL;
	}

	@Override
	protected void updateInternal(InputInformation inputInformation) {
		//Do nothing
	}

	@Override
	protected void drawInternal() {
		//Do nothing
	}
	
	public enum StaticObjectType{
		IMMOVABLE_BLOCK,
		SPIKES_METAL,
		SPIKES_WOOD,
		DESTRUCTABLE_GRASS,
		DESTRUCTABLE_STONE
	}

}
