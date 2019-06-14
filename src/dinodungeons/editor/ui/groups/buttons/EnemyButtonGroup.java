package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.mapchange.EnemyChangeButton;
import dinodungeons.game.data.map.objects.EnemyMapObject.EnemyType;

public class EnemyButtonGroup extends UIButtonGroup {
	
	private Editor editorHandle;
	
	public EnemyButtonGroup(final Editor editorHandle){
		super(editorHandle);
		this.editorHandle = editorHandle;
	}

	@Override
	protected Collection<? extends BaseButton> initializeButtons(final Editor editorHandle) {
		ArrayList<BaseButton> buttons = new ArrayList<>();
		//Basic Enemies
		buttons.add(new EnemyChangeButton(256, 208, this, EnemyType.GREEN_BAT));
		buttons.add(new EnemyChangeButton(272, 208, this, EnemyType.TRICERABLOB));
		return buttons;
	}
	
	public void setEnemyType(EnemyType enemyType) {
		editorHandle.setMapChange(MapChangeType.ENEMY_PLACEMENT, enemyType.getSaveRepresentation());
	}
	
	@Override
	protected void onActivate() {
		buttons.get(0).setPressed(true);
		setEnemyType(EnemyType.GREEN_BAT);
	}

}
